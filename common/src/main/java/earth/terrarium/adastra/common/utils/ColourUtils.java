package earth.terrarium.adastra.common.utils;

import com.teamresourceful.resourcefullib.common.color.Color;
import net.minecraft.world.phys.Vec3;

public final class ColourUtils {

    public static Color lighten(Color colour, float amount) {
        return new Color((int) (colour.getIntRed() + amount * 255),
                (int) (colour.getIntGreen() + amount * 255),
                (int) (colour.getIntBlue() + amount * 255),
                colour.getIntAlpha());
    }

    public static Vec3 toVector(Color colour) {
        return new Vec3(colour.getIntRed() / 255.0f, colour.getIntGreen() / 255.0f, colour.getIntBlue() / 255.0f);
    }
}
