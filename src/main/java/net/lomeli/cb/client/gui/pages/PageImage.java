package net.lomeli.cb.client.gui.pages;

import java.awt.Color;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class PageImage extends PageBase {
    private Icon image;
    private String[] pageText;

    public PageImage(GuiScreen gui, Icon image, String... imageDescription) {
        super(gui);
        this.image = image;
        pageText = imageDescription;
    }

    @Override
    public void draw() {
        super.draw();
        if (image != null) {
            gui.drawTexturedModelRectFromIcon(x, y, image, image.getIconWidth() + 1, image.getIconHeight() + 1);

            int textHeight = y + image.getIconHeight() + 10;
            if (pageText != null) {
                for (int i = 0; i < pageText.length; i++) {
                    smallFontRenderer.drawSplitString(StatCollector.translateToLocal(pageText[i]), x, textHeight, width, Color.BLACK.getRGB());
                    textHeight += smallFontRenderer.getStringHeight(StatCollector.translateToLocal(pageText[i]), width) + 20;
                }
            }
        }
    }
}
