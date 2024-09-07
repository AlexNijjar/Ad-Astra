package earth.terrarium.adastra.client.utils;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.mixins.client.LevelRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class DimensionRenderingUtils {

    public static final ResourceLocation BACKLIGHT = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/backlight.png");

    public static final ResourceLocation ACID_RAIN = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/acid_rain.png");
    public static final ResourceLocation VENUS_CLOUDS = new ResourceLocation(AdAstra.MOD_ID, "textures/environment/venus_clouds.png");


    public static final List<ResourceLocation> SOLAR_SYSTEM_TEXTURES = List.of(
    );

    public static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
