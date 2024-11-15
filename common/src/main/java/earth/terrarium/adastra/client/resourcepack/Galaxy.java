package earth.terrarium.adastra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.client.utils.ButtonColor;
import net.minecraft.resources.ResourceLocation;

public record Galaxy(ResourceLocation galaxy, ResourceLocation texture, ButtonColor buttonColor, int scale) {
    public static final Codec<Galaxy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Galaxy::galaxy),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Galaxy::texture),
            ButtonColor.CODEC.fieldOf("button_color").forGetter(Galaxy::buttonColor),
            Codec.INT.fieldOf("scale").orElse(1).forGetter(Galaxy::scale)
    ).apply(instance, Galaxy::new));
}