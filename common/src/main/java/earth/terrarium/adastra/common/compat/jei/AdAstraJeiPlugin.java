package earth.terrarium.adastra.common.compat.jei;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.machines.*;
import earth.terrarium.adastra.common.compat.jei.categories.*;
import earth.terrarium.adastra.common.registry.ModCreativeTab;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.registry.ModRecipeTypes;
import me.shedaniel.rei.plugincompatibilities.api.REIPluginCompatIgnore;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@JeiPlugin
@REIPluginCompatIgnore
public class AdAstraJeiPlugin implements IModPlugin {
    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return new ResourceLocation(AdAstra.MOD_ID, "jei");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();

        registration.addRecipeCategories(new CompressingCategory(guiHelper));
        registration.addRecipeCategories(new AlloyingCategory(guiHelper));
        registration.addRecipeCategories(new OxygenLoadingCategory(guiHelper));
        registration.addRecipeCategories(new RefiningCategory(guiHelper));
        registration.addRecipeCategories(new CryoFreezingCategory(guiHelper));
        registration.addRecipeCategories(new NasaWorkbenchCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientLevel level = Objects.requireNonNull(Minecraft.getInstance().level);

        registration.addRecipes(CompressingCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.COMPRESSING.get()));
        registration.addRecipes(AlloyingCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.ALLOYING.get()));
        registration.addRecipes(OxygenLoadingCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.OXYGEN_LOADING.get()));
        registration.addRecipes(RefiningCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.REFINING.get()));
        registration.addRecipes(CryoFreezingCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.CRYO_FREEZING.get()));
        registration.addRecipes(NasaWorkbenchCategory.RECIPE, level.getRecipeManager().getAllRecipesFor(ModRecipeTypes.NASA_WORKBENCH.get()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.COMPRESSOR.get().getDefaultInstance(), CompressingCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.ETRIONIC_BLAST_FURNACE.get().getDefaultInstance(), AlloyingCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.OXYGEN_LOADER.get().getDefaultInstance(), OxygenLoadingCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.FUEL_REFINERY.get().getDefaultInstance(), RefiningCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.CRYO_FREEZER.get().getDefaultInstance(), CryoFreezingCategory.RECIPE);
        registration.addRecipeCatalyst(ModItems.NASA_WORKBENCH.get().getDefaultInstance(), NasaWorkbenchCategory.RECIPE);
    }

    @Override
    public void registerGuiHandlers(@NotNull IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CompressorScreen.class, CompressorScreen.CLICK_AREA.getX(), CompressorScreen.CLICK_AREA.getY(), CompressorScreen.CLICK_AREA.getWidth(), CompressorScreen.CLICK_AREA.getHeight(), CompressingCategory.RECIPE);
        registration.addRecipeClickArea(EtrionicBlastFurnaceScreen.class, EtrionicBlastFurnaceScreen.CLICK_AREA.getX(), EtrionicBlastFurnaceScreen.CLICK_AREA.getY(), EtrionicBlastFurnaceScreen.CLICK_AREA.getWidth(), EtrionicBlastFurnaceScreen.CLICK_AREA.getHeight(), AlloyingCategory.RECIPE);
        registration.addRecipeClickArea(OxygenLoaderScreen.class, OxygenLoaderScreen.CLICK_AREA.getX(), OxygenLoaderScreen.CLICK_AREA.getY(), OxygenLoaderScreen.CLICK_AREA.getWidth(), OxygenLoaderScreen.CLICK_AREA.getHeight(), OxygenLoadingCategory.RECIPE);
        registration.addRecipeClickArea(OxygenDistributorScreen.class, OxygenDistributorScreen.CLICK_AREA.getX(), OxygenDistributorScreen.CLICK_AREA.getY(), OxygenDistributorScreen.CLICK_AREA.getWidth(), OxygenDistributorScreen.CLICK_AREA.getHeight(), OxygenLoadingCategory.RECIPE);
        registration.addRecipeClickArea(FuelRefineryScreen.class, FuelRefineryScreen.CLICK_AREA.getX(), FuelRefineryScreen.CLICK_AREA.getY(), FuelRefineryScreen.CLICK_AREA.getWidth(), FuelRefineryScreen.CLICK_AREA.getHeight(), RefiningCategory.RECIPE);
        registration.addRecipeClickArea(CryoFreezerScreen.class, CryoFreezerScreen.CLICK_AREA.getX(), CryoFreezerScreen.CLICK_AREA.getY(), CryoFreezerScreen.CLICK_AREA.getWidth(), CryoFreezerScreen.CLICK_AREA.getHeight(), CryoFreezingCategory.RECIPE);
        registration.addRecipeClickArea(NasaWorkbenchScreen.class, NasaWorkbenchScreen.CLICK_AREA.getX(), NasaWorkbenchScreen.CLICK_AREA.getY(), NasaWorkbenchScreen.CLICK_AREA.getWidth(), NasaWorkbenchScreen.CLICK_AREA.getHeight(), NasaWorkbenchCategory.RECIPE);
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        ModCreativeTab.getCustomNbtItems().forEach(stack -> registration.useNbtForSubtypes(stack.getItem()));
    }
}
