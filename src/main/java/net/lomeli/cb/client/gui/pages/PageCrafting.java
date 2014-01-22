package net.lomeli.cb.client.gui.pages;

import java.awt.Color;

import net.lomeli.cb.lib.Strings;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

/**
 * Allows you to add a page with a crafting recipe. ATM only works with Vanilla,
 * IC2, and Forge OreDictionary Recipes, more to come.
 * 
 * @author Anthony
 * 
 */
public class PageCrafting extends PageBase {
    private ItemStack[] items;
    private ItemStack output;

    public PageCrafting(GuiScreen gui, ItemStack output) {
        super(gui);
        this.output = output;
        items = ItemRecipeUtil.getItemShapedRecipe(output);
    }

    public PageCrafting(int x, int y, GuiScreen gui, ItemStack output) {
        super(x, y, gui);
        this.output = output;
        items = ItemRecipeUtil.getItemShapedRecipe(output);
    }

    @Override
    public void draw() {
        smallFontRenderer.drawStringWithShadow(output.getDisplayName(), x + (width / 2) - (smallFontRenderer.getStringWidth(output.getDisplayName()) / 2), y + 2,
                Color.YELLOW.getRGB());
        
        super.draw();
        renderSlot(x + 43, y + 13);
        itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, output, x + 45, y + 15);

        if (items.length > 0) {
            if (items[0] != null) {
                renderSlot(x + 23, y + 48);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[0], x + 25, y + 50);
            }
            if (items[1] != null) {
                renderSlot(x + 43, y + 48);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[1], x + 45, y + 50);
            }
            if (items[2] != null) {
                renderSlot(x + 63, y + 48);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[2], x + 65, y + 50);
            }

            if (items[3] != null) {
                renderSlot(x + 23, y + 68);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[3], x + 25, y + 70);
            }
            if (items[4] != null) {
                renderSlot(x + 43, y + 68);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[4], x + 45, y + 70);
            }
            if (items[5] != null) {
                renderSlot(x + 63, y + 68);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[5], x + 65, y + 70);
            }

            if (items[6] != null) {
                renderSlot(x + 23, y + 88);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[6], x + 25, y + 90);
            }
            if (items[7] != null) {
                renderSlot(x + 43, y + 88);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[7], x + 45, y + 90);
            }
            if (items[8] != null) {
                renderSlot(x + 63, y + 88);
                itemRenderer.renderItemAndEffectIntoGUI(largeFontRenderer, mc.renderEngine, items[8], x + 65, y + 90);
            }
        } else
            smallFontRenderer.drawSplitString(StatCollector.translateToLocal(Strings.INVALID_RECIPE), x + 5, y + 25, width, Color.BLACK.getRGB());
    }

    public void renderSlot(int slotX, int slotY) {
        bindTexture(prop);
        gui.drawTexturedModalRect(slotX, slotY, 35, 236, 20, 20);
    }
}
