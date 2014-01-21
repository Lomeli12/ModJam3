package net.lomeli.cb.client.gui.pages;

import java.awt.Color;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.StatCollector;

public class PageText extends PageBase{
    
    private String text;

    public PageText(int x, int y, GuiScreen gui, String text) {
        super(x, y, gui);
        this.text = text;
    }
    
    public PageText(GuiScreen gui, String text) {
        super(gui);
        this.text = text;
    }
    
    @Override
    public void draw() {
        super.draw();
        smallFontRenderer.drawSplitString(StatCollector.translateToLocal(text), x, y + 5, width, Color.BLACK.getRGB());
    }

}
