package net.kaupenjoe.mccourse.recipe.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kaupenjoe.mccourse.recipe.ModRecipes;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;

public record GrowthChamberRecipe(List<Ingredient> inputItems, ItemStackTemplate output) implements Recipe<GrowthChamberRecipeInput> {
    public static MapCodec<GrowthChamberRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(GrowthChamberRecipe::inputItems),
            ItemStackTemplate.CODEC.fieldOf("result").forGetter(GrowthChamberRecipe::output)
    ).apply(inst, GrowthChamberRecipe::new));

    public static StreamCodec<RegistryFriendlyByteBuf, GrowthChamberRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
                    GrowthChamberRecipe::inputItems,

                    ItemStackTemplate.STREAM_CODEC,
                    GrowthChamberRecipe::output,

                    GrowthChamberRecipe::new);

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.addAll(inputItems);
        return list;
    }

    @Override
    public boolean matches(GrowthChamberRecipeInput pInput, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        for (int i = 0; i < inputItems.size(); i++) {
            if(!inputItems.get(i).test(pInput.getItem(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(GrowthChamberRecipeInput pInput) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Growing";
    }


    @Override
    public RecipeSerializer<GrowthChamberRecipe> getSerializer() {
        return ModRecipes.GROWTH_CHAMBER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<GrowthChamberRecipeInput>> getType() {
        return ModRecipes.GROWTH_CHAMBER_TYPE.get();
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
