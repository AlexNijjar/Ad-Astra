package earth.terrarium.adastra.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.dimension.AdAstraPlanetRenderers;
import earth.terrarium.adastra.client.models.armor.SpaceSuitModel;
import earth.terrarium.adastra.client.models.entities.mobs.*;
import earth.terrarium.adastra.client.models.entities.vehicles.LanderModel;
import earth.terrarium.adastra.client.models.entities.vehicles.RocketModel;
import earth.terrarium.adastra.client.models.entities.vehicles.RoverModel;
import earth.terrarium.adastra.client.particle.LargeFlameParticle;
import earth.terrarium.adastra.client.particle.OxygenBubbleParticle;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.client.renderers.blocks.*;
import earth.terrarium.adastra.client.renderers.entities.mobs.*;
import earth.terrarium.adastra.client.renderers.entities.vehicles.LanderRenderer;
import earth.terrarium.adastra.client.renderers.entities.vehicles.RocketRenderer;
import earth.terrarium.adastra.client.renderers.entities.vehicles.RoverRenderer;
import earth.terrarium.adastra.client.renderers.world.OverlayRenderer;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import earth.terrarium.adastra.client.screens.machines.*;
import earth.terrarium.adastra.client.screens.player.OverlayScreen;
import earth.terrarium.adastra.client.screens.vehicles.LanderScreen;
import earth.terrarium.adastra.client.screens.vehicles.RocketScreen;
import earth.terrarium.adastra.client.screens.vehicles.RoverScreen;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.items.EtrionicCapacitorItem;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ServerboundSyncKeybindPacket;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.common.utils.KeybindManager;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.botarium.client.ClientHooks;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class AdAstraClient {
    public static final OverlayRenderer OXYGEN_OVERLAY_RENDERER = new OverlayRenderer(0x4099ccff, () -> AdAstraConfigClient.showOxygenDistributorArea, ModBlocks.OXYGEN_DISTRIBUTOR);
    public static final OverlayRenderer GRAVITY_OVERLAY_RENDERER = new OverlayRenderer(0x40DE2F14, () -> AdAstraConfigClient.showGravityNormalizerArea, ModBlocks.GRAVITY_NORMALIZER);

    public static final KeyMapping KEY_TOGGLE_SUIT_FLIGHT = new KeyMapping(
        ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY.getString(),
        InputConstants.KEY_V,
        ConstantComponents.AD_ASTRA_CATEGORY.getString());

    public static final KeyMapping KEY_OPEN_RADIO = new KeyMapping(
        ConstantComponents.OPEN_RADIO_KEY.getString(),
        InputConstants.KEY_R,
        ConstantComponents.AD_ASTRA_CATEGORY.getString());

    public static void init() {
        AdAstra.CONFIGURATOR.registerConfig(AdAstraConfigClient.class);
        registerScreens();
        registerBlockEntityRenderers();
        registerEntityRenderers();
        registerItemProperties();
        registerRenderLayers();
        registerArmor();
    }

    private static void registerScreens() {
        MenuScreens.register(ModMenus.COAL_GENERATOR.get(), CoalGeneratorScreen::new);
        MenuScreens.register(ModMenus.COMPRESSOR.get(), CompressorScreen::new);
        MenuScreens.register(ModMenus.ETRIONIC_BLAST_FURNACE.get(), EtrionicBlastFurnaceScreen::new);
        MenuScreens.register(ModMenus.OXYGEN_LOADER.get(), OxygenLoaderScreen::new);
        MenuScreens.register(ModMenus.FUEL_REFINERY.get(), FuelRefineryScreen::new);
        MenuScreens.register(ModMenus.WATER_PUMP.get(), WaterPumpScreen::new);
        MenuScreens.register(ModMenus.SOLAR_PANEL.get(), SolarPanelScreen::new);
        MenuScreens.register(ModMenus.OXYGEN_DISTRIBUTOR.get(), OxygenDistributorScreen::new);
        MenuScreens.register(ModMenus.GRAVITY_NORMALIZER.get(), GravityNormalizerScreen::new);
        MenuScreens.register(ModMenus.CRYO_FREEZER.get(), CryoFreezerScreen::new);
        MenuScreens.register(ModMenus.NASA_WORKBENCH.get(), NasaWorkbenchScreen::new);

        MenuScreens.register(ModMenus.ROCKET.get(), RocketScreen::new);
        MenuScreens.register(ModMenus.ROVER.get(), RoverScreen::new);
        MenuScreens.register(ModMenus.LANDER.get(), LanderScreen::new);

        MenuScreens.register(ModMenus.PLANETS.get(), PlanetsScreen::new);
    }

    private static void registerBlockEntityRenderers() {
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.ENERGIZER.get(), c -> new EnergizerBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.GLOBE.get(), c -> new GlobeBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.OXYGEN_DISTRIBUTOR.get(), c -> new OxygenDistributorBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.GRAVITY_NORMALIZER.get(), c -> new GravityNormalizerBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.FLAG.get(), c -> new FlagBlockEntityRenderer());
        ClientHooks.registerBlockEntityRenderers(ModBlockEntityTypes.SLIDING_DOOR.get(), c -> new SlidingDoorBlockEntityRenderer());
    }

    private static void registerEntityRenderers() {
        ClientHooks.registerEntityRenderer(ModEntityTypes.AIR_VORTEX, NoopRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ROVER, RoverRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_1_ROCKET, c -> new RocketRenderer(c, RocketModel.TIER_1_LAYER, RocketRenderer.TIER_1_TEXTURE));
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_2_ROCKET, c -> new RocketRenderer(c, RocketModel.TIER_2_LAYER, RocketRenderer.TIER_2_TEXTURE));
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_3_ROCKET, c -> new RocketRenderer(c, RocketModel.TIER_3_LAYER, RocketRenderer.TIER_3_TEXTURE));
        ClientHooks.registerEntityRenderer(ModEntityTypes.TIER_4_ROCKET, c -> new RocketRenderer(c, RocketModel.TIER_4_LAYER, RocketRenderer.TIER_4_TEXTURE));
        ClientHooks.registerEntityRenderer(ModEntityTypes.LANDER, c -> new LanderRenderer(c, LanderModel.LAYER));

        ClientHooks.registerEntityRenderer(ModEntityTypes.LUNARIAN, LunarianRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.CORRUPTED_LUNARIAN, CorruptedLunarianRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.STAR_CRAWLER, StarCrawlerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.MARTIAN_RAPTOR, MartianRaptorRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.PYGRO, PygroRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ZOMBIFIED_PYGRO, ZombifiedPygroRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.PYGRO_BRUTE, PygroBruteRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.MOGLER, MoglerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ZOMBIFIED_MOGLER, ZombifiedMoglerRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.SULFUR_CREEPER, SulfurCreeperRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.LUNARIAN_WANDERING_TRADER, LunarianWanderingTraderRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.GLACIAN_RAM, GlacianRamRenderer::new);
        ClientHooks.registerEntityRenderer(ModEntityTypes.ICE_SPIT, ThrownItemRenderer::new);
    }

    public static void registerArmor() {
        ClientPlatformUtils.registerArmor(SpaceSuitModel.SPACE_SUIT_TEXTURE, SpaceSuitModel.SPACE_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(),
            ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get());
        ClientPlatformUtils.registerArmor(SpaceSuitModel.NETHERITE_SPACE_SUIT_TEXTURE, SpaceSuitModel.NETHERITE_SPACE_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(),
            ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get());
        ClientPlatformUtils.registerArmor(SpaceSuitModel.JET_SUIT_TEXTURE, SpaceSuitModel.JET_SUIT_LAYER, SpaceSuitModel::new,
            ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(),
            ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get());
    }

    public static void onRegisterEntityLayers(ClientPlatformUtils.LayerDefinitionRegistry consumer) {
        consumer.register(RoverModel.LAYER, RoverModel::createBodyLayer);
        RocketModel.register(consumer);
        consumer.register(LanderModel.LAYER, LanderModel::createBodyLayer);
        SpaceSuitModel.register(consumer);

        consumer.register(LunarianModel.LAYER_LOCATION, LunarianModel::createBodyLayer);
        consumer.register(CorruptedLunarianModel.LAYER_LOCATION, CorruptedLunarianModel::createBodyLayer);
        consumer.register(StarCrawlerModel.LAYER_LOCATION, StarCrawlerModel::createBodyLayer);
        consumer.register(MartianRaptorModel.LAYER_LOCATION, MartianRaptorModel::createBodyLayer);
        consumer.register(PygroModel.LAYER_LOCATION, PygroModel::createBodyLayer);
        consumer.register(PygroBruteModel.LAYER_LOCATION, PygroBruteModel::createBodyLayer);
        consumer.register(ZombifiedPygroModel.LAYER_LOCATION, ZombifiedPygroModel::createBodyLayer);
        consumer.register(MoglerModel.LAYER_LOCATION, MoglerModel::createBodyLayer);
        consumer.register(SulfurCreeperModel.LAYER_LOCATION, SulfurCreeperModel::createBodyLayer);
        consumer.register(GlacianRamModel.LAYER_LOCATION, GlacianRamModel::createBodyLayer);
    }

    private static void registerItemProperties() {
        ClientHooks.registerItemProperty(ModItems.ETRIONIC_CAPACITOR.get(), new ResourceLocation(AdAstra.MOD_ID, "toggled"), (stack, level, entity, i) -> EtrionicCapacitorItem.active(stack) ? 0 : 1);
    }

    public static void registerRenderLayers() {
        ClientHooks.setRenderLayer(ModBlocks.VENT.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.STEEL_DOOR.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.STEEL_TRAPDOOR.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.AERONOS_LADDER.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.STROPHAR_LADDER.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.GLACIAN_TRAPDOOR.get(), RenderType.cutout());
        ClientHooks.setRenderLayer(ModBlocks.AERONOS_TRAPDOOR.get(), RenderType.cutout());
    }

    public static void onRegisterParticles(BiConsumer<ParticleType<SimpleParticleType>, ClientPlatformUtils.SpriteParticleRegistration<SimpleParticleType>> consumer) {
        consumer.accept(ModParticleTypes.ACID_RAIN.get(), SplashParticle.Provider::new);
        consumer.accept(ModParticleTypes.LARGE_FLAME.get(), LargeFlameParticle.Provider::new);
        consumer.accept(ModParticleTypes.LARGE_SMOKE.get(), LargeFlameParticle.Provider::new);
        consumer.accept(ModParticleTypes.OXYGEN_BUBBLE.get(), OxygenBubbleParticle.Provider::new);
    }

    public static void onRegisterModels(Consumer<ResourceLocation> consumer) {
        ModBlocks.GLOBES.stream().forEach(b -> consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "block/%s_cube".formatted(b.getId().getPath()))));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "block/%s_flipped".formatted(ModBlocks.AIRLOCK.getId().getPath())));
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "block/%s_flipped".formatted(ModBlocks.REINFORCED_DOOR.getId().getPath())));
        consumer.accept(OxygenDistributorBlockEntityRenderer.TOP);
        consumer.accept(GravityNormalizerBlockEntityRenderer.TOP);
        consumer.accept(GravityNormalizerBlockEntityRenderer.TOE);
    }

    public static void onRegisterItemRenderers(BiConsumer<Item, BlockEntityWithoutLevelRenderer> consumer) {
        ModItems.GLOBES.stream().forEach(item -> consumer.accept(item.get(), new GlobeBlockEntityRenderer.ItemRenderer()));
        consumer.accept(ModItems.OXYGEN_DISTRIBUTOR.get(), new OxygenDistributorBlockEntityRenderer.ItemRenderer());
        consumer.accept(ModItems.GRAVITY_NORMALIZER.get(), new GravityNormalizerBlockEntityRenderer.ItemRenderer());
        consumer.accept(ModItems.ROVER.get(), new RoverRenderer.ItemRenderer());
        consumer.accept(ModItems.TIER_1_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_1_LAYER, RocketRenderer.TIER_1_TEXTURE));
        consumer.accept(ModItems.TIER_2_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_2_LAYER, RocketRenderer.TIER_2_TEXTURE));
        consumer.accept(ModItems.TIER_3_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_3_LAYER, RocketRenderer.TIER_3_TEXTURE));
        consumer.accept(ModItems.TIER_4_ROCKET.get(), new RocketRenderer.ItemRenderer(RocketModel.TIER_4_LAYER, RocketRenderer.TIER_4_TEXTURE));
    }

    public static void onRegisterHud(Consumer<ClientPlatformUtils.RenderHud> consumer) {
        consumer.accept(OverlayScreen::render);
    }

    public static void onAddItemColors(BiConsumer<ItemColor, ItemLike[]> consumer) {
        consumer.accept((stack, i) -> i > 0 ? -1 : ((DyeableArmorItem) stack.getItem()).getColor(stack), new ItemLike[]{ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(), ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get()});
        consumer.accept((stack, i) -> i > 0 ? -1 : ((DyeableArmorItem) stack.getItem()).getColor(stack), new ItemLike[]{ModItems.NETHERITE_SPACE_HELMET.get(), ModItems.NETHERITE_SPACE_SUIT.get(), ModItems.NETHERITE_SPACE_PANTS.get(), ModItems.NETHERITE_SPACE_BOOTS.get()});
        consumer.accept((stack, i) -> i > 0 ? -1 : ((DyeableArmorItem) stack.getItem()).getColor(stack), new ItemLike[]{ModItems.JET_SUIT_HELMET.get(), ModItems.JET_SUIT.get(), ModItems.JET_SUIT_PANTS.get(), ModItems.JET_SUIT_BOOTS.get()});
    }

    public static void renderOverlays(PoseStack stack, Camera camera) {
        OXYGEN_OVERLAY_RENDERER.render(stack, camera);
        GRAVITY_OVERLAY_RENDERER.render(stack, camera);
    }

    public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> consumer) {
        consumer.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_renderers"), new AdAstraPlanetRenderers());
    }

    public static void clientTick(Minecraft minecraft) {
        LocalPlayer player = minecraft.player;
        if (player == null) return;

        if (KEY_OPEN_RADIO.consumeClick() && player.getVehicle() instanceof RadioHolder) {
            RadioHandler.open(null);
        }

        if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItemTags.JET_SUITS)) {
            Options options = minecraft.options;

            if (KEY_TOGGLE_SUIT_FLIGHT.consumeClick()) {
                AdAstraConfigClient.jetSuitEnabled = !AdAstraConfigClient.jetSuitEnabled;
                Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
                player.displayClientMessage(AdAstraConfigClient.jetSuitEnabled ? ConstantComponents.SUIT_FLIGHT_ENABLED : ConstantComponents.SUIT_FLIGHT_DISABLED, true);
            }

            KeybindManager.set(player,
                options.keyJump.isDown(),
                options.keySprint.isDown(),
                AdAstraConfigClient.jetSuitEnabled);

            NetworkHandler.CHANNEL.sendToServer(new ServerboundSyncKeybindPacket(
                options.keyJump.isDown(),
                options.keySprint.isDown(),
                AdAstraConfigClient.jetSuitEnabled
            ));
        }
    }
}
