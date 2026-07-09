package net.kaupenjoe.mccourse.datagen.recipe;

import net.kaupenjoe.mccourse.recipe.custom.AtomicSeparatorRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class AtomicSeparatorRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final List<ItemStackTemplate> results;
    private final Ingredient ingredient;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private AtomicSeparatorRecipeBuilder(RecipeCategory category, Ingredient ingredient, List<ItemStackTemplate> results) {
        this.category = category;
        this.results = results;
        this.ingredient = ingredient;
    }

    public static AtomicSeparatorRecipeBuilder atomicSeparator(RecipeCategory category, Ingredient inputItem, List<ItemStackTemplate> items) {
        return new AtomicSeparatorRecipeBuilder(category, inputItem, items);
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.results.getFirst());
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        AtomicSeparatorRecipe recipe = new AtomicSeparatorRecipe(this.ingredient, this.results);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
