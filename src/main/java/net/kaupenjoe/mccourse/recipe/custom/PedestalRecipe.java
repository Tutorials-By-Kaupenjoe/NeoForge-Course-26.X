package net.kaupenjoe.mccourse.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record PedestalRecipe(List<Ingredient> ingredients,
                             ItemStackTemplate output) implements Recipe<PedestalRecipeInput> {
    public static final MapCodec<PedestalRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(PedestalRecipe::getIngredients),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(PedestalRecipe::output)
    ).apply(inst, PedestalRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PedestalRecipe> STREAM_CODEC =
            StreamCodec.composite(Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()), PedestalRecipe::ingredients,
                    ItemStackTemplate.STREAM_CODEC, PedestalRecipe::output,
                    PedestalRecipe::new);


    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean matches(PedestalRecipeInput input, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        if (!ingredients.get(0).test(input.mainPedestalItem())) {
            return false;
        }

        for (int i = 0; i < input.sidePedestalItems().size(); i++) {
            if (!ingredients.get(i + 1).test(input.sidePedestalItems().get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(PedestalRecipeInput input) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "Pedestal";
    }

    @Override
    public RecipeSerializer<? extends Recipe<PedestalRecipeInput>> getSerializer() {
        return ModRecipes.PEDESTAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<PedestalRecipeInput>> getType() {
        return ModRecipes.PEDESTAL_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}
