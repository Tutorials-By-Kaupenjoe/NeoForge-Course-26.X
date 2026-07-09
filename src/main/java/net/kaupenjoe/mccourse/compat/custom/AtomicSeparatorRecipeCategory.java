package net.kaupenjoe.mccourse.compat.custom;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.types.IRecipeType;
import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.block.ModBlocks;
import net.kaupenjoe.mccourse.compat.ModJEIRecipeTypes;
import net.kaupenjoe.mccourse.recipe.custom.AtomicSeparatorRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class AtomicSeparatorRecipeCategory implements IRecipeCategory<RecipeHolder<AtomicSeparatorRecipe>> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,
            "textures/gui/atomic_separator/atomic_separator_gui.png");
    private final IDrawable icon;
    private final IDrawable overlay;

    public AtomicSeparatorRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE,0 ,0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.ATOMIC_SEPARATOR));
    }

    @Override
    public IRecipeType<RecipeHolder<AtomicSeparatorRecipe>> getRecipeType() {
        return ModJEIRecipeTypes.ATOMIC_SEPARATOR;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mccourse.atomic_separator");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<AtomicSeparatorRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 80, 11).add(recipe.value().getIngredients().get(0));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 46, 55).add(recipe.value().getOutputs().get(0));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 55).add(recipe.value().getOutputs().get(1));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 114,55).add(recipe.value().getOutputs().get(2));
    }

    @Override
    public void draw(RecipeHolder<AtomicSeparatorRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 85;
    }
}
