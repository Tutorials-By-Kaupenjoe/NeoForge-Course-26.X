package net.kaupenjoe.mccourse.menu.custom;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.menu.renderer.EnergyDisplayTooltipArea;
import net.kaupenjoe.mccourse.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;

public class CoalGeneratorScreen extends AbstractContainerScreen<CoalGeneratorMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/coal_generator/coal_generator_gui.png");
    private static final Identifier LIT_PROGRESS_TEXTURE =
            Identifier.fromNamespaceAndPath("minecraft","container/furnace/lit_progress");
    private EnergyDisplayTooltipArea energyInfoArea;

    public CoalGeneratorScreen(CoalGeneratorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        // Gets rid of title and inventory title
        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;

        assignEnergyInfoArea();
    }

    private void renderEnergyAreaTooltip(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, int x, int y) {
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 156, 11, 8, 64)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font, energyInfoArea.getTooltips(),
                    pMouseX, pMouseY);
        }
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 156,
                ((height - imageHeight) / 2) + 11, menu.blockEntity.getEnergyStorage(null));
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltip(guiGraphics, pMouseX, pMouseY, x, y);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.extractBackground(guiGraphics, pMouseX, pMouseY, pPartialTick);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        energyInfoArea.render(guiGraphics);
        renderFuelBurning(guiGraphics, x, y);
    }

    private void renderFuelBurning(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(this.menu.isBurning()) {
            int litProgressHeight = Mth.ceil(this.menu.getFuelProgress() * 13.0F) + 1;
            guiGraphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    LIT_PROGRESS_TEXTURE,
                    14,
                    14,
                    0,
                    14 - litProgressHeight,
                    x + 80,
                    y + 18 + 14 - litProgressHeight,
                    14,
                    litProgressHeight);
        }
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
