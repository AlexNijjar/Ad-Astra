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
    private static final Set<RegistryEntry<Block>> CUSTOM_BLOCK_NAMES = Set.of(ModBlocks.TINKERERS_WORKBENCH, ModBlocks.BLOCK_OF_ETRIUM, ModBlocks.BLOCK_OF_STEEL);

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

        addItem(ModItems.TI_69, "TI-69");
        addBlock(ModBlocks.BLOCK_OF_ETRIUM, "Block of Etrium");
        addBlock(ModBlocks.BLOCK_OF_STEEL, "Block of Steel");
        addBlock(ModBlocks.TINKERERS_WORKBENCH, "Tinkerer's Workbench");

        add(ConstantComponents.ITEM_GROUP.getString(), "Ad Astra");
        add(ConstantComponents.DEATH_OXYGEN.getString(), "%1$s couldn't breathe anymore");
        add(ConstantComponents.DEATH_OXYGEN_PLAYER.getString(), "%1$s lost their breath whilst trying to escape %2$s");

        add(ConstantComponents.TOGGLE_SUIT_FLIGHT_KEY.getString(), "Toggle suit flight");
        add(ConstantComponents.AD_ASTRA_CATEGORY.getString(), "Ad Astra");

        add(ConstantComponents.SUIT_FLIGHT_ENABLED.getString(), "Suit flight enabled");
        add(ConstantComponents.SUIT_FLIGHT_DISABLED.getString(), "Suit flight disabled");

        add(ConstantComponents.OXYGEN_TRUE.getString(), "✔");
        add(ConstantComponents.OXYGEN_FALSE.getString(), "✘");
        add("text.adastra.temperature", "%s°C");
        add("text.adastra.gravity", "%sm/s²");
        add("text.adastra.radio.none", "No station");

        add("text.adastra.weather.temperature", "Now: %s°C");
        add("text.adastra.weather.temperature.in", "In: %s");

        add("tooltip.adastra.energy", "%s ⚡ / %s ⚡");
        add("tooltip.adastra.energy_in", "In: %s ⚡/t");
        add("tooltip.adastra.energy_out", "Out: %s ⚡/t");
        add("tooltip.adastra.max_energy_in", "Max In: %s ⚡/t");
        add("tooltip.adastra.max_energy_out", "Max Out: %s ⚡/t");
        add("tooltip.adastra.energy_use_per_tick", "Uses %s ⚡ per tick");
        add("tooltip.adastra.energy_generation_per_tick", "Generates %s ⚡ per tick");

        add("tooltip.adastra.fluid", "%s 🪣 / %s 🪣 %s");
        add("tooltip.adastra.fluid_in", "In: %s 🪣/t");
        add("tooltip.adastra.fluid_out", "Out: %s 🪣/t");
        add("tooltip.adastra.max_fluid_in", "Max In: %s 🪣/t");
        add("tooltip.adastra.max_fluid_out", "Max Out: %s 🪣/t");
        add("tooltip.adastra.fluid_use_per_iteration", "Uses %s 🪣 per iteration");
        add("tooltip.adastra.fluid_generation_per_iteration", "Generates %s 🪣 per iteration");

        add("tooltip.adastra.ticks_per_iteration", "Takes %s ticks per iteration");

        add("tooltip.ad_astra.progress", "%s / %s");
        add("tooltip.ad_astra.eta", "ETA: %s seconds");

        add(ConstantComponents.CLEAR_FLUID_TANK.getString(), "Shift-right-click to clear");

        add(ConstantComponents.SIDE_CONFIG.getString(), "Side Config");
        add(ConstantComponents.REDSTONE_CONTROL.getString(), "Redstone Control");

        add("tooltip.adastra.redstone_control.always_on", "Always on");
        add("tooltip.adastra.redstone_control.on_when_powered", "On when powered");
        add("tooltip.adastra.redstone_control.on_when_not_powered", "On when not powered");
        add("tooltip.adastra.redstone_control.never_on", "Never on");
        add("tooltip.adastra.redstone_control.mode", "Mode: %s");

        add(ConstantComponents.ACTIVE.getString(), "Active");
        add(ConstantComponents.INACTIVE.getString(), "Inactive");

        add(ConstantComponents.SEQUENTIAL.getString(), "Sequential");
        add(ConstantComponents.ROUND_ROBIN.getString(), "Round Robin");

        add(ConstantComponents.CAPACITOR_ENABLED.getString(), "Capacitor enabled");
        add(ConstantComponents.CAPACITOR_DISABLED.getString(), "Capacitor disabled");
        add(ConstantComponents.CHANGE_MODE_SEQUENTIAL.getString(), "Set capacitor mode to \"Sequential\"");
        add(ConstantComponents.CHANGE_MODE_ROUND_ROBIN.getString(), "Set capacitor mode to \"Round Robin\"");

        add(ConstantComponents.SHIFT_DESCRIPTION.getString(), "Hold SHIFT for more information");
        add(ConstantComponents.TI_69_INFO.getString(), "Displays important information\nRight-click to change the current app");
        add(ConstantComponents.ETRIONIC_CAPACITOR_INFO.getString(), "Right-click to toggle\nShift-right-click to change the distribution mode");
        add(ConstantComponents.ZIP_GUN_INFO.getString(), "Propels you forward\nUse a second one in your offhand to propel even further");
        add(ConstantComponents.GAS_TANK_INFO.getString(), "Stores fluids and gases\nRight-click to distribute into your inventory");

        add(ConstantComponents.UPGRADES.getString(), "Upgrades");
        add("tooltip.adastra.upgrades.entry", "- %sx %s");
        add("upgrade.adastra.speed", "Speed Upgrade");
        add("upgrade.adastra.capacity", "Capacity Upgrade");

        add(ConstantComponents.SIDE_CONFIG_SLOTS.getString(), "Slots");
        add(ConstantComponents.SIDE_CONFIG_ENERGY.getString(), "Energy");
        add(ConstantComponents.SIDE_CONFIG_FLUID.getString(), "Fluid");
        add(ConstantComponents.SIDE_CONFIG_INPUT_SLOTS.getString(), "Input slots");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_SLOTS.getString(), "Output slots");
        add(ConstantComponents.SIDE_CONFIG_EXTRACTION_SLOTS.getString(), "Extraction slots");
        add(ConstantComponents.SIDE_CONFIG_INPUT_FLUID.getString(), "Input fluid");
        add(ConstantComponents.SIDE_CONFIG_OUTPUT_FLUID.getString(), "Output fluid");

        add("side_config.adastra.title", "Side Config [%s]");
        add("side_config.adastra.type.type", "Type: %s");
        add("side_config.adastra.type.direction", "Direction: %s (%s)");
        add("side_config.adastra.type.action", "Action: %s");

        add("side_config.adastra.type.none", "None");
        add("side_config.adastra.type.push", "Push");
        add("side_config.adastra.type.pull", "Pull");
        add("side_config.adastra.type.push_pull", "Push/Pull");

        add("side_config.adastra.type.slot", "Slot");
        add("side_config.adastra.type.energy", "Energy");
        add("side_config.adastra.type.fluid", "Fluid");

        add("direction.adastra.up", "Up");
        add("direction.adastra.down", "Down");
        add("direction.adastra.north", "North");
        add("direction.adastra.east", "East");
        add("direction.adastra.south", "South");
        add("direction.adastra.west", "West");

        add("direction.adastra.relative.up", "Top");
        add("direction.adastra.relative.down", "Bottom");
        add("direction.adastra.relative.north", "Front");
        add("direction.adastra.relative.east", "Left");
        add("direction.adastra.relative.south", "Back");
        add("direction.adastra.relative.west", "Right");

        add(ConstantComponents.NEXT.getString(), "Next");
        add(ConstantComponents.PREVIOUS.getString(), "Previous");
        add(ConstantComponents.RESET_TO_DEFAULT.getString(), "Reset to default");

        add(ConstantComponents.DETECTOR_INVERTED_TRUE.getString(), "Set to inverted");
        add(ConstantComponents.DETECTOR_INVERTED_FALSE.getString(), "Set to normal");
        add(ConstantComponents.DETECTOR_OXYGEN_MODE.getString(), "Set to oxygen detection mode");
        add(ConstantComponents.DETECTOR_GRAVITY_MODE.getString(), "Set to normal gravity detection mode");
        add(ConstantComponents.DETECTOR_TEMPERATURE_MODE.getString(), "Set to safe temperature detection Mode");

        add("tag.item.ad_astra.recyclable", "Recyclable");
        add("tag.item.ad_astra.iron_plates", "Iron Plates");
        add("tag.item.ad_astra.iron_rods", "Iron Rods");
        add("tag.item.ad_astra.steel_ingots", "Steel Ingots");
        add("tag.item.ad_astra.steel_nuggets", "Steel Nuggets");
        add("tag.item.ad_astra.steel_plates", "Steel Plates");
        add("tag.item.ad_astra.steel_rods", "Steel Rods");
        add("tag.item.ad_astra.steel_blocks", "Steel Blocks");
    }

    public void addFluid(Supplier<? extends Fluid> key, String name) {
        if (key.get() instanceof BotariumFlowingFluid) return;
        add("fluid_type.%s.%s".formatted(AdAstra.MOD_ID, Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(key.get())).getPath()), name);
    }
}
