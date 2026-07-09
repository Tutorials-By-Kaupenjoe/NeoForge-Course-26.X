package net.kaupenjoe.mccourse.menu.custom;

import net.kaupenjoe.mccourse.MCCourse;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;

public class AtomicSeparatorScreen extends AbstractContainerScreen<AtomicSeparatorMenu> {
    private static final Identifier GUI_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/atomic_separator/atomic_separator_gui.png");
    private static final Identifier ARROW_TEXTURE =
            Identifier.fromNamespaceAndPath(MCCourse.MOD_ID,"textures/gui/atomic_separator/atomic_separator_progress_arrow.png");

    public AtomicSeparatorScreen(AtomicSeparatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();

        this.inventoryLabelY = 10000;
        this.titleLabelY = 10000;
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.extractBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight, 256, 256);

        renderProgressArrow(pGuiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphicsExtractor guiGraphics, int x, int y) {
        if(menu.isCrafting()) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED, ARROW_TEXTURE,
                    x + 45, y + 29,
                    0, 0,
                    84, menu.getScaledArrowProgress(),
                    84, 19
            );
        }
    }
}
