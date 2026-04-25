package net.kaupenjoe.mccourse.menu.renderer;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;

import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  https://github.com/BluSunrize/ImmersiveEngineering/blob/1.19.2/LICENSE
 *
 *  Modified Version by: Kaupenjoe
 */
public class EnergyDisplayTooltipArea {
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final EnergyHandler energy;

    public EnergyDisplayTooltipArea(int xMin, int yMin, EnergyHandler energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public EnergyDisplayTooltipArea(int xMin, int yMin, EnergyHandler energy, int width, int height)  {
        xPos = xMin;
        yPos = yMin;
        this.width = width;
        this.height = height;
        this.energy = energy;
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(energy.getAmountAsInt()+" / "+energy.getCapacityAsInt()+" FE"));
    }

    public void render(GuiGraphicsExtractor guiGraphics) {
        int stored = (int)(height * (energy.getAmountAsInt() / (float)energy.getCapacityAsInt()));
        guiGraphics.fillGradient(xPos,yPos + (height - stored),xPos + width,
                yPos + height,0xffb51500, 0xff600b00);
    }
}