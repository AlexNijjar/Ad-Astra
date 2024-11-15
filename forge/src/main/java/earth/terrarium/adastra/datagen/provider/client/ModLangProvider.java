package earth.terrarium.adastra.datagen.provider.client;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.constants.ConstantComponents;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModEntityTypes;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.botarium.common.registry.fluid.BotariumFlowingFluid;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import org.codehaus.plexus.util.StringUtils;

import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, AdAstra.MOD_ID, "en_us");
    }

    private static final Set<RegistryEntry<Item>> CUSTOM_ITEM_NAMES = Set.of(ModItems.TI_69);
    private static final Set<RegistryEntry<Block>> CUSTOM_BLOCK_NAMES = Set.of();

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream()
            .filter(b -> !(CUSTOM_BLOCK_NAMES.contains(b)))
            .forEach(entry -> addBlock(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModItems.ITEMS.stream()
            .filter(i -> !(i.get() instanceof BlockItem))
            .filter(i -> !(CUSTOM_ITEM_NAMES.contains(i)))
            .forEach(entry -> addItem(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModEntityTypes.ENTITY_TYPES.stream()
            .forEach(entry -> addEntityType(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModFluids.FLUIDS.stream()
            .forEach(entry -> addFluid(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        add("itemGroup.ad_astra.main", "Ad Astra");

        add("entity.ad_astra.lunarian.armorer", "Lunarian Armorer");
        add("entity.ad_astra.lunarian.butcher", "Lunarian Butcher");
        add("entity.ad_astra.lunarian.cartographer", "Lunarian Cartographer");
        add("entity.ad_astra.lunarian.cleric", "Lunarian Cleric");
        add("entity.ad_astra.lunarian.farmer", "Lunarian Farmer");
        add("entity.ad_astra.lunarian.fisherman", "Lunarian Fisherman");
        add("entity.ad_astra.lunarian.fletcher", "Lunarian Fletcher");
        add("entity.ad_astra.lunarian.leatherworker", "Lunarian Leatherworker");
        add("entity.ad_astra.lunarian.librarian", "Lunarian Librarian");
        add("entity.ad_astra.lunarian.mason", "Lunarian Mason");
        add("entity.ad_astra.lunarian.none", "Lunarian");
        add("entity.ad_astra.lunarian.shepherd", "Lunarian Shepherd");
        add("entity.ad_astra.lunarian.toolsmith", "Lunarian Toolsmith");
        add("entity.ad_astra.lunarian.weaponsmith", "Lunarian Weaponsmith");

        addItem(ModItems.TI_69, "TI-69");
        add("item.ad_astra.astrodux", "Astrodux");
        add("item.ad_astra.astrodux.landing", "Welcome to your guide to the depths of Space.$(br2)Ad Astra is in development. Some features in the guide may be missing or incomplete.");

        add("death.attack.oxygen", "%1$s couldn't breathe anymore");
        add("death.attack.oxygen.player", "%1$s lost their breath whilst trying to escape %2$s");

        add("death.attack.cryo_fuel", "%1$s became an ice cube");
        add("death.attack.cryo_fuel.player", "%1$s became an ice cube whilst trying to escape %2$s");

        add("death.attack.ran_over", "%1$s was ran over");
        add("death.attack.ran_over.player", "%1$s was ran over by %2$s");

        add("death.attack.rocket_flames", "%1$s melted under an active rocket");
        add("death.attack.rocket_flames.player", "%1$s melted under an active rocket whilst trying to escape %2$s");

        add("death.attack.acid_rain", "%1$s was melted by acid rain");
        add("death.attack.acid_rain.player", "%1$s was melted by acid rain whilst trying to escape %2$s");

        add(ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY, "Toggle Suit Flight");
        add(ConstantComponents.OPEN_RADIO_KEY, "Open radio");
        add(ConstantComponents.AD_ASTRA_CATEGORY, "Ad Astra");

        add(ConstantComponents.SUIT_FLIGHT_ENABLED, "Suit flight enabled");
        add(ConstantComponents.SUIT_FLIGHT_DISABLED, "Suit flight disabled");

        add("message.ad_astra.hold_to_dismount", "Hold SHIFT for %.1f seconds to dismount");

        add(ConstantComponents.TRUE, "✔");
        add(ConstantComponents.FALSE, "✘");
        add("text.ad_astra.temperature", "%s°C");
        add("text.ad_astra.gravity", "%sm/s²");
        add("text.ad_astra.radio.none", "No station");

        add("text.ad_astra.weather.temperature", "Now: %s°C");
        add("text.ad_astra.weather.temperature.in", "In: %s");

        add("tooltip.ad_astra.energy", "%s ⚡ / %s ⚡");
        add("tooltip.ad_astra.energy_in", "In: %s ⚡/t");
        add("tooltip.ad_astra.energy_out", "Out: %s ⚡/t");
        add("tooltip.ad_astra.max_energy_in", "Max In: %s ⚡/t");
        add("tooltip.ad_astra.max_energy_out", "Max Out: %s ⚡/t");
        add("tooltip.ad_astra.energy_use_per_tick", "Uses %s ⚡ per tick");
        add("tooltip.ad_astra.energy_generation_per_tick", "Generates %s ⚡ per tick");

        add("tooltip.ad_astra.fluid", "%s 🪣 / %s 🪣 %s");
        add("tooltip.ad_astra.fluid_in", "In: %s 🪣/t");
        add("tooltip.ad_astra.fluid_out", "Out: %s 🪣/t");
        add("tooltip.ad_astra.max_fluid_in", "Max In: %s 🪣/t");
        add("tooltip.ad_astra.max_fluid_out", "Max Out: %s 🪣/t");
        add("tooltip.ad_astra.fluid_use_per_iteration", "Uses %s 🪣 per iteration");
        add("tooltip.ad_astra.fluid_generation_per_iteration", "Generates %s 🪣 per iteration");

        add("tooltip.ad_astra.ticks_per_iteration", "Takes %s ticks per iteration");

        add("tooltip.ad_astra.progress", "%s / %s");
        add("tooltip.ad_astra.eta", "ETA: %s seconds");

        add("tooltip.ad_astra.energy_per_tick", "%s ⚡/t");
        add("tooltip.ad_astra.max_generation", "Max: %s ⚡/t");

        add("tooltip.ad_astra.fluid_per_tick", "%s 🪣/t");

        add("tooltip.ad_astra.blocks_distributed", "%s/%s");

        add("tooltip.ad_astra.gravity_amount", "%.1f/%.1f m/s²");

        add(ConstantComponents.OXYGEN_DISTRIBUTION_AREA, "Show where the oxygen is being distributed");
        add(ConstantComponents.GRAVITY_DISTRIBUTION_AREA, "Show where the gravity is being distributed");

        add(ConstantComponents.CLEAR_FLUID_TANK, "Shift-right-click to clear");

        add("message.ad_astra.lander.onboard", "Hold %1$s to Apply Boosters");

        add(ConstantComponents.SIDE_CONFIG, "Side Config");
        add(ConstantComponents.REDSTONE_CONTROL, "Redstone Control");

        add("tooltip.ad_astra.redstone_control.always_on", "Always on");
        add("tooltip.ad_astra.redstone_control.on_when_powered", "On when powered");
        add("tooltip.ad_astra.redstone_control.on_when_not_powered", "On when not powered");
        add("tooltip.ad_astra.redstone_control.never_on", "Never on");
        add("tooltip.ad_astra.redstone_control.mode", "Mode: %s");

        add(ConstantComponents.ETRIONIC_BLAST_FURNACE_MODE, "Mode");
        add("tooltip.ad_astra.etrionic_blast_furnace.mode.alloying", "Alloying");
        add("tooltip.ad_astra.etrionic_blast_furnace.mode.blasting", "Blasting");
        add("tooltip.ad_astra.etrionic_blast_furnace.mode", "Mode: %s");

        add(ConstantComponents.ACTIVE, "Active");
        add(ConstantComponents.INACTIVE, "Inactive");

        add(ConstantComponents.SEQUENTIAL, "Sequential");
        add(ConstantComponents.ROUND_ROBIN, "Round Robin");

        add(ConstantComponents.CAPACITOR_ENABLED, "Capacitor enabled");
        add(ConstantComponents.CAPACITOR_DISABLED, "Capacitor disabled");
        add(ConstantComponents.CHANGE_MODE_SEQUENTIAL, "Set capacitor mode to \"Sequential\"");
        add(ConstantComponents.CHANGE_MODE_ROUND_ROBIN, "Set capacitor mode to \"Round Robin\"");

        add(ConstantComponents.SHIFT_DESCRIPTION, "Hold SHIFT for more information");
        add(ConstantComponents.TI_69_INFO, "Displays local oxygen, temperature, and gravity information.");
        add(ConstantComponents.ETRIONIC_CAPACITOR_INFO, "Stores energy. Right-click to toggle. Shift-right-click to change the distribution mode.");
        add(ConstantComponents.WRENCH_INFO, "Used to configure pipes and various other blocks.");
        add(ConstantComponents.ZIP_GUN_INFO, "Propels you forward. Use a second one in your offhand to propel even further. Excellent for maneuvering in zero-gravity.");
        add(ConstantComponents.GAS_TANK_INFO, "Stores fluids and gases. Right-click to distribute into your inventory.");
        add(ConstantComponents.CABLE_INFO, "Transfers energy. Right-click with a wrench to change the distribution mode.");
        add(ConstantComponents.FLUID_PIPE_INFO, "Transfers fluids. Right-click with a wrench to change the distribution mode.");
        add(ConstantComponents.CABLE_DUCT, "Works like cables, but fully-conceals oxygen, preventing leaks.");
        add(ConstantComponents.FLUID_DUCT_INFO, "Works like fluid pipes, but fully-conceals oxygen, preventing leaks.");
        add(ConstantComponents.SPACE_SUIT_INFO, "Supplies the wearer with oxygen and protects them from the extreme-cold of space.");
        add(ConstantComponents.NETHERITE_SPACE_SUIT_INFO, "Works like the Space Suit, with the added advantage of protecting the wearer from extreme heat.");
        add(ConstantComponents.JET_SUIT_INFO, "Works like the Netherite Space Suit, with jets installed to propel the wearer forward with extreme force.");
        add(ConstantComponents.SLIDING_DOOR_INFO, "A large 3x3 door. Shift-right-click with a wrench to lock it. Locked doors can only be opened with redstone.");
        add(ConstantComponents.FLAG_INFO, "A flag with your face on it. Right-click to add an image from a URL.");
        add(ConstantComponents.LAUNCH_PAD_INFO, "Launches rockets. Power with redstone to automatically launch placed rockets.");
        add(ConstantComponents.GLOBE_INFO, "Right-click to spin the globe. Power it with redstone to make it constantly spin.");
        add(ConstantComponents.VENT_INFO, "Allows oxygen to flow through.");
        add(ConstantComponents.RADIO_INFO, "Plays real internet-streamed radio stations.");

        add(ConstantComponents.ROCKET_INFO, "A rocket used for interplanetary travel. Must be placed on a launch pad. Shift-right-click to access its fuel and inventory.");
        add(ConstantComponents.ROVER_INFO, "An all-terrain vehicle. Shift-right-click to access its inventory. Click on the screen to access the rover's radio. This radio can play real radio stations.");

        add(ConstantComponents.COAL_GENERATOR_INFO, "The simplest of generators. Generates energy from burnable materials.");
        add(ConstantComponents.COMPRESSOR_INFO, "Compresses materials into plates.");
        add(ConstantComponents.ETRIONIC_BLAST_FURNACE_INFO, "Smelts ores into ingots like a blast furnace, but uses energy and has 4 slots. Has two modes: blasting and alloying.");
        add(ConstantComponents.NASA_WORKBENCH_INFO, "Used to craft rockets.");
        add(ConstantComponents.FUEL_REFINERY_INFO, "Refines fuel from oil.");
        add(ConstantComponents.OXYGEN_LOADER_INFO, "Converts water into oxygen. Use it to fill up space suits and gas tanks.");
        add(ConstantComponents.SOLAR_PANEL_INFO, "Generates energy from the sun during the day. Note that the energy generated is dependent on the planet.");
        add(ConstantComponents.WATER_PUMP_INFO, "Pumps water. Ensure a water source is below the pump for it to function.");
        add(ConstantComponents.OXYGEN_DISTRIBUTOR_INFO, "Distributes oxygen and regulates the temperature. Use it to create livable environments. Should be used in a completely sealed structure.");
        add(ConstantComponents.GRAVITY_NORMALIZER_INFO, "Allows you to control gravity in the local area. Should be used in a completely sealed structure.");
        add(ConstantComponents.ENERGIZER_INFO, "Stores a large amount of energy. Right-click with an item to charge it. Retains its charge when broken.");
        add(ConstantComponents.CRYO_FREEZER_INFO, "Converts ice materials into cryo fuel.");
        add(ConstantComponents.OXYGEN_SENSOR_INFO, "Emits a redstone signal when oxygen is detected. shift-right-click to invert the signal. Right-click to change what it detects.");

        add(ConstantComponents.PIPE_NORMAL, "Set pipe to Normal");
        add(ConstantComponents.PIPE_INSERT, "Set pipe to Insert");
        add(ConstantComponents.PIPE_EXTRACT, "Set pipe to Extract");
        add(ConstantComponents.PIPE_NONE, "Set pipe to None");

        add(ConstantComponents.DOOR_LOCKED, "Locked Door");
        add(ConstantComponents.DOOR_UNLOCKED, "Unlocked Door");

        add("tooltip.ad_astra.energy_transfer_tick", "Transfer: %s ⚡/t");
        add("tooltip.ad_astra.fluid_transfer_tick", "Transfer: %s 🪣/t");

        add(ConstantComponents.SIDE_CONFIG_SLOTS, "Slots");
        add(ConstantComponents.SIDE_CONFIG_ENERGY, "Energy");
        add(ConstantComponents.SIDE_CONFIG_FLUID, "Fluid");
        add(ConstantComponents.SIDE_CONFIG_INPUT_SLOTS, "Input slots");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS, "Output slots");
        add(ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS, "Extraction slots");
        add(ConstantComponents.SIDE_CONFIG_INPUT_FLUID, "Input fluid");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID, "Output fluid");

        add("side_config.ad_astra.title", "Side Config [%s]");
        add("side_config.ad_astra.type.type", "Type: %s");
        add("side_config.ad_astra.type.direction", "Direction: %s (%s)");
        add("side_config.ad_astra.type.action", "Action: %s");

        add("side_config.ad_astra.type.none", "None");
        add("side_config.ad_astra.type.push", "Push");
        add("side_config.ad_astra.type.pull", "Pull");
        add("side_config.ad_astra.type.push_pull", "Push/Pull");

        add("side_config.ad_astra.type.slot", "Slot");
        add("side_config.ad_astra.type.energy", "Energy");
        add("side_config.ad_astra.type.fluid", "Fluid");

        add("direction.ad_astra.up", "Up");
        add("direction.ad_astra.down", "Down");
        add("direction.ad_astra.north", "North");
        add("direction.ad_astra.east", "East");
        add("direction.ad_astra.south", "South");
        add("direction.ad_astra.west", "West");

        add("direction.ad_astra.relative.up", "Top");
        add("direction.ad_astra.relative.down", "Bottom");
        add("direction.ad_astra.relative.north", "Front");
        add("direction.ad_astra.relative.east", "Left");
        add("direction.ad_astra.relative.south", "Back");
        add("direction.ad_astra.relative.west", "Right");

        add(ConstantComponents.NEXT, "Next");
        add(ConstantComponents.PREVIOUS, "Previous");
        add(ConstantComponents.RESET_TO_DEFAULT, "Reset to default");

        add(ConstantComponents.DETECTOR_INVERTED_TRUE, "Set to inverted");
        add(ConstantComponents.DETECTOR_INVERTED_FALSE, "Set to normal");
        add(ConstantComponents.DETECTOR_OXYGEN_MODE, "Set to oxygen detection mode");
        add(ConstantComponents.DETECTOR_GRAVITY_MODE, "Set to normal gravity detection mode");
        add(ConstantComponents.DETECTOR_TEMPERATURE_MODE, "Set to safe temperature detection Mode");

        add(ConstantComponents.FLAG_URL, "Flag Url (https://i.imgur.com/urURL.png)");
        add(ConstantComponents.CONFIRM, "Confirm");
        add(ConstantComponents.NOT_THE_OWNER, "You do not own this flag!");

        add(ConstantComponents.NOT_ENOUGH_FUEL, "Not enough fuel!");
        add(ConstantComponents.INVALID_LAUNCHING_DIMENSION, "You cannot launch from this dimension!");

        add(ConstantComponents.CATALOG, "Catalog");
        add(ConstantComponents.LAND, "Land");
        add(ConstantComponents.SPACE_STATION, "Space Stations");
        add(ConstantComponents.CONSTRUCT, "Construct");
        add("text.ad_astra.text.space_station_name", "Space Station %s");
        add("tooltip.ad_astra.space_station_owner", "Owner: %s");
        add("tooltip.ad_astra.land", "Land on %s at [%s, %s]");
        add("tooltip.ad_astra.space_station_land", "Land on space station in %s at [%s, %s]");
        add("tooltip.ad_astra.construct_space_station_at", "Construct space station in %s at [%s, %s]");

        add(ConstantComponents.CONSTRUCTION_COST, "Construction Cost:");
        add("tooltip.ad_astra.requirement", "%s/%s %s");

        add(ConstantComponents.SPACE_STATION_ALREADY_EXISTS, "A space station already exists at this location!");

        add("galaxy.ad_astra.andromeda", "Andromeda Galaxy");
        add("text.galaxy.ad_astra.andromeda", "Andromeda Galaxy");
        add("galaxy.ad_astra.milky_way", "Milky Way");
        add("text.galaxy.ad_astra.milky_way", "Milky Way");

        add("solar_system.ad_astra.solar_system", "Solar System");
        add("solar_system.ad_astra.proxima_centauri", "Proxima Centauri");
        add("solar_system.ad_astra.pytheas", "Pytheas");
        add("solar_system.ad_astra.trappist_1", "Trappist 1");

        add("planet.minecraft.overworld", "Earth");
        add("planet.ad_astra.moon", "Moon");
        add("planet.ad_astra.mars", "Mars");
        add("planet.ad_astra.venus", "Venus");
        add("planet.ad_astra.mercury", "Mercury");
        add("planet.ad_astra.trappist_1_e", "Trappist 1e");
        add("planet.ad_astra.eos", "Eos");

        add("planet.ad_astra.earth_orbit", "Earth Orbit");
        add("planet.ad_astra.moon_orbit", "Moon Orbit");
        add("planet.ad_astra.mars_orbit", "Mars Orbit");
        add("planet.ad_astra.venus_orbit", "Venus Orbit");
        add("planet.ad_astra.mercury_orbit", "Mercury Orbit");
        add("planet.ad_astra.glacio_orbit", "Glacio Orbit");
        add("planet.ad_astra.trappist_1_e_orbit", "Trappist 1e Orbit");
        add("planet.ad_astra.eos_orbit", "Eos Orbit");

        add("dimension.ad_astra.earth", "Earth");
        add("dimension.ad_astra.deimos", "Deimos");
        add("dimension.ad_astra.eos", "Eos");
        add("dimension.ad_astra.glacio", "Glacio");
        add("dimension.ad_astra.mars", "Mars");
        add("dimension.ad_astra.mercury", "Mercury");
        add("dimension.ad_astra.moon", "Moon");
        add("dimension.ad_astra.trappist_1_e", "Trappist 1e");
        add("dimension.ad_astra.venus", "Venus");

        add("dimension.ad_astra.earth_orbit", "Earth Orbit");
        add("dimension.ad_astra.deimos_orbit", "Deimos Orbit");
        add("dimension.ad_astra.eos_orbit", "Eos Orbit");
        add("dimension.ad_astra.glacio_orbit", "Glacio Orbit");
        add("dimension.ad_astra.mars_orbit", "Mars Orbit");
        add("dimension.ad_astra.mercury_orbit", "Mercury Orbit");
        add("dimension.ad_astra.moon_orbit", "Moon Orbit");
        add("dimension.ad_astra.trappist_1_e_orbit", "Trappist 1e Orbit");
        add("dimension.ad_astra.venus_orbit", "Venus Orbit");

        add("biome.ad_astra.orbit", "Space");
        add("biome.ad_astra.lunar_wastelands", "Lunar Wastelands");
        add("biome.ad_astra.martian_canyon_creek", "Martian Canyon Creek");
        add("biome.ad_astra.martian_polar_caps", "Martian Polar Caps");
        add("biome.ad_astra.martian_wastelands", "Martian Wastelands");
        add("biome.ad_astra.infernal_venus_barrens", "Infernal Venus Barrens");
        add("biome.ad_astra.venus_wastelands", "Venus Wastelands");
        add("biome.ad_astra.mercury_deltas", "Mercury Deltas");
        add("biome.ad_astra.glacio_ice_peaks", "Glacio Ice Peaks");
        add("biome.ad_astra.glacio_snowy_barrens", "Glacio Snowy Barrens");
        add("biome.ad_astra.trappist_1_e_plains", "Trappist 1e Plains");

        add("tag.item.ad_astra.globes", "Globes");
        add("tag.item.ad_astra.flags", "Flags");
        add("tag.item.ad_astra.sliding_doors", "Sliding Doors");
        add("tag.item.ad_astra.space_suits", "Space Suits");
        add("tag.item.ad_astra.netherite_space_suits", "Netherite Space Suits");
        add("tag.item.ad_astra.jet_suits", "Jet Suits");
        add("tag.item.ad_astra.space_resistant_armor", "Space-Resistant Armor");
        add("tag.item.ad_astra.oxygen_supplying_armor", "Oxygen-Supplying Armor");
        add("tag.item.ad_astra.freeze_resistant_armor", "Freeze-Resistant Armor");
        add("tag.item.ad_astra.heat_resistant_armor", "Heat-Resistant Armor");
        add("tag.item.ad_astra.held_over_head", "Held Over Head");
        add("tag.item.ad_astra.cable_ducts", "Cable Ducts");
        add("tag.item.ad_astra.fluid_pipe_ducts", "Fluid Pipe Ducts");
        add("tag.item.ad_astra.iron_plates", "Iron Plates");
        add("tag.item.ad_astra.iron_rods", "Iron Rods");
        add("tag.item.ad_astra.steel_ingots", "Steel Ingots");
        add("tag.item.ad_astra.steel_nuggets", "Steel Nuggets");
        add("tag.item.ad_astra.steel_plates", "Steel Plates");
        add("tag.item.ad_astra.steel_rods", "Steel Rods");
        add("tag.item.ad_astra.steel_blocks", "Steel Blocks");
        add("tag.item.ad_astra.desh_ingots", "Desh Ingots");
        add("tag.item.ad_astra.desh_nuggets", "Desh Nuggets");
        add("tag.item.ad_astra.desh_plates", "Desh Plates");
        add("tag.item.ad_astra.raw_desh", "Raw Desh");
        add("tag.item.ad_astra.desh_blocks", "Desh Blocks");
        add("tag.item.ad_astra.raw_desh_blocks", "Raw Desh Blocks");
        add("tag.item.ad_astra.ostrum_ingots", "Ostrum Ingots");
        add("tag.item.ad_astra.ostrum_nuggets", "Ostrum Nuggets");
        add("tag.item.ad_astra.ostrum_plates", "Ostrum Plates");
        add("tag.item.ad_astra.raw_ostrum", "Raw Ostrum");
        add("tag.item.ad_astra.ostrum_blocks", "Ostrum Blocks");
        add("tag.item.ad_astra.raw_ostrum_blocks", "Raw Ostrum Blocks");
        add("tag.item.ad_astra.calorite_ingots", "Calorite Ingots");
        add("tag.item.ad_astra.calorite_nuggets", "Calorite Nuggets");
        add("tag.item.ad_astra.calorite_plates", "Calorite Plates");
        add("tag.item.ad_astra.raw_calorite", "Raw Calorite");
        add("tag.item.ad_astra.calorite_blocks", "Calorite Blocks");
        add("tag.item.ad_astra.raw_calorite_blocks", "Raw Calorite Blocks");
        add("tag.item.ad_astra.glacian_logs", "Glacian Logs");
        add("tag.item.ad_astra.aeronos_caps", "Aeronos Caps");
        add("tag.item.ad_astra.strophar_caps", "Strophar Caps");
        add("tag.item.ad_astra.destroyed_in_space", "Destroyed in Space");

        add("config.ad_astra.clientConfig", "Client Config");
        add("config.ad_astra.allowFlagImages", "Allow flag images");
        add("config.ad_astra.launchFromAnywhere", "Launch from anywhere");
        add("config.ad_astra.planetRandomTickSpeed", "Planet random tick speed");
        add("config.ad_astra.forcePlanetTick", "Force planet tick");
        add("config.ad_astra.disabledPlanets", "Disabled planets");
        add("config.ad_astra.atmosphereLeave", "Atmosphere leave");
        add("config.ad_astra.disableOxygen", "Disable oxygen");
        add("config.ad_astra.disableTemperature", "Disable temperature");
        add("config.ad_astra.disableGravity", "Disable gravity");
        add("config.ad_astra.disableAirVortexes", "Disable air vortexes");

        add("config.ad_astra.machines", "Machines");
        add("config.ad_astra.ironTierMaxEnergyInOut", "Iron tier max energy in/out");
        add("config.ad_astra.steelTierMaxEnergyInOut", "Steel tier max energy in/out");
        add("config.ad_astra.deshTierMaxEnergyInOut", "Desh tier max energy in/out");
        add("config.ad_astra.ostrumTierMaxEnergyInOut", "Ostrum tier max energy in/out");
        add("config.ad_astra.ironTierEnergyCapacity", "Iron tier energy capacity");
        add("config.ad_astra.steelTierEnergyCapacity", "Steel tier energy capacity");
        add("config.ad_astra.deshTierEnergyCapacity", "Desh tier energy capacity");
        add("config.ad_astra.ostrumTierEnergyCapacity", "Ostrum tier energy capacity");

        add("config.ad_astra.steelTierFluidCapacity", "Steel tier max fluid capacity");
        add("config.ad_astra.deshTierFluidCapacity", "Desh tier max fluid capacity");
        add("config.ad_astra.ostrumTierFluidCapacity", "Ostrum tier max fluid capacity");

        add("config.ad_astra.coalGeneratorEnergyGenerationPerTick", "Coal generator energy generation per tick");
        add("config.ad_astra.etrionicBlastFurnaceBlastingEnergyPerItem", "Etrionic blast furnace blasting energy per item");
        add("config.ad_astra.waterPumpEnergyPerTick", "Water pump energy per tick");
        add("config.ad_astra.waterPumpFluidGenerationPerTick", "Water pump fluid generation per tick");
        add("config.ad_astra.energizerEnergyCapacity", "Energizer energy capacity");
        add("config.ad_astra.maxDistributionBlocks", "Max distribution blocks");
        add("config.ad_astra.distributionRefreshRate", "Distribution refresh rate");
        add("config.ad_astra.pipeRefreshRate", "Pipe refresh rate");

        add("subtitles.ad_astra.entity.rocket_launch", "Rocket launches");
        add("subtitles.ad_astra.entity.rocket", "Rocket flies");
        add("subtitles.ad_astra.block.wrench", "Wrench ratchets");
        add("subtitles.ad_astra.block.sliding_door_open", "Sliding door opens");
        add("subtitles.ad_astra.block.sliding_door_close", "Sliding door closes");
        add("subtitles.ad_astra.entity.oxygen_intake", "Oxygen intake");
        add("subtitles.ad_astra.entity.oxygen_outtake", "Oxygen outtake");
        add("subtitles.ad_astra.entity.gravity_normalizer_idle", "Gravity normalizer idle");

        add("advancements.ad_astra.ad_astra.title", "Ad Astra");
        add("advancements.ad_astra.ad_astra.description", "Welcome to Ad Astra!");
        add("advancements.ad_astra.carbon_footprint.title", "Carbon Footprint");
        add("advancements.ad_astra.carbon_footprint.description", "Construct a Coal Generator, the simplest way to generate energy");
        add("advancements.ad_astra.under_pressure.title", "Under Pressure");
        add("advancements.ad_astra.under_pressure.description", "Construct a Compressor, making it possible to press ingots into plates");
        add("advancements.ad_astra.smart_blasting.title", "Smart Blasting");
        add("advancements.ad_astra.smart_blasting.description", "Craft an Etrionic Blast Furnace, allowing you to smelt with energy and create steel.");
        add("advancements.ad_astra.rocket_science.title", "Rocket Science");
        add("advancements.ad_astra.rocket_science.description", "Construct a NASA Workbench, allowing you to craft rockets");
        add("advancements.ad_astra.ocean_cleanup.title", "Ocean Cleanup");
        add("advancements.ad_astra.ocean_cleanup.description", "Fill a bucket with oil from the ocean");
        add("advancements.ad_astra.astronaut.title", "Astronaut");
        add("advancements.ad_astra.astronaut.description", "Create a space suit");
        add("advancements.ad_astra.dj.title", "DJ");
        add("advancements.ad_astra.dj.description", "Craft a radio");
        add("advancements.ad_astra.zip_gun.title", "Unconventional Flight");
        add("advancements.ad_astra.zip_gun.description", "Craft and use a zip gun, alloying you to propel yourself forward. Use it to move around in space!");
        add("advancements.ad_astra.ti_69.title", "New Phone, Who Dis?");
        add("advancements.ad_astra.ti_69.description", "Craft a TI-69, allowing you to view local oxygen, temperature, and gravity information");
        add("advancements.ad_astra.rover.title", "New Whip");
        add("advancements.ad_astra.rover.description", "Construct a rover");
        add("advancements.ad_astra.tier_1_rocket.title", "Baby's First Rocket");
        add("advancements.ad_astra.tier_1_rocket.description", "Construct a Tier 1 Rocket");
        add("advancements.ad_astra.moon.title", "One Small Step");
        add("advancements.ad_astra.moon.description", "Land on The Moon");
        add("advancements.ad_astra.moon_cheese.title", "Not FDA Approved");
        add("advancements.ad_astra.moon_cheese.description", "Obtain moon cheese. I wonder where it came from?");
        add("advancements.ad_astra.solar_panel.title", "Green Energy");
        add("advancements.ad_astra.solar_panel.description", "Construct a Solar Panel, allowing you to generate energy from the sun");
        add("advancements.ad_astra.oxygen_distributor.title", "Take a Deep Breath");
        add("advancements.ad_astra.oxygen_distributor.description", "Construct an Oxygen Distributor, allowing you to create livable environments");
        add("advancements.ad_astra.gravity_normalizer.title", "Make Newton Proud");
        add("advancements.ad_astra.gravity_normalizer.description", "Construct a Gravity Normalizer, allowing you to control gravity in the local area");
        add("advancements.ad_astra.tier_2_rocket.title", "The Next Step");
        add("advancements.ad_astra.tier_2_rocket.description", "Construct a Tier 2 Rocket");
        add("advancements.ad_astra.mars.title", "The Martian");
        add("advancements.ad_astra.mars.description", "Land on Mars");
        add("advancements.ad_astra.doom_slayer.title", "Doom Slayer");
        add("advancements.ad_astra.doom_slayer.description", "Create a netherite space suit, allowing you to survive in extreme heat");
        add("advancements.ad_astra.energizer.title", "Electric Boogaloo");
        add("advancements.ad_astra.energizer.description", "Construct an Energizer, a block capable of storing a large amount of energy");
        add("advancements.ad_astra.cryo_freezer.title", "Ice Ice Baby");
        add("advancements.ad_astra.cryo_freezer.description", "Construct a Cryo Freezer, allowing you to create cryo fuel");
        add("advancements.ad_astra.cryo_fuel.title", "Cold Stuff");
        add("advancements.ad_astra.cryo_fuel.description", "Obtain Cryo Fuel, a fuel that's 3 times as efficient as rocket fuel");
        add("advancements.ad_astra.tier_3_rocket.title", "The Final Frontier");
        add("advancements.ad_astra.tier_3_rocket.description", "Construct a Tier 3 Rocket");
        add("advancements.ad_astra.venus.title", "Venusian");
        add("advancements.ad_astra.venus.description", "Land on Venus");
        add("advancements.ad_astra.mercury.title", "Quite the Sun Tan");
        add("advancements.ad_astra.mercury.description", "Land on Mercury");
        add("advancements.ad_astra.rocket_man.title", "Rocket Man");
        add("advancements.ad_astra.rocket_man.description", "Craft a jet suit, allowing you to propel yourself forward with extreme force");
        add("advancements.ad_astra.tier_4_rocket.title", "To Infinity and Beyond");
        add("advancements.ad_astra.tier_4_rocket.description", "Construct a Tier 4 Rocket");
        add("advancements.ad_astra.interstellar.title", "Interstellar");
        add("advancements.ad_astra.interstellar.description", "Reach a new solar system");

        add("advancements.ad_astra.jet_suit.description", "§bEquip a jet suit, allowing for unimaginable flight capabilities");
        add("advancements.ad_astra.jet_suit.title", "Now that's an upgrade!");
        add("advancements.ad_astra.mars_dungeon.description", "§bEnter a Mars dungeon");
        add("advancements.ad_astra.mars_dungeon.title", "What lies below the dust?");
        add("advancements.ad_astra.mercury_dungeon.description", "§bEnter a Mercury dungeon");
        add("advancements.ad_astra.mercury_dungeon.title", "Back to the Future");
        add("advancements.ad_astra.moon_dungeon.description", "§bEnter a Moon dungeon");
        add("advancements.ad_astra.moon_dungeon.title", "Secrets Beneath");
        add("advancements.ad_astra.netherite_space_suit.description", "§bEquip a netherite space suit, allowing you to survive the most dangerous conditions.");
        add("advancements.ad_astra.netherite_space_suit.title", "Beat the Heat");
        add("advancements.ad_astra.rocket_destroyed.description", "§bLaunch a rocket with no passenger, causing it to explode in the sky.");
        add("advancements.ad_astra..rocket_destroyed.title", "§bChasing Voyager 1.... or not...");
        add("advancements.ad_astra.space_suit.description", "§bEquip your first space suit");
        add("advancements.ad_astra.space_suit.title", "Astronaut");
        add("advancements.ad_astra.venus_dungeon.description", "§bEnter a Venus dungeon");
        add("advancements.ad_astra.venus_dungeon.title", "Hellish Treasures");



        add("painting.ad_astra.mercury.author", "Facu");
        add("painting.ad_astra.mercury.title", "Mercury");
        add("painting.ad_astra.moon.author", "Facu");
        add("painting.ad_astra.moon.title", "Moon");
        add("painting.ad_astra.pluto.author", "Facu");
        add("painting.ad_astra.pluto.title", "Pluto");
        add("painting.ad_astra.earth.author", "Facu");
        add("painting.ad_astra.earth.title", "Earth");
        add("painting.ad_astra.glacio.author", "Facu");
        add("painting.ad_astra.glacio.title", "Glacio");
        add("painting.ad_astra.mars.author", "Facu");
        add("painting.ad_astra.mars.title", "Mars");
        add("painting.ad_astra.venus.author", "Facu");
        add("painting.ad_astra.venus.title", "Venus");
        add("painting.ad_astra.jupiter.author", "Facu");
        add("painting.ad_astra.jupiter.title", "Jupiter");
        add("painting.ad_astra.neptune.author", "Facu");
        add("painting.ad_astra.neptune.title", "Neptune");
        add("painting.ad_astra.uranus.author", "Facu");
        add("painting.ad_astra.uranus.title", "Uranus");
        add("painting.ad_astra.saturn.author", "Facu");
        add("painting.ad_astra.saturn.title", "Saturn");
        add("painting.ad_astra.the_milky_way.author", "Facu");
        add("painting.ad_astra.the_milky_way.title", "The Milky Way");
        add("painting.ad_astra.andromeda.title", "Andromeda Galaxy");
        add("painting.ad_astra.alpha_centauri.author", "Facu");
        add("painting.ad_astra.alpha_centauri.title", "Alpha Centauri");
        add("painting.ad_astra.sun.author", "Facu");
        add("painting.ad_astra.sun.title", "Sun");
    }

    public void add(Component component, String name) {
        add(component.getString(), name);
    }

    public void addFluid(Supplier<? extends Fluid> key, String name) {
        if (key.get() instanceof BotariumFlowingFluid) return;
        add("fluid_type.%s.%s".formatted(AdAstra.MOD_ID, Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key.get())).getPath()), name);
    }
}
