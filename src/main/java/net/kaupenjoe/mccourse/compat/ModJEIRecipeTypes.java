package net.kaupenjoe.mccourse.compat;

import mezz.jei.api.recipe.types.IRecipeType;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.recipe.custom.CrystallizerRecipe;
import net.kaupenjoe.mccourse.recipe.custom.GrowthChamberRecipe;
import net.kaupenjoe.mccourse.recipe.custom.PedestalRecipe;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ModJEIRecipeTypes {
    public static final IRecipeType<RecipeHolder<CrystallizerRecipe>> CRYSTALLIZER =
            create(MCCourse.MOD_ID, "crystallizing", CrystallizerRecipe.class);
    public static final IRecipeType<RecipeHolder<PedestalRecipe>> PEDESTAL =
            create(MCCourse.MOD_ID, "pedestal_crafting", PedestalRecipe.class);
    public static final IRecipeType<RecipeHolder<GrowthChamberRecipe>> GROWTH_CHAMBER =
            create(MCCourse.MOD_ID, "growing", GrowthChamberRecipe.class);


    // From Occultism: https://github.com/klikli-dev/occultism/blob/version/26.1.2/src/main/java/com/klikli_dev/occultism/integration/jei/impl/JeiRecipeTypes.java
    // Under MIT-License
    public static <R extends Recipe<?>> IRecipeType<RecipeHolder<R>> create(String modid, String name, Class<? extends R> recipeClass) {
        Identifier uid = Identifier.fromNamespaceAndPath(modid, name);
        @SuppressWarnings({"unchecked", "RedundantCast"})
        Class<? extends RecipeHolder<R>> holderClass = (Class<? extends RecipeHolder<R>>) (Object) RecipeHolder.class;
        return IRecipeType.create(uid, holderClass);
    }
}
