package earth.terrarium.adastra.client.forge;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.AdAstraClient;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdAstraClientForge {
    public static final Map<Item, BlockEntityWithoutLevelRenderer> ITEM_RENDERERS = new HashMap<>();

    public static void init() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(AdAstraClientForge::onSetupItemColors);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRegisterClientHud);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(AdAstraClientForge::onRenderLevelStage);

        ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
            () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> {
                ResourcefulConfig config = AdAstra.CONFIGURATOR.getConfig(AdAstraConfig.class);
                if (config == null) return null;
                return new ConfigScreen(parent, null, config);
            })
        );
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(AdAstraClient::init);
        AdAstraClient.onRegisterItemRenderers(ITEM_RENDERERS::put);
    }

    @SubscribeEvent
    public static void onRegisterKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(AdAstraClient.KEY_TOGGLE_SUIT_FLIGHT);
        event.register(AdAstraClient.KEY_TOGGLE_SUIT_HOVER);
        event.register(AdAstraClient.KEY_OPEN_RADIO);
    }

    @SubscribeEvent
    public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
        AdAstraClient.onRegisterParticles((type, provider) -> event.registerSpriteSet(type, provider::create));
    }

    @SubscribeEvent
    public static void modelLoading(ModelEvent.RegisterAdditional event) {
        AdAstraClient.onRegisterModels(event::register);
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        AdAstraClient.onRegisterEntityLayers(event::registerLayerDefinition);
    }

    @SubscribeEvent
    public static void onClientReloadListeners(RegisterClientReloadListenersEvent event) {
        AdAstraClient.onAddReloadListener((id, listener) -> event.registerReloadListener(listener));
    }

    private static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase.equals(TickEvent.Phase.START)) {
            AdAstraClient.clientTick(Minecraft.getInstance());
        }
    }

    private static void onRenderLevelStage(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            AdAstraClient.renderOverlays(event.getPoseStack(), event.getCamera());
        }
    }

    private static void onRegisterClientHud(RenderGuiEvent.Post event) {
        AdAstraClient.onRegisterHud(hud -> hud.renderHud(event.getGuiGraphics(), event.getPartialTick()));
    }

    private static void onSetupItemColors(RegisterColorHandlersEvent.Item event) {
        AdAstraClient.onAddItemColors(event::register);
    }
}
