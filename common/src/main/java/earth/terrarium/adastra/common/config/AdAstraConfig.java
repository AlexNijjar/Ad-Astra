package earth.terrarium.adastra.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import earth.terrarium.adastra.common.config.info.AdAstraConfigInfo;

@Config(
    value = "ad_astra",
    categories = {
        MachineConfig.class
    }
)
@ConfigInfo.Provider(AdAstraConfigInfo.class)
public final class AdAstraConfig {

    @ConfigEntry(
        id = "allowFlagImages",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.allowFlagImages"
    )
    @Comment("Allow players to set custom flag images for their rockets.")
    public static boolean allowFlagImages = true;

    @ConfigEntry(
        id = "launchAnywhere",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.launchFromAnywhere"
    )
    @Comment("Allow rockets to be launched from any dimension, even if it's not considered a planet.")
    public static boolean launchFromAnywhere;

    @ConfigEntry(
        id = "planetRandomTickSpeed",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.planetRandomTickSpeed"
    )
    @Comment("The random tick speed for breaking plants, torches, freezing water, etc. on planets.")
    public static int planetRandomTickSpeed = 20;

    @ConfigEntry(
        id = "forcePlanetTick",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.forcePlanetTick"
    )
    @Comment("Always tick every planet chunk for things like freezing water, breaking plants, etc., regardless of whether the chunk can tick randomly or not. This has a small performance impact.")
    public static boolean forcePlanetTick;

    @ConfigEntry(
        id = "atmosphereLeave",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.atmosphereLeave"
    )
    @Comment("The y level where rockets should leave the dimension and enter space.")
    public static int atmosphereLeave = 600;

    @ConfigEntry(
        id = "disabledPlanets",
        type = EntryType.STRING,
        translation = "config.ad_astra.disabledPlanets"
    )
    @Comment("A comma-separated list of planet IDs that should be hidden from the planets screen. e.g. minecraft:overworld,ad_astra:moon,ad_astra:mars,ad_astra:venus,ad_astra:mercury,ad_astra:glacio")
    public static String disabledPlanets = "";

    @ConfigEntry(
        id = "disableOxygen",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.disableOxygen"
    )
    @Comment("Disables oxygen damage.")
    public static boolean disableOxygen;

    @ConfigEntry(
        id = "disableTemperature",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.disableTemperature"
    )
    @Comment("Disables temperature damage.")
    public static boolean disableTemperature;

    @ConfigEntry(
        id = "disableGravity",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.disableGravity"
    )
    @Comment("Uses normal gravity for all planets.")
    public static boolean disableGravity;

    @ConfigEntry(
        id = "disableAirVortexes",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.disableAirVortexes"
    )
    @Comment("An Air Vortex is created when an oxygenated structure breaks its seal, causing every entity inside to rapidly get sucked out. This setting disables that.")
    public static boolean disableAirVortexes;

    @ConfigEntry(
        id = "launchFuelCost",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.launchFuelCost"
    )
    @Comment("The amount of MBs of fuel needed to launch a rocket")
    public static int launchFuelCost = 3000;

    @ConfigEntry(
        id = "launchEfficientFuelCost",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.launchEfficientFuelCost"
    )
    @Comment("The amount of MBs of efficient fuel (like cryo fuel) needed to launch a rocket")
    public static int launchEfficientFuelCost = 1000;
}
