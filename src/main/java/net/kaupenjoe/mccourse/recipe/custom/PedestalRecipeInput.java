package net.kaupenjoe.mccourse.recipe.custom;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public record PedestalRecipeInput(ItemStack mainPedestalItem, List<ItemStack> sidePedestalItems) implements RecipeInput {
    @Override
    public ItemStack getItem(int index) {
        if(index == 0) {
            return mainPedestalItem;
        }
        return sidePedestalItems.get(index - 1);
    }

    @Override
    public int size() {
        return 9;
    }
}
