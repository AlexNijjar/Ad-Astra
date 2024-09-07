package earth.terrarium.adastra.datagen.provider.server;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.common.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ModAdvancementProvider extends ForgeAdvancementProvider {

    public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new Advancements()));
    }

    public static class Advancements implements AdvancementGenerator {

        @Override
        public void generate(HolderLookup.Provider writer, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {
            Advancement adAstra = Advancement.Builder.advancement()
                .display(
                    ModItems.EARTH_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.ad_astra.title"),
                    Component.translatable("advancements.ad_astra.ad_astra.description"),
                    new ResourceLocation(AdAstra.MOD_ID, "textures/block/steel_panel.png"),
                    FrameType.TASK,
                    false,
                    false,
                    false
                )
                .addCriterion("has_coal_generator", hasItem(ModItems.COAL_GENERATOR.get()))
                .save(consumer, path("ad_astra"));

            Advancement carbonFootprint = Advancement.Builder.advancement()
                .parent(adAstra)
                .display(
                    ModItems.COAL_GENERATOR.get(),
                    Component.translatable("advancements.ad_astra.carbon_footprint.title"),
                    Component.translatable("advancements.ad_astra.carbon_footprint.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_coal_generator", hasItem(ModItems.COAL_GENERATOR.get()))
                .save(consumer, path("carbon_footprint"));

            Advancement underPressure = Advancement.Builder.advancement()
                .parent(carbonFootprint)
                .display(
                    ModItems.COMPRESSOR.get(),
                    Component.translatable("advancements.ad_astra.under_pressure.title"),
                    Component.translatable("advancements.ad_astra.under_pressure.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_compressor", hasItem(ModItems.COMPRESSOR.get()))
                .save(consumer, path("under_pressure"));

            Advancement smartBlasting = Advancement.Builder.advancement()
                .parent(underPressure)
                .display(
                    ModItems.ETRIONIC_BLAST_FURNACE.get(),
                    Component.translatable("advancements.ad_astra.smart_blasting.title"),
                    Component.translatable("advancements.ad_astra.smart_blasting.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_etrionic_blast_furnace", hasItem(ModItems.ETRIONIC_BLAST_FURNACE.get()))
                .save(consumer, path("smart_blasting"));

            Advancement rocketScience = Advancement.Builder.advancement()
                .parent(smartBlasting)
                .display(
                    ModItems.NASA_WORKBENCH.get(),
                    Component.translatable("advancements.ad_astra.rocket_science.title"),
                    Component.translatable("advancements.ad_astra.rocket_science.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_nasa_workbench", hasItem(ModItems.NASA_WORKBENCH.get()))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, path("rocket_science"));

            Advancement oceanCleanup = Advancement.Builder.advancement()
                .parent(rocketScience)
                .display(
                    ModItems.OIL_BUCKET.get(),
                    Component.translatable("advancements.ad_astra.ocean_cleanup.title"),
                    Component.translatable("advancements.ad_astra.ocean_cleanup.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion(
                    ModItems.OIL_BUCKET.getId().getPath(),
                    FilledBucketTrigger.TriggerInstance.filledBucket(ItemPredicate.Builder.item().of(ModItems.OIL_BUCKET.get()).build()))
                .save(consumer, path("ocean_cleanup"));

            Advancement astronaut = Advancement.Builder.advancement()
                .parent(oceanCleanup)
                .display(
                    ModItems.SPACE_HELMET.get(),
                    Component.translatable("advancements.ad_astra.astronaut.title"),
                    Component.translatable("advancements.ad_astra.astronaut.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_astronaut", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ModItems.SPACE_HELMET.get(),
                    ModItems.SPACE_SUIT.get(),
                    ModItems.SPACE_PANTS.get(),
                    ModItems.SPACE_BOOTS.get()
                ))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, path("astronaut"));

            Advancement dj = Advancement.Builder.advancement()
                .parent(astronaut)
                .display(
                    ModItems.RADIO.get(),
                    Component.translatable("advancements.ad_astra.dj.title"),
                    Component.translatable("advancements.ad_astra.dj.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_radio", hasItem(ModItems.RADIO.get()))
                .save(consumer, path("dj"));

            Advancement zipGun = Advancement.Builder.advancement()
                .parent(dj)
                .display(
                    ModItems.ZIP_GUN.get(),
                    Component.translatable("advancements.ad_astra.zip_gun.title"),
                    Component.translatable("advancements.ad_astra.zip_gun.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("using_zip_gun", new UsingItemTrigger.TriggerInstance(ContextAwarePredicate.ANY, ItemPredicate.Builder.item().of(ModItems.ZIP_GUN.get()).build()))
                .save(consumer, path("zip_gun"));

            Advancement ti69 = Advancement.Builder.advancement()
                .parent(zipGun)
                .display(
                    ModItems.TI_69.get(),
                    Component.translatable("advancements.ad_astra.ti_69.title"),
                    Component.translatable("advancements.ad_astra.ti_69.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_ti_69", hasItem(ModItems.TI_69.get()))
                .save(consumer, path("ti_69"));

            Advancement rover = Advancement.Builder.advancement()
                .parent(dj)
                .display(
                    ModItems.ROVER.get(),
                    Component.translatable("advancements.ad_astra.rover.title"),
                    Component.translatable("advancements.ad_astra.rover.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_rover", hasItem(ModItems.ROVER.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("rover"));

            Advancement tier1Rocket = Advancement.Builder.advancement()
                .parent(astronaut)
                .display(
                    ModItems.TIER_1_ROCKET.get(),
                    Component.translatable("advancements.ad_astra.tier_1_rocket.title"),
                    Component.translatable("advancements.ad_astra.tier_1_rocket.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_tier_1_rocket", hasItem(ModItems.TIER_1_ROCKET.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("tier_1_rocket"));

            Advancement moon = Advancement.Builder.advancement()
                .parent(tier1Rocket)
                .display(
                    ModItems.MOON_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.moon.title"),
                    Component.translatable("advancements.ad_astra.moon.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("entered_moon", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon"))))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("moon"));

            Advancement moonCheese = Advancement.Builder.advancement()
                .parent(moon)
                .display(
                    ModItems.CHEESE.get(),
                    Component.translatable("advancements.ad_astra.moon_cheese.title"),
                    Component.translatable("advancements.ad_astra.moon_cheese.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_moon_cheese", hasItem(ModItems.CHEESE.get()))
                .save(consumer, path("moon_cheese"));

            Advancement solarPanel = Advancement.Builder.advancement()
                .parent(moon)
                .display(
                    ModItems.SOLAR_PANEL.get(),
                    Component.translatable("advancements.ad_astra.solar_panel.title"),
                    Component.translatable("advancements.ad_astra.solar_panel.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_solar_panel", hasItem(ModItems.SOLAR_PANEL.get()))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, path("solar_panel"));

            Advancement oxygenDistributor = Advancement.Builder.advancement()
                .parent(solarPanel)
                .display(
                    ModItems.OXYGEN_DISTRIBUTOR.get(),
                    Component.translatable("advancements.ad_astra.oxygen_distributor.title"),
                    Component.translatable("advancements.ad_astra.oxygen_distributor.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_oxygen_distributor", hasItem(ModItems.OXYGEN_DISTRIBUTOR.get()))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, path("oxygen_distributor"));

            Advancement gravityNormalizer = Advancement.Builder.advancement()
                .parent(oxygenDistributor)
                .display(
                    ModItems.GRAVITY_NORMALIZER.get(),
                    Component.translatable("advancements.ad_astra.gravity_normalizer.title"),
                    Component.translatable("advancements.ad_astra.gravity_normalizer.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_gravity_normalizer", hasItem(ModItems.GRAVITY_NORMALIZER.get()))
                .rewards(AdvancementRewards.Builder.experience(50))
                .save(consumer, path("gravity_normalizer"));

            Advancement tier2Rocket = Advancement.Builder.advancement()
                .parent(moon)
                .display(
                    ModItems.TIER_2_ROCKET.get(),
                    Component.translatable("advancements.ad_astra.tier_2_rocket.title"),
                    Component.translatable("advancements.ad_astra.tier_2_rocket.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_tier_2_rocket", hasItem(ModItems.TIER_2_ROCKET.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("tier_2_rocket"));

            Advancement mars = Advancement.Builder.advancement()
                .parent(tier2Rocket)
                .display(
                    ModItems.MARS_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.mars.title"),
                    Component.translatable("advancements.ad_astra.mars.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("entered_mars", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars"))))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("mars"));

            Advancement doomSlayer = Advancement.Builder.advancement()
                .parent(mars)
                .display(
                    ModItems.NETHERITE_SPACE_HELMET.get(),
                    Component.translatable("advancements.ad_astra.doom_slayer.title"),
                    Component.translatable("advancements.ad_astra.doom_slayer.description"),
                    null,
                    FrameType.GOAL,
                    true,
                    true,
                    false
                )
                .addCriterion("has_netherite_space_suit", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ModItems.NETHERITE_SPACE_HELMET.get(),
                    ModItems.NETHERITE_SPACE_SUIT.get(),
                    ModItems.NETHERITE_SPACE_PANTS.get(),
                    ModItems.NETHERITE_SPACE_BOOTS.get()
                ))
                .rewards(AdvancementRewards.Builder.experience(200))
                .save(consumer, path("doom_slayer"));

            Advancement energizer = Advancement.Builder.advancement()
                .parent(mars)
                .display(
                    ModItems.ENERGIZER.get(),
                    Component.translatable("advancements.ad_astra.energizer.title"),
                    Component.translatable("advancements.ad_astra.energizer.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_energizer", hasItem(ModItems.ENERGIZER.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("energizer"));

            Advancement cryoFreezer = Advancement.Builder.advancement()
                .parent(mars)
                .display(
                    ModItems.CRYO_FREEZER.get(),
                    Component.translatable("advancements.ad_astra.cryo_freezer.title"),
                    Component.translatable("advancements.ad_astra.cryo_freezer.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_cryo_freezer", hasItem(ModItems.CRYO_FREEZER.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("cryo_freezer"));

            Advancement cryoFuel = Advancement.Builder.advancement()
                .parent(cryoFreezer)
                .display(
                    ModItems.CRYO_FUEL_BUCKET.get(),
                    Component.translatable("advancements.ad_astra.cryo_fuel.title"),
                    Component.translatable("advancements.ad_astra.cryo_fuel.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_cryo_fuel", hasItem(ModItems.CRYO_FUEL_BUCKET.get()))
                .rewards(AdvancementRewards.Builder.experience(100))
                .save(consumer, path("cryo_fuel"));

            Advancement tier3Rocket = Advancement.Builder.advancement()
                .parent(doomSlayer)
                .display(
                    ModItems.TIER_3_ROCKET.get(),
                    Component.translatable("advancements.ad_astra.tier_3_rocket.title"),
                    Component.translatable("advancements.ad_astra.tier_3_rocket.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_tier_3_rocket", hasItem(ModItems.TIER_3_ROCKET.get()))
                .rewards(AdvancementRewards.Builder.experience(200))
                .save(consumer, path("tier_3_rocket"));

            Advancement venus = Advancement.Builder.advancement()
                .parent(tier3Rocket)
                .display(
                    ModItems.VENUS_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.venus.title"),
                    Component.translatable("advancements.ad_astra.venus.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("entered_venus", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus"))))
                .rewards(AdvancementRewards.Builder.experience(200))
                .save(consumer, path("venus"));

            Advancement mercury = Advancement.Builder.advancement()
                .parent(tier3Rocket)
                .display(
                    ModItems.MERCURY_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.mercury.title"),
                    Component.translatable("advancements.ad_astra.mercury.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("entered_mercury", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury"))))
                .rewards(AdvancementRewards.Builder.experience(200))
                .save(consumer, path("mercury"));

            Advancement tier4Rocket = Advancement.Builder.advancement()
                .parent(venus)
                .display(
                    ModItems.TIER_4_ROCKET.get(),
                    Component.translatable("advancements.ad_astra.tier_4_rocket.title"),
                    Component.translatable("advancements.ad_astra.tier_4_rocket.description"),
                    null,
                    FrameType.TASK,
                    true,
                    true,
                    false
                )
                .addCriterion("has_tier_4_rocket", hasItem(ModItems.TIER_4_ROCKET.get()))
                .rewards(AdvancementRewards.Builder.experience(300))
                .save(consumer, path("tier_4_rocket"));

            Advancement rocketMan = Advancement.Builder.advancement()
                .parent(venus)
                .display(
                    ModItems.JET_SUIT_HELMET.get(),
                    Component.translatable("advancements.ad_astra.rocket_man.title"),
                    Component.translatable("advancements.ad_astra.rocket_man.description"),
                    null,
                    FrameType.GOAL,
                    true,
                    true,
                    false
                )
                .addCriterion("has_jet_suit", InventoryChangeTrigger.TriggerInstance.hasItems(
                    ModItems.JET_SUIT_HELMET.get(),
                    ModItems.JET_SUIT.get(),
                    ModItems.JET_SUIT_PANTS.get(),
                    ModItems.JET_SUIT_BOOTS.get()
                ))
                .rewards(AdvancementRewards.Builder.experience(300))
                .save(consumer, path("rocket_man"));

            Advancement interstellar = Advancement.Builder.advancement()
                .parent(tier4Rocket)
                .display(
                    ModItems.GLACIO_GLOBE.get(),
                    Component.translatable("advancements.ad_astra.interstellar.title"),
                    Component.translatable("advancements.ad_astra.interstellar.description"),
                    null,
                    FrameType.GOAL,
                    true,
                    true,
                    false
                )
                .addCriterion("entered_glacio", ChangeDimensionTrigger.TriggerInstance.changedDimensionTo(ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio"))))
                .rewards(AdvancementRewards.Builder.experience(300))
                .save(consumer, path("interstellar"));
        }


        private String path(String name) {
            return "%s:%s".formatted(AdAstra.MOD_ID, name);
        }

        private InventoryChangeTrigger.TriggerInstance hasItem(Item item) {
            return InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(item).build());
        }
    }
}
