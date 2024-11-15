package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.utils.ButtonColor;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


public class ModPlanetProvider extends ModCodecProvider<Planet> {
    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(AdAstra.MOD_ID, "planets"));

    public ModPlanetProvider(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PLANET_REGISTRY);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, Planet> consumer) {
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth_orbit")), 32, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 1, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon_orbit")), 24, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 1, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars_orbit")), 24, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 2, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus_orbit")), 48, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 3, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury_orbit")), 72, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 3, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio_orbit")),36, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 4, new ResourceLocation(AdAstra.MOD_ID, "preoxima_centauri"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos_orbit")),36,new ResourceLocation(AdAstra.MOD_ID, "andromeda"), 4, new ResourceLocation(AdAstra.MOD_ID, "pytheas"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e_orbit")),36, new ResourceLocation(AdAstra.MOD_ID, "milky_way"),4, new ResourceLocation(AdAstra.MOD_ID, "trappist_1"));
        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos_orbit")),36, new ResourceLocation(AdAstra.MOD_ID, "milky_way"), 4, new ResourceLocation(AdAstra.MOD_ID, "solar_system"));

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "earth"),
            new Planet(
                "gui.ad_astra.text.earth",
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                Level.OVERWORLD,
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth_orbit"))),
                Optional.empty(),
                1,
                9.807F,
                365,
                (short) 15,
                16,
                true,
                ButtonColor.GREEN,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "moon"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon_orbit"))),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth"))),
                1,
                1.622F,
                27,
                (short) -173,
                24,
                false,
                ButtonColor.GREY,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "mars"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars_orbit"))),
                Optional.empty(),
                2,
                3.7207F,
                687,
                (short) -65,
                12,
                false,
                ButtonColor.RED,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "deimos"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos_orbit"))),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars"))),
                1,
                .003F,
                1.1F,
                (short) -40.15F,
                24,
                false,
                ButtonColor.BLUE,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "mercury"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury_orbit"))),
                Optional.empty(),
                3,
                3.7F,
                88,
                (short) 167,
                64,
                false,
                ButtonColor.RED,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "venus"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "solar_system"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus_orbit"))),
                Optional.empty(),
                3,
                8.87F,
                225,
                (short) 464,
                8,
                false,
                ButtonColor.ORANGE,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "glacio"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "proxima_centauri"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio_orbit"))),
                Optional.empty(),
                4,
                3.721F,
                365,
                (short) -20,
                14,
                false,
                ButtonColor.LIGHT_BLUE,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "milky_way"),
                new ResourceLocation(AdAstra.MOD_ID, "trappist_1"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e_orbit"))),
                Optional.empty(),
                4,
                9.1205F,
                6,
                (short) 15,
                14,
                true,
                ButtonColor.GREEN,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "eos"),
            new Planet(
                "gui." + AdAstra.MOD_ID.toString() + ".text." + ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos")).location().getPath().toString(),
                new ResourceLocation(AdAstra.MOD_ID, "andromeda"),
                new ResourceLocation(AdAstra.MOD_ID, "pytheas"),
                ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos")),
                Optional.of(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos_orbit"))),
                Optional.empty(),
                3,
                3.72076F,
                687,
                (short) -65,
                14,
                false,
                ButtonColor.LIGHT_BLUE,
                List.of()
            )
        );

    }

    private static void orbit(BiConsumer<ResourceLocation, Planet> consumer, ResourceKey<Level> planet, int solarPower, ResourceLocation galaxy, int tier, ResourceLocation solarSystem) {
        consumer.accept(
            planet.location(),
            new Planet(
                planet.location().toString(),
                galaxy,
                solarSystem,
                planet,
                Optional.empty(),
                Optional.empty(),
                tier,
                0.0F,
                0,
                (short) -270,
                solarPower,
                false,
                ButtonColor.BLACK,
                List.of()
            )
        );

    }

    @Override
    public @NotNull String getName() {
        return "Planets";
    }
}
