package net.kaupenjoe.mccourse.menu.renderer;

import com.google.common.base.Preconditions;
import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.CommonColors;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.fluid.FluidTintSource;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

// CREDIT: https://github.com/mezz/JustEnoughItems by mezz
// Under MIT-License: https://github.com/mezz/JustEnoughItems/blob/1.19/LICENSE.txt
// Includes major rewrites and methods from:
// https://github.com/mezz/JustEnoughItems/blob/1.19/Forge/src/main/java/mezz/jei/forge/platform/FluidHelper.java
// Major rewrites from Kaupenjoe
public class FluidTankRenderer {
    private static final NumberFormat nf = NumberFormat.getIntegerInstance();
    private static final int MIN_FLUID_HEIGHT = 1;

    private final long capacity;
    private final TooltipMode tooltipMode;
    private final int width;
    private final int height;

    public enum TooltipMode {
        SHOW_AMOUNT,
        SHOW_AMOUNT_AND_CAPACITY,
        ITEM_LIST
    }

    public FluidTankRenderer(long capacity, boolean showCapacity, int width, int height) {
        this(capacity, showCapacity ? TooltipMode.SHOW_AMOUNT_AND_CAPACITY : TooltipMode.SHOW_AMOUNT, width, height);
    }

    public FluidTankRenderer(long capacity, TooltipMode tooltipMode, int width, int height) {
        Preconditions.checkArgument(capacity > 0, "capacity must be > 0");
        Preconditions.checkArgument(width > 0, "width must be > 0");
        Preconditions.checkArgument(height > 0, "height must be > 0");

        this.capacity = capacity;
        this.tooltipMode = tooltipMode;
        this.width = width;
        this.height = height;
    }

    public void render(GuiGraphicsExtractor guiGraphics, int x, int y, FluidStack fluidStack) {
        int scaledAmount = getScaledAmount(fluidStack);
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, getFluidTexture(fluidStack),
                x, y + height - scaledAmount, width, scaledAmount, getColorTint(fluidStack));
    }

    private int getScaledAmount(FluidStack fluidStack) {
        int amount = fluidStack.getAmount();
        int scaledAmount = (amount * height) / (int)capacity;

        if (amount > 0 && scaledAmount < MIN_FLUID_HEIGHT) {
            scaledAmount = MIN_FLUID_HEIGHT;
        }
        if (scaledAmount > height) {
            scaledAmount = height;
        }
        return scaledAmount;
    }

    private TextureAtlasSprite getFluidTexture(FluidStack fluidStack) {
        return Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluidStack.getFluid().defaultFluidState()).stillMaterial().sprite();
    }

    private int getColorTint(FluidStack fluidStack) {
        FluidTintSource tintSource = Minecraft.getInstance().getModelManager().getFluidStateModelSet().get(fluidStack.getFluid().defaultFluidState()).fluidTintSource();
        if(tintSource == null) {
            return CommonColors.WHITE;
        }

        return tintSource.colorAsStack(fluidStack);
    }

    public List<Component> getTooltip(FluidStack fluidStack) {
        List<Component> tooltip = new ArrayList<>();

        if(fluidStack == null) {
            fluidStack = new FluidStack(Fluids.EMPTY, 0);
        }

        Fluid fluidType = fluidStack.getFluid();
        try {
            if (fluidType.isSame(Fluids.EMPTY)) {
                tooltip.add(Component.literal("Empty"));
                tooltip.add(Component.translatable("mccourse.tooltip.liquid.amount.with.capacity", 0, nf.format(capacity)).withStyle(ChatFormatting.GRAY));
                return tooltip;
            }

            Component displayName = fluidStack.getHoverName();
            tooltip.add(displayName);

            long amount = fluidStack.getAmount();
            long milliBuckets = (amount * 1000) / FluidType.BUCKET_VOLUME;

            if (tooltipMode == TooltipMode.SHOW_AMOUNT_AND_CAPACITY) {
                MutableComponent amountString = Component.translatable("mccourse.tooltip.liquid.amount.with.capacity", nf.format(milliBuckets), nf.format(capacity));
                tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
            } else if (tooltipMode == TooltipMode.SHOW_AMOUNT) {
                MutableComponent amountString = Component.translatable("mccourse.tooltip.liquid.amount", nf.format(milliBuckets));
                tooltip.add(amountString.withStyle(ChatFormatting.GRAY));
            }
        } catch (RuntimeException e) {
            MCCourse.LOGGER.error("Failed to get tooltip for fluid: " + e);
        }

        return tooltip;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}