package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.planets.Planet;
import earth.terrarium.adastra.common.recipes.base.IngredientHolder;
import earth.terrarium.adastra.common.registry.ModFluids;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModItemTags;
import earth.terrarium.adastra.datagen.builder.*;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public abstract class ModMachineRecipeProvider extends RecipeProvider {

    public ModMachineRecipeProvider(PackOutput output) {
        super(output);
    }

    public static void createRecipes(@NotNull Consumer<FinishedRecipe> writer) {
        createCompressing(writer, 100, 20, Items.IRON_INGOT, ModItems.IRON_PLATE.get().getDefaultInstance());
        createCompressing(writer, 800, 20, Items.IRON_BLOCK, new ItemStack(ModItems.IRON_PLATE.get(), 9));
        createCompressing(writer, 100, 20, ModItemTags.STEEL_INGOTS, ModItems.STEEL_PLATE.get().getDefaultInstance());
        createCompressing(writer, 800, 20, ModItemTags.STEEL_BLOCKS, new ItemStack(ModItems.STEEL_PLATE.get(), 9));
        createCompressing(writer, 100, 20, ModItemTags.DESH_INGOTS, ModItems.DESH_PLATE.get().getDefaultInstance());
        createCompressing(writer, 800, 20, ModItemTags.DESH_BLOCKS, new ItemStack(ModItems.DESH_PLATE.get(), 9));
        createCompressing(writer, 100, 20, ModItemTags.OSTRUM_INGOTS, ModItems.OSTRUM_PLATE.get().getDefaultInstance());
        createCompressing(writer, 800, 20, ModItemTags.OSTRUM_BLOCKS, new ItemStack(ModItems.OSTRUM_PLATE.get(), 9));
        createCompressing(writer, 100, 20, ModItemTags.CALORITE_INGOTS, ModItems.CALORITE_PLATE.get().getDefaultInstance());
        createCompressing(writer, 800, 20, ModItemTags.CALORITE_BLOCKS, new ItemStack(ModItems.CALORITE_PLATE.get(), 9));

        createAlloying(writer, 100, 20,
            List.of(Ingredient.of(Items.IRON_INGOT), new TagIngredient(ItemTags.COALS)),
            ModItems.STEEL_INGOT.get().getDefaultInstance()
        );

        createOxygenLoading(writer, 1, 30,
            FluidHooks.newFluidHolder(Fluids.WATER, 100, null),
            FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), 4, null)
        );

        createOxygenLoading(writer, 1, 30,
            FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), 25, null),
            FluidHooks.newFluidHolder(ModFluids.OXYGEN.get(), 25, null)
        );

        createRefining(writer, 1, 30,
            FluidHooks.newFluidHolder(ModFluids.OIL.get(), 5, null),
            FluidHooks.newFluidHolder(ModFluids.FUEL.get(), 5, null)
        );

        createCryoFreezing(writer, 60, 40,
            ModItems.ICE_SHARD.get().getDefaultInstance(),
            FluidHooks.newFluidHolder(ModFluids.CRYO_FUEL.get(), 25, null)
        );

        createCryoFreezing(writer, 120, 40,
            Items.ICE.getDefaultInstance(),
            FluidHooks.newFluidHolder(ModFluids.CRYO_FUEL.get(), 1, null)
        );

        createCryoFreezing(writer, 120, 40,
            Items.PACKED_ICE.getDefaultInstance(),
            FluidHooks.newFluidHolder(ModFluids.CRYO_FUEL.get(), 2, null)
        );

        createCryoFreezing(writer, 120, 40,
            Items.BLUE_ICE.getDefaultInstance(),
            FluidHooks.newFluidHolder(ModFluids.CRYO_FUEL.get(), 10, null)
        );

        createNasaWorkbench(writer, List.of(
                Ingredient.of(ModItems.ROCKET_NOSE_CONE.get()),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItemTags.STEEL_BLOCKS),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.STEEL_TANK.get()),
                Ingredient.of(ModItems.STEEL_TANK.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.STEEL_ENGINE.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get())
            ),
            ModItems.TIER_1_ROCKET.get().getDefaultInstance());

        createNasaWorkbench(writer, List.of(
                Ingredient.of(ModItems.ROCKET_NOSE_CONE.get()),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItemTags.DESH_BLOCKS),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.DESH_TANK.get()),
                Ingredient.of(ModItems.DESH_TANK.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.DESH_ENGINE.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get())
            ),
            ModItems.TIER_2_ROCKET.get().getDefaultInstance());

        createNasaWorkbench(writer, List.of(
                Ingredient.of(ModItems.ROCKET_NOSE_CONE.get()),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItemTags.OSTRUM_BLOCKS),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.OSTRUM_TANK.get()),
                Ingredient.of(ModItems.OSTRUM_TANK.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.OSTRUM_ENGINE.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get())
            ),
            ModItems.TIER_3_ROCKET.get().getDefaultInstance());

        createNasaWorkbench(writer, List.of(
                Ingredient.of(ModItems.ROCKET_NOSE_CONE.get()),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItemTags.CALORITE_BLOCKS),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.CALORITE_TANK.get()),
                Ingredient.of(ModItems.CALORITE_TANK.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get()),
                Ingredient.of(ModItems.CALORITE_ENGINE.get()),
                Ingredient.of(ModItems.ROCKET_FIN.get())
            ),
            ModItems.TIER_4_ROCKET.get().getDefaultInstance());

        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.IRON_PLATES), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.DESH_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.DESH_PLATES), 32)
            ),
            Planet.EARTH_ORBIT.location());

        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.IRON_PLATES), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.DESH_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.DESH_PLATES), 32)
            ),
            Planet.MOON_ORBIT.location());

        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_PLATES), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.OSTRUM_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.OSTRUM_PLATES), 32)
            ),
            Planet.MARS_ORBIT.location());

        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_INGOTS), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_PLATES), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.CALORITE_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.CALORITE_PLATES), 32)
            ),
            Planet.VENUS_ORBIT.location());

        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_INGOTS), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_PLATES), 64),
                IngredientHolder.of(Ingredient.of(ModItemTags.CALORITE_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.CALORITE_PLATES), 32)
            ),
            Planet.MERCURY_ORBIT.location());


        createSpaceStation(writer, List.of(
                IngredientHolder.of(Ingredient.of(ModItemTags.STEEL_PLATES), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.DESH_INGOTS), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.OSTRUM_PLATES), 32),
                IngredientHolder.of(Ingredient.of(ModItemTags.CALORITE_PLATES), 32)
            ),
            Planet.GLACIO_ORBIT.location());
    }

    public static void createCompressing(Consumer<FinishedRecipe> writer, int cookingtime, int energy, TagKey<Item> ingredient, ItemStack result) {
        ResourceLocation ingredientId = ingredient.location();
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new CompressingRecipeBuilder(Ingredient.of(ingredient), result)
            .unlockedBy("has_item", has(ingredient))
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "compressing/%s_from_compressing_%s".formatted(resultId.getPath(), ingredientId.getPath())));
    }

    public static void createCompressing(Consumer<FinishedRecipe> writer, int cookingtime, int energy, Item ingredient, ItemStack result) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ingredient));
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new CompressingRecipeBuilder(Ingredient.of(ingredient), result)
            .unlockedBy("has_item", has(ingredient))
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "compressing/%s_from_compressing_%s".formatted(resultId.getPath(), ingredientId.getPath())));
    }

    public static void createAlloying(Consumer<FinishedRecipe> writer, int cookingtime, int energy, List<Ingredient> ingredients, ItemStack result) {
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new AlloyingRecipeBuilder(ingredients, result)
            .unlockedBy("has_item", has(ModItems.ETRIONIC_BLAST_FURNACE.get()))
            .cookingTime(cookingtime)
            .energy(energy);
        List<String> ingredientNames = ingredients.stream()
                .map(ingredient -> {
                    if (ingredient instanceof TagIngredient recipeIngredient) {
                        return recipeIngredient.name;
                    }
                    return Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ingredient.getItems()[0].getItem())).getPath();
                })
                .toList();

        builder.save(writer, new ResourceLocation(
            AdAstra.MOD_ID,
            "alloying/%s_from_alloying_%s".formatted(resultId.getPath(), String.join("_and_", ingredientNames))
        ));
    }

    public static void createOxygenLoading(Consumer<FinishedRecipe> writer, int cookingtime, int energy, FluidHolder ingredient, FluidHolder resultFluid) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(ingredient.getFluid()));
        ResourceLocation resultFluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid.getFluid()));

        var builder = new OxygenLoadingRecipeBuilder(ingredient, resultFluid)
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "oxygen_loading/%s_from_oxygen_loading_%s".formatted(resultFluidId.getPath(), ingredientId.getPath())));
    }

    public static void createRefining(Consumer<FinishedRecipe> writer, int cookingtime, int energy, FluidHolder ingredient, FluidHolder resultFluid) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(ingredient.getFluid()));
        ResourceLocation resultFluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid.getFluid()));

        var builder = new RefiningRecipeBuilder(ingredient, resultFluid)
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "refining/%s_from_refining_%s".formatted(resultFluidId.getPath(), ingredientId.getPath())));
    }

    public static void createCryoFreezing(Consumer<FinishedRecipe> writer, int cookingtime, int energy, ItemStack ingredient, FluidHolder resultFluid) {
        ResourceLocation ingredientId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(ingredient.getItem()));
        ResourceLocation resultFluidId = Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(resultFluid.getFluid()));

        var builder = new CryoFreezingRecipeBuilder(Ingredient.of(ingredient), resultFluid)
            .cookingTime(cookingtime)
            .energy(energy);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "cryo_freezing/%s_from_cryo_freezing_%s".formatted(resultFluidId.getPath(), ingredientId.getPath())));
    }

    public static void createNasaWorkbench(Consumer<FinishedRecipe> writer, List<Ingredient> ingredients, ItemStack result) {
        ResourceLocation resultId = Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result.getItem()));

        var builder = new NasaWorkbenchRecipeBuilder(ingredients, result)
            .unlockedBy("has_item", has(ModItems.NASA_WORKBENCH.get()));
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench/%s_from_nasa_workbench".formatted(resultId.getPath())));
    }

    public static void createSpaceStation(Consumer<FinishedRecipe> writer, List<IngredientHolder> ingredients, ResourceLocation dimension) {
        var builder = new SpaceStationRecipeBuilder(ingredients, dimension);
        builder.save(writer, new ResourceLocation(AdAstra.MOD_ID, "space_station/%s_space_station".formatted(dimension.getPath())));
    }

    private static class TagIngredient extends Ingredient {

        private final String name;

        protected TagIngredient(TagKey<Item> tag) {
            super(Stream.of(new TagValue(tag)));
            this.name = tag.location().getPath();
        }
    }
}
