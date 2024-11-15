package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

@SuppressWarnings("SameParameterValue")
public class ModBiomeDataProvider {
    public static final ResourceKey<Biome> SPACE = register("orbit");
    public static final ResourceKey<Biome> LUNAR_WASTELANDS = register("lunar_wastelands");
    public static final ResourceKey<Biome> MARTIAN_CANYON_CREEK = register("martian_canyon_creek");
    public static final ResourceKey<Biome> MARTIAN_POLAR_CAPS = register("martian_polar_caps");
    public static final ResourceKey<Biome> MARTIAN_WASTELANDS = register("martian_wastelands");
    public static final ResourceKey<Biome> INFERNAL_VENUS_BARRENS = register("infernal_venus_barrens");
    public static final ResourceKey<Biome> VENUS_WASTELANDS = register("venus_wastelands");
    public static final ResourceKey<Biome> MERCURY_DELTAS = register("mercury_deltas");
    public static final ResourceKey<Biome> GLACIO_ICE_PEAKS = register("glacio_ice_peaks");
    public static final ResourceKey<Biome> GLACIO_SNOWY_BARRENS = register("glacio_snowy_barrens");
    public static final ResourceKey<Biome> TRAPPIST_1_E_PLAINS = register("trappist_1_e_plains");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);

        context.register(SPACE, biome(
            false,
            0.5f,
            0,
            0x000000,
            0x000000,
            new MobSpawnSettings.Builder(),
            new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers),
            null));

        context.register(LUNAR_WASTELANDS, biome(
            false,
            0.7f,
            0,
            0x000000,
            0x161614,
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.CORRUPTED_LUNARIAN.get(), 100, 1, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.STAR_CRAWLER.get(), 100, 1, 3))
                .addMobCharge(ModEntityTypes.CORRUPTED_LUNARIAN.get(), 0.2, 0.015)
                .addMobCharge(ModEntityTypes.STAR_CRAWLER.get(), 0.4, 0.015),
            moon(placedFeatures, configuredCarvers),
            null));

        context.register(MARTIAN_CANYON_CREEK, biome(
            false,
            0.8f,
            0,
            0xe6ac84,
            0xe6ac84,
            new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f),
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MARTIAN_RAPTOR.get(), 100, 1, 3))
                .addMobCharge(ModEntityTypes.MARTIAN_RAPTOR.get(), 0.2, 0.015),
            marsCanyonCreek(placedFeatures, configuredCarvers),
            null));

        context.register(MARTIAN_POLAR_CAPS, biome(
            true,
            -0.7f,
            1,
            0xe6ac84,
            0xe6ac84,
            new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f),
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MARTIAN_RAPTOR.get(), 100, 1, 3))
                .addMobCharge(ModEntityTypes.MARTIAN_RAPTOR.get(), 0.2, 0.015),
            marsPolarCaps(placedFeatures, configuredCarvers),
            null));

        context.register(MARTIAN_WASTELANDS, biome(
            false,
            0.8f,
            0,
            0xe6ac84,
            0xe6ac84,
            new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f),
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MARTIAN_RAPTOR.get(), 100, 1, 3))
                .addMobCharge(ModEntityTypes.MARTIAN_RAPTOR.get(), 0.2, 0.015),
            mars(placedFeatures, configuredCarvers),
            null));

        context.register(INFERNAL_VENUS_BARRENS, biome(
            true,
            1.6f,
            1,
            0xd18b52,
            0xd18b52,
            new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f),
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MOGLER.get(), 100, 1, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.SULFUR_CREEPER.get(), 100, 1, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.ZOMBIFIED_PYGRO.get(), 100, 1, 2))
                .addMobCharge(ModEntityTypes.MOGLER.get(), 0.2, 0.015)
                .addMobCharge(ModEntityTypes.SULFUR_CREEPER.get(), 0.4, 0.02)
                .addMobCharge(ModEntityTypes.ZOMBIFIED_PYGRO.get(), 0.2, 0.012),
            venusInfernalBarrens(placedFeatures, configuredCarvers),
            null));

        context.register(VENUS_WASTELANDS, biome(
            true,
            1.6f,
            1,
            0xd18b52,
            0xd18b52,
            new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f),
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MOGLER.get(), 100, 1, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.SULFUR_CREEPER.get(), 100, 1, 3))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.ZOMBIFIED_PYGRO.get(), 100, 1, 2))
                .addMobCharge(ModEntityTypes.MOGLER.get(), 0.2, 0.015)
                .addMobCharge(ModEntityTypes.SULFUR_CREEPER.get(), 0.4, 0.02)
                .addMobCharge(ModEntityTypes.ZOMBIFIED_PYGRO.get(), 0.2, 0.012),
            venus(placedFeatures, configuredCarvers),
            null));

        context.register(MERCURY_DELTAS, biome(
            false,
            1.6f,
            0,
            0x8b0000,
            0x000000,
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 100, 1, 3))
                .addMobCharge(EntityType.MAGMA_CUBE, 0.4, 0.015),
            mercury(placedFeatures, configuredCarvers),
            null));

        context.register(GLACIO_ICE_PEAKS, biome(
            true,
            -0.7f,
            1,
            0xc0d8ff,
            0xc0d8ff,
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.GLACIAN_RAM.get(), 12, 2, 4)),
            glacioIcePeaks(placedFeatures, configuredCarvers),
            null));

        context.register(GLACIO_SNOWY_BARRENS, biome(
            true,
            -0.7f,
            1,
            0xc0d8ff,
            0xc0d8ff,
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntityTypes.GLACIAN_RAM.get(), 12, 2, 4)),
            glacio(placedFeatures, configuredCarvers),
            null));

        context.register(TRAPPIST_1_E_PLAINS, biome(
            true,
            0.8f,
            0.4F,
            0x049fcc,
            0x205A0B,
            new MobSpawnSettings.Builder()
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.BAT, 10, 8, 8))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 12, 4, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COW, 10, 4, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 10, 4, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 10, 4, 4))
                .addSpawn(MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SPIDER, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE_VILLAGER, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.CREEPER, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.WITCH, 10, 4, 4))
                .addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.GLOW_SQUID, 10, 4, 4))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModEntityTypes.MARTIAN_RAPTOR.get(), 10, 1, 3))
                .addMobCharge(ModEntityTypes.MARTIAN_RAPTOR.get(), 0.2, 0.015),
             trappist_1_e(placedFeatures, configuredCarvers),
            null));

    }

    private static BiomeGenerationSettings.Builder moon(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LARGE_DRIPSTONE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MOON_CHEESE_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MOON_DESH_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MOON_ICE_SHARD_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MOON_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MOON_SOUL_SOIL);
    }

    private static BiomeGenerationSettings.Builder mars(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.LARGE_DRIPSTONE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.DRIPSTONE_CLUSTER)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, CavePlacements.POINTED_DRIPSTONE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MARS_DIAMOND_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MARS_ICE_SHARD_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MARS_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MARS_OSTRUM_ORE);
    }

    private static BiomeGenerationSettings.Builder marsCanyonCreek(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return mars(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatureProvider.MARS_ROCK);
    }

    private static BiomeGenerationSettings.Builder marsPolarCaps(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return mars(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
    }

    private static BiomeGenerationSettings.Builder venus(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.VENUS_CALORITE_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.VENUS_COAL_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.VENUS_DIAMOND_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.VENUS_GOLD_ORE);
    }

    private static BiomeGenerationSettings.Builder venusInfernalBarrens(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return venus(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_MAGMA)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatureProvider.SMALL_INFERNAL_SPIRE_COLUMN)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, ModPlacedFeatureProvider.LARGE_INFERNAL_SPIRE_COLUMN);
    }

    private static BiomeGenerationSettings.Builder mercury(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, NetherPlacements.BASALT_PILLAR)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, MiscOverworldPlacements.SPRING_LAVA)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OrePlacements.ORE_MAGMA)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.MERCURY_IRON_ORE);
    }

    private static BiomeGenerationSettings.Builder glacio(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.AMETHYST_GEODE)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_COAL_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_COPPER_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_ICE_SHARD_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_LAPIS_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_COAL_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_COPPER_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_LAPIS_ORE);
    }

    private static BiomeGenerationSettings.Builder trappist_1_e(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return caves(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER)
            .addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavePlacements.AMETHYST_GEODE)
            .addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN)
