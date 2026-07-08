package net.kaupenjoe.mccourse.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.compat.custom.CrystallizerRecipeCategory;
import net.kaupenjoe.mccourse.compat.custom.GrowthChamberRecipeCategory;
import net.kaupenjoe.mccourse.compat.custom.PedestalRecipeCategory;
import net.kaupenjoe.mccourse.menu.custom.CrystallizerScreen;
import net.kaupenjoe.mccourse.menu.custom.GrowthChamberScreen;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RecipesReceivedEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;

import java.util.List;

@JeiPlugin
public class ModJEIPlugin implements IModPlugin {
    private static RecipeMap syncedRecipes = RecipeMap.EMPTY;

    @Override
    public Identifier getPluginUid() {
        return Identifier.fromNamespaceAndPath(MCCourse.MOD_ID, "jei_plugin");
    }

    // From Occultism
    // Under MIT License
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <I extends RecipeInput, T extends Recipe<I>> List<RecipeHolder<T>> getRecipes(RecipeMap recipeMap, RecipeType<T> type) {
        return (List) recipeMap.byType(type);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrystallizerRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
        registration.addRecipeCategories(new PedestalRecipeCategory(registration.getJeiHelpers().getGuiHelper()));

        registration.addRecipeCategories(new GrowthChamberRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(ModJEIRecipeTypes.CRYSTALLIZER, this.getRecipes(syncedRecipes, ModRecipes.CRYSTALLIZER_TYPE.get()));
        registration.addRecipes(ModJEIRecipeTypes.PEDESTAL, this.getRecipes(syncedRecipes, ModRecipes.PEDESTAL_TYPE.get()));

        registration.addRecipes(ModJEIRecipeTypes.GROWTH_CHAMBER, this.getRecipes(syncedRecipes, ModRecipes.GROWTH_CHAMBER_TYPE.get()));
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrystallizerScreen.class, 74, 30, 22, 20,
                ModJEIRecipeTypes.CRYSTALLIZER);

        registration.addRecipeClickArea(GrowthChamberScreen.class, 74, 30, 22, 20,
                ModJEIRecipeTypes.GROWTH_CHAMBER);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addCraftingStation(ModJEIRecipeTypes.CRYSTALLIZER, new ItemStack(ModBlocks.CRYSTALLIZER.asItem()));
        registration.addCraftingStation(ModJEIRecipeTypes.PEDESTAL, new ItemStack(ModBlocks.MAIN_PEDESTAL.asItem()));
        registration.addCraftingStation(ModJEIRecipeTypes.PEDESTAL, new ItemStack(ModBlocks.SIDE_PEDESTAL.asItem()));

        registration.addCraftingStation(ModJEIRecipeTypes.GROWTH_CHAMBER, new ItemStack(ModBlocks.GROWTH_CHAMBER.asItem()));
    }


    @EventBusSubscriber(modid = MCCourse.MOD_ID)
    public static class ServerRecipeSync {
        @SubscribeEvent
        public static void onDatapackSync(OnDatapackSyncEvent event) {
            event.sendRecipes(
                    ModRecipes.CRYSTALLIZER_TYPE.get(),
                    ModRecipes.PEDESTAL_TYPE.get(),
                    ModRecipes.GROWTH_CHAMBER_TYPE.get()
            );
        }
    }

    @EventBusSubscriber(modid = MCCourse.MOD_ID, value = Dist.CLIENT)
    public static class ClientRecipeSync {
        @SubscribeEvent
        public static void onRecipeReceived(RecipesReceivedEvent event) {
            syncedRecipes = event.getRecipeMap();
        }
    }
}
