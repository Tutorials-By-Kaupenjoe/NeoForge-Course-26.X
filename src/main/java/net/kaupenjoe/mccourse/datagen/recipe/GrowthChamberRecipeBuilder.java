package net.kaupenjoe.mccourse.datagen.recipe;

import net.kaupenjoe.mccourse.recipe.custom.GrowthChamberRecipe;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class GrowthChamberRecipeBuilder implements RecipeBuilder {
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final List<Ingredient> ingredients;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    private GrowthChamberRecipeBuilder(RecipeCategory category, List<Ingredient> ingredients, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    public static GrowthChamberRecipeBuilder growthChamber(RecipeCategory category, List<Ingredient> inputItems, ItemLike item, int count) {
        return new GrowthChamberRecipeBuilder(category, inputItems, new ItemStackTemplate(item.asItem(), count));
    }

    public static GrowthChamberRecipeBuilder growthChamber(RecipeCategory category, List<Ingredient> inputItems, ItemLike item) {
        return new GrowthChamberRecipeBuilder(category, inputItems, new ItemStackTemplate(item.asItem()));
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
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> id) {
        GrowthChamberRecipe recipe = new GrowthChamberRecipe(this.ingredients, this.result);
        output.accept(id, recipe, this.advancementBuilder.build(output, id, this.category));
    }
}
