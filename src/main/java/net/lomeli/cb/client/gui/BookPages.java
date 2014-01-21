package net.lomeli.cb.client.gui;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.client.gui.pages.PageBase;
import net.lomeli.cb.client.gui.pages.PageImage;
import net.lomeli.cb.client.gui.pages.PageItemDescription;
import net.lomeli.cb.client.gui.pages.PageText;
import net.lomeli.cb.client.gui.pages.PageTitle;
import net.lomeli.cb.lib.PageInfo;

import net.minecraft.item.ItemStack;

public class BookPages {
    private static String title = PageInfo.base + "manual";

    public static void setPages(GuiDatabase gui) {
        GuiDatabase.pagesLeft.clear();
        GuiDatabase.pagesRight.clear();
        addPage(null, new PageTitle(gui, "By: Im A. Riter", title));
        addPage(new PageText(gui, PageInfo.quote, PageInfo.quotePerson), new PageText(gui, PageInfo.intro, PageInfo.introP2));
        addPage(new PageItemDescription(gui, new ItemStack(ModBlocks.crystal), PageInfo.crystalP1, PageInfo.crystalP2), new PageImage(gui, PageInfo.crystal, ""));

    }

    private static void addPage(PageBase left, PageBase right) {
        GuiDatabase.pagesLeft.add(left);
        GuiDatabase.pagesRight.add(right);
    }
}
