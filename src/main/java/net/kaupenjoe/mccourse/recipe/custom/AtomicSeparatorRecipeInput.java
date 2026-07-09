package net.kaupenjoe.mccourse.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AtomicSeparatorRecipeInput(ItemStack input) implements RecipeInput {
    @Override
    public ItemStack getItem(int pIndex) {
        return input;
    }

    @Override
    public int size() {
        return 1;
    }
}
