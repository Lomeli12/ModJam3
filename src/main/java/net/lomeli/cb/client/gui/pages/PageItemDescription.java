package net.lomeli.cb.client.gui.pages;

import java.awt.Color;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PageItemDescription extends PageBase{
    private ItemStack stack;
    private String itemInfo;
    
    public PageItemDescription(int x, int y, GuiScreen gui, ItemStack item, String description) {
        super(x, y, gui);
        stack = item;
        itemInfo = description;
    }
    
    public PageItemDescription(GuiScreen gui, ItemStack item, String description) {
        super(gui);
        stack = item;
        itemInfo = description;
    }

    @Override
    public void draw() {
        super.draw();
        itemRenderer.renderItemIntoGUI(largeFontRenderer, mc.renderEngine, stack, x, y, false);
        largeFontRenderer.drawStringWithShadow(stack.getDisplayName(), x + 20, y + 2, Color.YELLOW.getRGB());
        smallFontRenderer.drawSplitString(StatCollector.translateToLocal(itemInfo), x, y + 20, width, Color.BLACK.getRGB());
    }
}
