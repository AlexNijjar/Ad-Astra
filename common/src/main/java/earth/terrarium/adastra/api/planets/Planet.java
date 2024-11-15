package earth.terrarium.adastra.api.planets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.utils.ButtonColor;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

public record Planet(
    String translation,
    ResourceLocation galaxy,
    ResourceLocation solarSystem,
    ResourceKey<Level> dimension,
    Optional<ResourceKey<Level>> orbit,
    Optional<ResourceKey<Level>> parent_world,
    int tier,
    float gravity,
    float daysInYear,
    short temperature,
    int solarPower,
    boolean oxygen,
    ButtonColor buttonColor,
    List<ResourceKey<Level>> additionalLaunchDimensions
) {



    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("translation").forGetter(Planet::translation),
        ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Planet::galaxy),
        ResourceLocation.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Planet::dimension),
        ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("orbit").forGetter(Planet::orbit),
        ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("parent_world").forGetter(Planet::parent_world),
        Codec.INT.fieldOf("tier").forGetter(Planet::tier),
        Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
        Codec.FLOAT.fieldOf("days_in_year").forGetter(Planet::daysInYear),
        Codec.SHORT.fieldOf("temperature").forGetter(Planet::temperature),
        Codec.INT.fieldOf("solar_power").forGetter(Planet::solarPower),
        Codec.BOOL.fieldOf("oxygen").forGetter(Planet::oxygen),
        ButtonColor.CODEC.fieldOf("button_color").forGetter(Planet::buttonColor),
        ResourceKey.codec(Registries.DIMENSION).listOf().optionalFieldOf("additional_launch_dimensions", List.of()).forGetter(Planet::additionalLaunchDimensions)
    ).apply(instance, Planet::new));

    public ResourceKey<Level> orbitIfPresent() {
        return orbit.orElse(dimension);
    }

    public boolean isSpace() {
        return orbit.isEmpty();
    }

    public ResourceKey<Level> getOrbitPlanet() {
        for (var planet : AdAstraData.planets().values()) {
            if (planet.orbit().isEmpty()) continue;
            if (planet.orbit().equals(dimension)) return planet.dimension();
        }
        return null;
    }



}
