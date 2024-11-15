package earth.terrarium.adastra.datagen.provider.client;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.client.dimension.MovementType;
import earth.terrarium.adastra.client.dimension.PlanetRenderer;
import earth.terrarium.adastra.client.dimension.SkyRenderable;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


public class ModPlanetRendererProvider extends ModCodecProvider<PlanetRenderer> {
    public static final ResourceKey<Registry<PlanetRenderer>> PLANET_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(AdAstra.MOD_ID, "planet_renderers"));

    public static final int DEFAULT_SUNRISE_COLOR = 0xd85f33;

    public static final SimpleWeightedRandomList<Integer> COLORED_STARS = SimpleWeightedRandomList.<Integer>builder()
        .add(0xA9BCDFFF, 3)   // Blue
        .add(0xBBD7FFFF, 5)   // Blue-White,
        .add(0xFFF4E8FF, 100) // Yellow-White
        .add(0xFFD1A0FF, 80)  // Orange
        .add(0xFF8A8AFF, 150) // Red
        .add(0xFF4500FF, 10)  // Orange-Red
        .add(0xFFFFF4FF, 60)  // White
        .add(0xFFF8E7FF, 40)  // Pale Yellow
        .add(0xFFFFFF00, 20)  // Very Pale Yellow
        .add(0xFFFF0000, 1)   // Bright Red
        .build();

    public static final SimpleWeightedRandomList<Integer> DEFAULT_STARS = SimpleWeightedRandomList.<Integer>builder()
        .add(0xffffffff, 1)
        .build();

    public ModPlanetRendererProvider(PackOutput packOutput) {
        super(packOutput, PlanetRenderer.CODEC, PLANET_REGISTRY, PackOutput.Target.RESOURCE_PACK);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, PlanetRenderer> consumer) {

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png"), 0xff3c7cda, 10,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/moon.png"),
                8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffafb8cc));

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/moon.png"), 0xffafb8cc, 9,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png"),
                14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda));

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mars.png"), 0xffb6552b, 7,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/deimos.png"),
                8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffb6552b));

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/deimos.png"), 0xffb6552b, 7,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mars.png"),
                8, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xffb6552b));

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/venus.png"), 0xfff3c476, 14);

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mercury.png"), 0xffab6989, 22);

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/glacio.png"), 0xffced7ec, 9,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/vicinus.png"),
                50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, 0xff974cb8));


        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/deimos.png"), 0xffafb8cc, 9,
            new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mars.png"),
                14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda));


        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/eos.png"), 0xffab6989, 10);

        orbit(consumer, ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e_orbit")),
            new ResourceLocation(AdAstra.MOD_ID, "textures/environment/trappist_1_e.png"), 0xffab6989, 10);



        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon")),
            true,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            13000,
            Optional.of(0.6f),
            0,
            true,
            COLORED_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/sun.png"), 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png"), 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars")),
            true,
            true,
            false,
            false,
            true,
            0x1fbbff,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/blue_sun.png"), 7, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffd0faf7),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/phobos.png"), 2, new Vec3(20, 20, 180), new Vec3(0, 0, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffb4908d),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/deimos.png"), 1.5f, new Vec3(0, 0, 180), new Vec3(0, 20, 0), MovementType.TIME_OF_DAY, true, 0xffe5d5ad),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png"), 0.4f, new Vec3(-40, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xff3c7cda)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus")),
            false,
            true,
            false,
            true,
            true,
            0xf9c21a,
            1500,
            Optional.empty(),
            180,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/red_sun.png"), 14, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY_REVERSED, true, 0xfff48c61),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/earth.png"), 0.5f, new Vec3(-20, 0, 160), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xff3c7cda),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mercury.png"), 0.3f, new Vec3(10, 0, -10), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY_REVERSED, true, 0xffab6989)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury")),
            true,
            true,
            false,
            false,
            true,
            0xd63a0b,
            9000,
            Optional.of(0.6f),
            0,
            true,
            COLORED_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/red_sun.png"), 22, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xfff48c61),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/venus.png"), 0.4f, new Vec3(-15, 0, 140), new Vec3(0, 100, 0), MovementType.TIME_OF_DAY, true, 0xfff3c476)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio")),
            false,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/sun.png"), 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/vicinus.png"), 50, new Vec3(60, 0, 5), new Vec3(0, 0, -5), MovementType.STATIC, false, 0xff974cb8)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "trappist_1_e")),
            false,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/red_sun.png"), 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "eos")),
            false,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            1500,
            Optional.empty(),
            0,
            false,
            DEFAULT_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/red_sun.png"), 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, true, 0xffffffd9)
            )));

        consumer.accept(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos")).location(), new PlanetRenderer(
            ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "deimos")),
            true,
            true,
            false,
            false,
            true,
            DEFAULT_SUNRISE_COLOR,
            13000,
            Optional.of(0.6f),
            0,
            true,
            COLORED_STARS,
            List.of(
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/sun.png"), 9, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9),
                new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/mars.png"), 14, new Vec3(80, 0, 30), new Vec3(0, -5, 0), MovementType.STATIC, 0xff3c7cda)
            )));

    }

    private static void orbit(BiConsumer<ResourceLocation, PlanetRenderer> consumer, ResourceKey<Level> planet, ResourceLocation planetTexture, int backlightColor, int sunScale, SkyRenderable... additionalRenderables) {
        List<SkyRenderable> renderables = new ArrayList<>();
        renderables.add(new SkyRenderable(new ResourceLocation(AdAstra.MOD_ID, "textures/environment/sun.png"), sunScale, Vec3.ZERO, Vec3.ZERO, MovementType.TIME_OF_DAY, 0xffffffd9));
        renderables.add(new SkyRenderable(planetTexture, 80, new Vec3(180, 0, 0), Vec3.ZERO, MovementType.STATIC, backlightColor));
        renderables.addAll(List.of(additionalRenderables));
        consumer.accept(
            planet.location(),
            new PlanetRenderer(
                planet,
                true,
                true,
                false,
                false,
                false,
                DEFAULT_SUNRISE_COLOR,
                13000,
                Optional.of(0.6f),
                0,
                true,
                COLORED_STARS,
                renderables
            ));
    }

    @Override
    public @NotNull String getName() {
        return "Planet Renderers";
    }
}
