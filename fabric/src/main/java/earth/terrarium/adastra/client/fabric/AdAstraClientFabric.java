package earth.terrarium.adastra.client.fabric;

import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.client.dimension.ModDimensionSpecialEffects;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.common.registry.ModBlocks;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class AdAstraClientFabric {

    public static void init() {
        AdAstraClient.init();
        onAddReloadListener();
        ClientTickEvents.START_CLIENT_TICK.register(AdAstraClient::clientTick);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_TOGGLE_SUIT_HOVER);
        KeyBindingHelper.registerKeyBinding(AdAstraClient.KEY_OPEN_RADIO);
        AdAstraClient.onRegisterParticles((particle, provider) -> ParticleFactoryRegistry.getInstance().register(particle, provider::create));
        AdAstraClient.onRegisterItemRenderers((item, renderer) -> BuiltinItemRendererRegistry.INSTANCE.register(item, renderer::renderByItem));
        AdAstraClient.onRegisterEntityLayers((location, definition) -> EntityModelLayerRegistry.registerModelLayer(location, definition::get));
        AdAstraClient.onRegisterHud(hud -> HudRenderCallback.EVENT.register(hud::renderHud));
        ModelLoadingPlugin.register(ctx -> AdAstraClient.onRegisterModels(ctx::addModels));
        WorldRenderEvents.AFTER_TRANSLUCENT.register(ctx -> AdAstraClient.renderOverlays(ctx.matrixStack(), ctx.camera()));
        AdAstraClient.onAddItemColors(ColorProviderRegistry.ITEM::register);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SOLAR_PANEL.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WATER_PUMP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ENERGIZER.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ETRIONIC_BLAST_FURNACE.get(), RenderType.cutout());
        ModBlocks.GLOBES.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.SLIDING_DOORS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.INDUSTRIAL_LAMPS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
        ModBlocks.SMALL_INDUSTRIAL_LAMPS.stream().forEach(block -> BlockRenderLayerMap.INSTANCE.putBlock(block.get(), RenderType.cutout()));
    }

    public static void onAddReloadListener() {
        AdAstraClient.onAddReloadListener((id, listener) -> ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return id;
            }

            @Override
            public @NotNull CompletableFuture<Void> reload(@NotNull PreparationBarrier synchronizer, @NotNull ResourceManager manager, @NotNull ProfilerFiller prepareProfiler, @NotNull ProfilerFiller applyProfiler, @NotNull Executor prepareExecutor, @NotNull Executor applyExecutor) {
                return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
            }
        }));
    }

    public static void registerDimensionEffects(Map<ResourceKey<Level>, ModDimensionSpecialEffects> renderers) {
        renderers.forEach((dimension, effects) -> {
            DimensionRenderingRegistry.registerDimensionEffects(dimension.location(), effects);
            if (effects.renderer().customClouds()) {
                DimensionRenderingRegistry.registerCloudRenderer(dimension, context -> {
                    var camera = context.camera().getPosition();
                    effects.renderClouds(
                        context.world(),
                        DimensionRenderingUtils.getTicks(),
                        context.tickDelta(),
                        context.matrixStack(),
                        camera.x, camera.y, camera.z,
                        context.projectionMatrix());
                });
            }

            if (effects.renderer().customSky()) {
                DimensionRenderingRegistry.registerSkyRenderer(dimension, context -> effects.renderSky(
                    context.world(),
                    DimensionRenderingUtils.getTicks(),
                    context.tickDelta(),
                    context.matrixStack(),
                    context.camera(),
                    context.projectionMatrix(),
                    false,
                    () -> {}
                ));
            }

            if (effects.renderer().customWeather()) {
                DimensionRenderingRegistry.registerWeatherRenderer(dimension, context -> {
                    var camera = context.camera().getPosition();
                    effects.renderSnowAndRain(
                        context.world(),
                        DimensionRenderingUtils.getTicks(),
                        context.tickDelta(),
                        context.lightmapTextureManager(),
                        camera.x, camera.y, camera.z
                    );
                });
            }
        });
    }
}
