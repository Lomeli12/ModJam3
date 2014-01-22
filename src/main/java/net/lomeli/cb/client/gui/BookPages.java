package net.lomeli.cb.client.gui;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.client.gui.pages.PageBase;
import net.lomeli.cb.client.gui.pages.PageCrafting;
import net.lomeli.cb.client.gui.pages.PageImage;
import net.lomeli.cb.client.gui.pages.PageItemDescription;
import net.lomeli.cb.client.gui.pages.PageSmelting;
import net.lomeli.cb.client.gui.pages.PageText;
import net.lomeli.cb.client.gui.pages.PageTitle;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.PageInfo;

import net.minecraft.item.ItemStack;

public class BookPages {
    private static String title = PageInfo.base + "manual";
    private static ItemStack[] factoryMultiBlock;

    public static void setPages(GuiDatabase gui) {
        GuiDatabase.pagesLeft.clear();
        GuiDatabase.pagesRight.clear();
        setupMultiBlock();

        addPage(null, new PageTitle(gui, "By: Ima W. Riter", title));
        addPage(new PageText(gui, PageInfo.quote, PageInfo.quotePerson), new PageText(gui, PageInfo.intro, PageInfo.introP2));
        addPage(new PageItemDescription(gui, new ItemStack(ModBlocks.crystal), PageInfo.crystalP1, PageInfo.crystalP2), new PageImage(gui, PageInfo.imageSheet1, 0, 0, 105, 59,
                PageInfo.crystalP3));
        addPage(new PageItemDescription(gui, new ItemStack(ModItems.shard), PageInfo.shardP1, PageInfo.shardP2).setTag(PageInfo.crystalTag), new PageSmelting(gui,
                ModBlocks.oreShards[0]).setTag(PageInfo.crystalTag));
        addPage(new PageCrafting(gui, ModBlocks.shardBlocks[0]).setTag(PageInfo.crystalTag),
                new PageCrafting(gui, new ItemStack(ModItems.scanner, 1, 1)).setTag(PageInfo.crystalTag));
    }

    private static void setupMultiBlock() {
        factoryMultiBlock = new ItemStack[27];
        for (int i = 0; i < 27; i++) {
            if (i != 13)
                factoryMultiBlock[i] = new ItemStack(ModBlocks.crystalFactory);
        }
    }

    private static void addPage(PageBase left, PageBase right) {
        GuiDatabase.pagesLeft.add(left);
        GuiDatabase.pagesRight.add(right);
    }
}
