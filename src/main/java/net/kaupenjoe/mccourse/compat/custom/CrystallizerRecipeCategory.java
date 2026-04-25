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
import net.kaupenjoe.mccourse.menu.custom.CrystallizerScreen;
import net.kaupenjoe.mccourse.menu.renderer.FluidTankRenderer;
import net.kaupenjoe.mccourse.recipe.custom.CrystallizerRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CrystallizerRecipeCategory implements IRecipeCategory<RecipeHolder<CrystallizerRecipe>> {
    public static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,
            "textures/gui/crystallizer/crystallizer_gui.png");
    private final IDrawable icon;
    private final IDrawable overlay;
    private final FluidTankRenderer fluidRenderer;

    public CrystallizerRecipeCategory(IGuiHelper helper) {
        this.overlay = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CRYSTALLIZER));

        fluidRenderer = new FluidTankRenderer(16000, FluidTankRenderer.TooltipMode.SHOW_AMOUNT, 16, 50);
    }

    @Override
    public IRecipeType<RecipeHolder<CrystallizerRecipe>> getRecipeType() {
        return ModJEIRecipeTypes.CRYSTALLIZER;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.mccourse.crystallizer");
    }

    @Override
    public int getWidth() {
        return 176;
    }

    @Override
    public int getHeight() {
        return 85;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CrystallizerRecipe> recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().inputItem());
        // builder.addSlot(RecipeIngredientRole.INPUT, 54, 34).add(recipe.value().getIngredients().get(0)); Would also get the input item

        builder.addSlot(RecipeIngredientRole.OUTPUT, 104, 34).add(recipe.value().output());
    }

    @Override
    public void draw(RecipeHolder<CrystallizerRecipe> recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        this.overlay.draw(guiGraphics, 0, 0);

        guiGraphics.fillGradient(156, 50, 164, 56,0xffb51500, 0xff600b00);
        if(CrystallizerScreen.isMouseAboveArea((int)mouseX, (int)mouseY, 0, 0, 156, 11, 8, 48)) {
            guiGraphics.setComponentTooltipForNextFrame(Minecraft.getInstance().font, List.of(Component.literal(25 + "FE/T Needed. Total of 1800 FE.")),
                    (int)mouseX, (int)mouseY + 110);
        }

        fluidRenderer.render(guiGraphics, 8, 7, new FluidStack(Fluids.WATER, 1000));
        if(CrystallizerScreen.isMouseAboveArea((int)mouseX, (int)mouseY, 0, 0, 9, 7, fluidRenderer)) {
            guiGraphics.setComponentTooltipForNextFrame(Minecraft.getInstance().font, fluidRenderer.getTooltip(new FluidStack(Fluids.WATER, 1000)),
                    (int)mouseX + 78, (int)mouseY + 110);
        }
    }
}
