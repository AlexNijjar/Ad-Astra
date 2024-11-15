package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class ModDimensionTypeProvider {
    public static final ResourceKey<DimensionType> EARTH_ORBIT = register("earth_orbit");
    public static final ResourceKey<DimensionType> MOON_ORBIT = register("moon_orbit");
    public static final ResourceKey<DimensionType> MARS_ORBIT = register("mars_orbit");
    public static final ResourceKey<DimensionType> VENUS_ORBIT = register("venus_orbit");
    public static final ResourceKey<DimensionType> MERCURY_ORBIT = register("mercury_orbit");
    public static final ResourceKey<DimensionType> GLACIO_ORBIT = register("glacio_orbit");
    public static final ResourceKey<DimensionType> DEIMOS_ORBIT = register("deimos_orbit");
    public static final ResourceKey<DimensionType> EOS_ORBIT = register("eos_orbit");
    public static final ResourceKey<DimensionType> TRAPPIST_1_E_ORBIT = register("trappist_1_e_orbit");

    public static final ResourceKey<DimensionType> MOON = register("moon");
    public static final ResourceKey<DimensionType> MARS = register("mars");
    public static final ResourceKey<DimensionType> VENUS = register("venus");
    public static final ResourceKey<DimensionType> MERCURY = register("mercury");
    public static final ResourceKey<DimensionType> GLACIO = register("glacio");
    public static final ResourceKey<DimensionType> DEIMOS = register("deimos");
    public static final ResourceKey<DimensionType> EOS = register("eos");
    public static final ResourceKey<DimensionType> TRAPPIST_1_E = register("trappist_1_e");

    private static ResourceKey<DimensionType> register(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DimensionType> context) {
        orbit(context, EARTH_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth_orbit")).location());
        orbit(context, MOON_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon_orbit")).location());
        orbit(context, MARS_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars_orbit")).location());
        orbit(context, VENUS_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus_orbit")).location());
        orbit(context, MERCURY_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury_orbit")).location());
        orbit(context, GLACIO_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio_orbit")).location());
        orbit(context, DEIMOS_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos_orbit")).location());
        orbit(context, EOS_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos_orbit")).location());
        orbit(context, TRAPPIST_1_E_ORBIT, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e_orbit")).location());

        context.register(
            MOON,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            DEIMOS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            MARS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            VENUS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus")).location(),
                0.0f,
                createMonsterSettings(
                    true,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            MERCURY,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury")).location(),
                0.0f,
                createMonsterSettings(
                    true,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            GLACIO,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            EOS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            TRAPPIST_1_E,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e")).location(),
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));


    }

    private static void orbit(BootstapContext<DimensionType> context, ResourceKey<DimensionType> key, ResourceLocation dimensionSpecialEffects) {
        context.register(
            key,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                dimensionSpecialEffects,
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static DimensionType create(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, ResourceLocation effectsLocation, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkyLight, hasCeiling, ultraWarm, natural, coordinateScale, bedWorks, respawnAnchorWorks, minY, height, logicalHeight, infiniburn, effectsLocation, ambientLight, monsterSettings);
    }

    public static DimensionType.MonsterSettings createMonsterSettings(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
