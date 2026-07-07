package net.kaupenjoe.mccourse.menu.custom;

import net.kaupenjoe.mccourse.MCCourse;
import net.kaupenjoe.mccourse.menu.renderer.EnergyDisplayTooltipArea;
import net.kaupenjoe.mccourse.util.MouseUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class BatteryScreen extends AbstractContainerScreen<BatteryMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/battery/battery_gui.png");
    private EnergyDisplayTooltipArea energyInfoArea;

    public BatteryScreen(BatteryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
        if(isMouseAboveArea(pMouseX, pMouseY, x, y, 61, 8, 53, 56)) {
            guiGraphics.setComponentTooltipForNextFrame(this.font, energyInfoArea.getTooltips(),
                    pMouseX, pMouseY);
        }
    }

    private void assignEnergyInfoArea() {
        energyInfoArea = new EnergyDisplayTooltipArea(((width - imageWidth) / 2) + 63,
                ((height - imageHeight) / 2) + 10, menu.blockEntity.getEnergyStorage(null), 50, 54);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        renderEnergyAreaTooltip(guiGraphics, pMouseX, pMouseY, x, y);
        guiGraphics.centeredText(this.font, energyInfoArea.getTooltips().get(0), 90, 70, -1);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.extractBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        energyInfoArea.render(pGuiGraphics);
    }

    public static boolean isMouseAboveArea(int pMouseX, int pMouseY, int x, int y, int offsetX, int offsetY, int width, int height) {
        return MouseUtil.isMouseOver(pMouseX, pMouseY, x + offsetX, y + offsetY, width, height);
    }
}
