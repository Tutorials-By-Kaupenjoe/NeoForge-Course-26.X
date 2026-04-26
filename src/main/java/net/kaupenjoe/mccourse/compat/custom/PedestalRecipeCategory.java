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
import net.kaupenjoe.mccourse.recipe.custom.PedestalRecipe;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;

public class PedestalRecipeCategory implements IRecipeCategory<RecipeHolder<PedestalRecipe>> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,
            "textures/gui/growth_chamber/growth_chamber_gui.png");
    private final IDrawable icon;
    private final IDrawable main_pedestal;
    private final IDrawable side_pedestal;
    private final IDrawable bg;
    private final IDrawable progress_arrow;

    public PedestalRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MAIN_PEDESTAL));
        this.main_pedestal = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.MAIN_PEDESTAL));
        this.side_pedestal = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SIDE_PEDESTAL));
        bg = helper.createDrawable(TEXTURE,0 ,0, 24, 16);
        progress_arrow = helper.getRecipeArrowFilled();
    }

    @Override
    public IRecipeType<RecipeHolder<PedestalRecipe>> getRecipeType() {
        return ModJEIRecipeTypes.PEDESTAL;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mccourse.main_pedestal");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<PedestalRecipe> recipe, IFocusGroup focuses) {
        var ingredients = recipe.value().getIngredients();

        int radius = 45;
        int centerX = 50; // Keep this the same as your pedestal centerX
        int centerY = 62; // Keep this the same as your pedestal centerY

        // Adjust this to visually float the items above the pedestal sprites (negative goes UP)
        int yOffset = -10;
        int xOffset = 1;

        // 1. Draw the center item
        builder.addSlot(RecipeIngredientRole.INPUT, centerX + xOffset, centerY + yOffset)
                .add(ingredients.get(0));

        // 2. Draw the remaining items in a circle
        int outerItemsCount = ingredients.size() - 1; // Based on your code, this will be 9

        for (int i = 0; i < outerItemsCount; i++) {
            // Divide a full circle (2 * PI) by the number of outer items
            double angle = i * (2 * Math.PI / outerItemsCount);

            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));

            // Notice we use (i + 1) to fetch get(1) through get(9)
            builder.addSlot(RecipeIngredientRole.INPUT, x + xOffset, y + yOffset)
                    .add(ingredients.get(i + 1));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, centerX + xOffset + 100, centerY + yOffset).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<PedestalRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        int radius = 45;
        int centerX = 50; // Adjust this to set the center X position on your screen
        int centerY = 65; // Adjust this to set the center Y position on your screen

        main_pedestal.draw(guiGraphics, centerX, centerY);

        for (int i = 0; i < 8; i++) {
            // A full circle is 2 * PI radians. Divide by 8 to get the angle step.
            double angle = i * (Math.PI / 4.0);
            // Calculate the X and Y offsets using cosine and sine
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));

            side_pedestal.draw(guiGraphics, x, y);
        }

        progress_arrow.draw(guiGraphics, centerX + 70, centerY);
        main_pedestal.draw(guiGraphics, centerX + 100, centerY);
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 150;
    }
}
