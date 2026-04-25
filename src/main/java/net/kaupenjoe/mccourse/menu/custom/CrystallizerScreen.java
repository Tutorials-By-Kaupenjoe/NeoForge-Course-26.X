package net.kaupenjoe.mccourse.menu.custom;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.menu.renderer.EnergyDisplayTooltipArea;
import net.kaupenjoe.mccourse.menu.renderer.FluidTankRenderer;
import net.kaupenjoe.mccourse.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.fluids.FluidStack;

public class CrystallizerScreen extends AbstractContainerScreen<CrystallizerMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/crystallizer/crystallizer_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/crystallizer/arrow_progress.png");
    private static final Identifier CRYSTAL_TEXTURE =
            Identifier.parse("textures/block/amethyst_cluster.png");
    private EnergyDisplayTooltipArea energyInfoArea;
    private FluidTankRenderer fluidRenderer;

    public CrystallizerScreen(CrystallizerMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelX = 65;
        this.titleLabelX = 65;

        assignEnergyInfoArea();
        assignFluidRenderer();
    }

    private void assignFluidRenderer() {
        fluidRenderer = new FluidTankRenderer(16000, true, 16, 50);
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 156,
                ((height - imageHeight) / 2 ) + 9, menu.blockEntity.getEnergyStorage(null), 8, 48);
    }

    private void renderEnergyAreaTooltip(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 48)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font, energyInfoArea.getTooltips(), pMouseX, pMouseY);
        }
    }

    private void renderFluidTooltipArea(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, int x, int y,
                                        FluidStack stack, int offsetX, int offsetY, FluidTankRenderer renderer) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, offsetX, offsetY, renderer)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font, renderer.getTooltip(stack), pMouseX, pMouseY);
        }
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor graphics, int xm, int ym) {
        super.extractLabels(graphics, xm, ym);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltip(graphics, xm, ym, x, y);
        renderFluidTooltipArea(graphics, xm, ym, x, y, menu.blockEntity.getFluid(), 8, 7, fluidRenderer);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractBackground(graphics, mouseX, mouseY, a);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        graphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        energyInfoArea.render(graphics);
        fluidRenderer.render(graphics, x + 8, y + 7, menu.blockEntity.getFluid());

        renderProgressArrow(graphics, x, y);
        renderProgressCrystal(graphics, x, y);
    }

    private void renderProgressArrow(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE,x + 73, y + 35, 0, 0,
                    menu.getScaledArrowProgress(), 16, 24, 16);
        }
    }

    private void renderProgressCrystal(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, CRYSTAL_TEXTURE, x + 104, y + 13 + 16 - menu.getScaledCrystalProgress(), 0,
                    16 - menu.getScaledCrystalProgress(), 16, menu.getScaledCrystalProgress(),16, 16);
        }
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, FluidTankRenderer renderer) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, renderer.getWidth(), renderer.getHeight());
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
