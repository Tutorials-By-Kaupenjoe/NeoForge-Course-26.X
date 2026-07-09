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

public record AtomicSeparatorRecipe(Ingredient inputItem, List<ItemStackTemplate> outputs) implements Recipe<AtomicSeparatorRecipeInput> {
    public static MapCodec<AtomicSeparatorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            Ingredient.CODEC.fieldOf("ingredients").forGetter(AtomicSeparatorRecipe::inputItem),
            ItemStackTemplate.CODEC.listOf().fieldOf("results").forGetter(AtomicSeparatorRecipe::outputs)
    ).apply(inst, AtomicSeparatorRecipe::new));

    public static StreamCodec<RegistryFriendlyByteBuf, AtomicSeparatorRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    Ingredient.CONTENTS_STREAM_CODEC,
                    AtomicSeparatorRecipe::inputItem,

                    ItemStackTemplate.STREAM_CODEC.apply(ByteBufCodecs.list()),
                    AtomicSeparatorRecipe::outputs,

                    AtomicSeparatorRecipe::new);

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputItem);
        return list;
    }

    public List<ItemStack> getOutputs() {
        return outputs.stream().map(ItemStackTemplate::create).toList();
    }

    @Override
    public boolean matches(AtomicSeparatorRecipeInput pInput, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        return inputItem.test(pInput.getItem(0));
    }

    @Override
    public ItemStack assemble(AtomicSeparatorRecipeInput pInput) {
        throw new IllegalStateException();
    }

    @Override
    public boolean showNotification() {
        return true;
    }

    @Override
    public String group() {
        return "Atomic Separation";
    }


    @Override
    public RecipeSerializer<AtomicSeparatorRecipe> getSerializer() {
        return ModRecipes.ATOMIC_SEPARATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<AtomicSeparatorRecipeInput>> getType() {
        return ModRecipes.ATOMIC_SEPARATOR_TYPE.get();
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
