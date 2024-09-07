package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensionProvider {
    public static final ResourceKey<LevelStem> EARTH_ORBIT = register("earth_orbit");
    public static final ResourceKey<LevelStem> MOON_ORBIT = register("moon_orbit");
    public static final ResourceKey<LevelStem> MARS_ORBIT = register("mars_orbit");
    public static final ResourceKey<LevelStem> VENUS_ORBIT = register("venus_orbit");
    public static final ResourceKey<LevelStem> MERCURY_ORBIT = register("mercury_orbit");
    public static final ResourceKey<LevelStem> GLACIO_ORBIT = register("glacio_orbit");
    public static final ResourceKey<LevelStem> EOS_ORBIT = register("eos_orbit");
    public static final ResourceKey<LevelStem> DEIMOS_ORBIT = register("deimos_orbit");
    public static final ResourceKey<LevelStem> TRAPPIST_1_E_ORBIT = register("trappist_1_e_orbit");

    public static final ResourceKey<LevelStem> MOON = register("moon");
    public static final ResourceKey<LevelStem> MARS = register("mars");
    public static final ResourceKey<LevelStem> VENUS = register("venus");
    public static final ResourceKey<LevelStem> MERCURY = register("mercury");
    public static final ResourceKey<LevelStem> GLACIO = register("glacio");
    public static final ResourceKey<LevelStem> EOS = register("eos");
    public static final ResourceKey<LevelStem> DEIMOS = register("deimos");
    public static final ResourceKey<LevelStem> TRAPPIST_1_E = register("trappist_1_e");

    private static ResourceKey<LevelStem> register(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    private static ResourceKey<NoiseGeneratorSettings> registerNoise(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        space(context, EARTH_ORBIT, ModDimensionTypeProvider.EARTH_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, MOON_ORBIT, ModDimensionTypeProvider.MOON_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, MARS_ORBIT, ModDimensionTypeProvider.MARS_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, VENUS_ORBIT, ModDimensionTypeProvider.VENUS_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, MERCURY_ORBIT, ModDimensionTypeProvider.MERCURY_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, GLACIO_ORBIT, ModDimensionTypeProvider.GLACIO_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, EOS_ORBIT, ModDimensionTypeProvider.EOS_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, TRAPPIST_1_E_ORBIT, ModDimensionTypeProvider.TRAPPIST_1_E_ORBIT, dimensionTypes, biomes, noiseSettings);
        space(context, DEIMOS_ORBIT, ModDimensionTypeProvider.DEIMOS_ORBIT, dimensionTypes, biomes, noiseSettings);
    }

    private static void space(BootstapContext<LevelStem> context, ResourceKey<LevelStem> key, ResourceKey<DimensionType> type, HolderGetter<DimensionType> dimensionTypes, HolderGetter<Biome> biomes, HolderGetter<NoiseGeneratorSettings> noiseSettings) {
        context.register(key, new LevelStem(
            dimensionTypes.getOrThrow(type),
            new NoiseBasedChunkGenerator(
                new FixedBiomeSource(
                    biomes.getOrThrow(ModBiomeDataProvider.SPACE)),
                noiseSettings.getOrThrow(ModNoiseGeneratorSettingsProvider.SPACE))));
    }
}