//            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, (ResourceKey<PlacedFeature>) "minecraft:ore_gold")
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_COPPER_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_ICE_SHARD_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_LAPIS_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_COAL_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_COPPER_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_IRON_ORE)
            .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, ModPlacedFeatureProvider.GLACIO_DEEPSLATE_LAPIS_ORE);
    }


    private static BiomeGenerationSettings.Builder glacioIcePeaks(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return glacio(placedFeatures, configuredCarvers)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE)
            .addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);
    }

    private static BiomeGenerationSettings.Builder caves(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers) {
        return new BiomeGenerationSettings.Builder(placedFeatures, configuredCarvers)
            .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE)
            .addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND)
            .addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        return biome(hasPrecipitation, temperature, downfall, skyColor, fogColor, null, 0x3f76e4, 0x50533, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, @Nullable AmbientParticleSettings particles, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        return biome(hasPrecipitation, temperature, downfall, skyColor, fogColor, particles, 0x3f76e4, 0x50533, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int skyColor, int fogColor, AmbientParticleSettings particles, int waterColor, int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        var specoalEffectsBuilder = (new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(skyColor).skyColor(fogColor).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(backgroundMusic);
        if (grassColorOverride != null) {
            specoalEffectsBuilder.grassColorOverride(grassColorOverride);
        }

        if (particles != null) {
            specoalEffectsBuilder.ambientParticle(particles);
        }

        if (foliageColorOverride != null) {
            specoalEffectsBuilder.foliageColorOverride(foliageColorOverride);
        }

        return (new Biome.BiomeBuilder()).hasPrecipitation(hasPrecipitation).temperature(temperature).downfall(downfall).specialEffects(specoalEffectsBuilder.build()).mobSpawnSettings(mobSpawnSettings.build()).generationSettings(generationSettings.build()).build();
    }
}
