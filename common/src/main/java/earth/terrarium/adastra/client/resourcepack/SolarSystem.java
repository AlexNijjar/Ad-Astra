package earth.terrarium.adastra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.adastra.client.utils.ButtonColor;
import net.minecraft.resources.ResourceLocation;

public record SolarSystem(ResourceLocation galaxy, ResourceLocation solarSystem, ResourceLocation sun, int sunScale,
                          ButtonColor buttonColor, Color ringColour) {

    public static final Codec<SolarSystem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(SolarSystem::galaxy),
            ResourceLocation.CODEC.fieldOf("solar_system").forGetter(SolarSystem::solarSystem),
            ResourceLocation.CODEC.fieldOf("sun").forGetter(SolarSystem::sun),
            Codec.INT.fieldOf("sun_scale").forGetter(SolarSystem::sunScale),
            ButtonColor.CODEC.fieldOf("button_color").forGetter(SolarSystem::buttonColor),
            Color.CODEC.fieldOf("ring_color").forGetter(SolarSystem::ringColour)
    ).apply(instance, SolarSystem::new));

}