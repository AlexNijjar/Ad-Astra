package earth.terrarium.adastra.common.utils;

public class MathUtil {

    public static float invLerp(float delta, float start, float end) {
        return (delta - end) / (start - end);
    }
}
