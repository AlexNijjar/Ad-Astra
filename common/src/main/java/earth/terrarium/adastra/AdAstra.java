package earth.terrarium.adastra;

import com.mojang.logging.LogUtils;
import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.api.systems.*;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncLocalPlanetDataPacket;
import earth.terrarium.adastra.common.network.messages.ClientboundSyncPlanetsPacket;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.registry.*;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.slf4j.Logger;

import java.util.function.BiConsumer;

public class AdAstra {

    public static final String MOD_ID = "ad_astra";
    public static final Configurator CONFIGURATOR = new Configurator();
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {

        CONFIGURATOR.registerConfig(AdAstraConfig.class);

        NetworkHandler.init();
        StationLoader.init();

        ModFluidProperties.FLUID_PROPERTIES.initialize();
        ModFluids.FLUIDS.init();
        ModBlocks.BLOCKS.init();
        ModItems.ITEMS.init();
        ModCreativeTab.TABS.init();
        ModEntityTypes.ENTITY_TYPES.init();
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
        ModMenus.MENUS.init();
        ModRecipeTypes.RECIPE_TYPES.init();
        ModRecipeSerializers.RECIPE_SERIALIZERS.init();
        ModParticleTypes.PARTICLE_TYPES.init();
        ModPaintingVariants.PAINTING_VARIANTS.init();
        ModSoundEvents.SOUND_EVENTS.init();
        ModStructures.STRUCTURE_TYPES.init();
        ModStructures.STRUCTURE_PROCESSORS.init();
        ModFeatures.FEATURES.init();
        ModWorldCarvers.WORLD_CARVERS.init();
        ModBiomeSources.BIOME_SOURCES.init();
        ModDensityFunctionTypes.DENSITY_FUNCTION_TYPES.init();
    }

    public static void postInit() {
        CauldronInteraction.WATER.put(ModItems.SPACE_HELMET.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(ModItems.SPACE_SUIT.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(ModItems.SPACE_PANTS.get(), CauldronInteraction.DYED_ITEM);
        CauldronInteraction.WATER.put(ModItems.SPACE_BOOTS.get(), CauldronInteraction.DYED_ITEM);
        ModEntityTypes.registerSpawnPlacements();

    }

    public static void onAddReloadListener(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planets"), new AdAstraData());
    }

    public static void onDatapackSync(ServerPlayer player) {
        NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncPlanetsPacket(AdAstraData.planets()), player);
    }

    public static void onServerTick(MinecraftServer server) {
        server.getPlayerList().getPlayers().forEach(player -> {
            if (player.tickCount % 5 == 0) {
                boolean oxygen = OxygenApi.API.hasOxygen(player);
                short temperature = TemperatureApi.API.getTemperature(player);
                float gravity = GravityApi.API.getGravity(player);
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSyncLocalPlanetDataPacket(new PlanetData(oxygen, temperature, gravity)), player);
            }
        });
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_data"), new PlanetData());
    }
}
