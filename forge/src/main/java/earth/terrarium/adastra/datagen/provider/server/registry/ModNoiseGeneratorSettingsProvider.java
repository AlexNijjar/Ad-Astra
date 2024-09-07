package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;

import java.util.List;

public class ModNoiseGeneratorSettingsProvider {
    public static final ResourceKey<NoiseGeneratorSettings> SPACE = register("orbit");
    public static final ResourceKey<NoiseGeneratorSettings> MOON = register("moon");
    public static final ResourceKey<NoiseGeneratorSettings> MARS = register("mars");
    public static final ResourceKey<NoiseGeneratorSettings> VENUS = register("venus");
    public static final ResourceKey<NoiseGeneratorSettings> MERCURY = register("mercury");
    public static final ResourceKey<NoiseGeneratorSettings> GLACIO = register("glacio");
    public static final ResourceKey<NoiseGeneratorSettings> DEIMOS = register("deimos");
    public static final ResourceKey<NoiseGeneratorSettings> EOS = register("eos");
    public static final ResourceKey<NoiseGeneratorSettings> TRAPPIST_1_E = register("trappist_1_e");

    protected static final NoiseSettings SIMPLE_NOISE_SETTINGS = NoiseSettings.create(0, 256, 2, 1);

    private static ResourceKey<NoiseGeneratorSettings> register(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(SPACE, space());
    }

    public static NoiseGeneratorSettings space() {
        return create(
            SIMPLE_NOISE_SETTINGS,
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            none(),
            SurfaceRuleData.air(),
            List.of(),
            0,
            true,
            false,
            false,
            false
        );
    }

    public static NoiseGeneratorSettings create(NoiseSettings noiseSettings, BlockState defaultBlock, BlockState defaultFluid, NoiseRouter noiseRouter, SurfaceRules.RuleSource surfaceRule, List<Climate.ParameterPoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {
        return new NoiseGeneratorSettings(noiseSettings, defaultBlock, defaultFluid, noiseRouter, surfaceRule, spawnTarget, seaLevel, disableMobGeneration, aquifersEnabled, oreVeinsEnabled, useLegacyRandomSource);
    }

    public static NoiseRouter none() {
        return create(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
        );
    }

    public static NoiseRouter create(
        DensityFunction barrierNoise,
        DensityFunction fluidLevelFloodednessNoise,
        DensityFunction fluidLevelSpreadNoise,
        DensityFunction lavaNoise,
        DensityFunction temperature,
        DensityFunction vegetation,
        DensityFunction continents,
        DensityFunction erosion,
        DensityFunction depth,
        DensityFunction ridges,
        DensityFunction initialDensityWithoutJaggedness,
        DensityFunction finalDensity,
        DensityFunction veinToggle,
        DensityFunction veinRidged,
        DensityFunction veinGap) {
        return new NoiseRouter(barrierNoise, fluidLevelFloodednessNoise, fluidLevelSpreadNoise, lavaNoise, temperature, vegetation, continents, erosion, depth, ridges, initialDensityWithoutJaggedness, finalDensity, veinToggle, veinRidged, veinGap);
    }
}
