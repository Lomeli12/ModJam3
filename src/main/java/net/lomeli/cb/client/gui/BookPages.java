package net.lomeli.cb.client.gui;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.client.gui.pages.PageBase;
import net.lomeli.cb.client.gui.pages.PageCrafting;
import net.lomeli.cb.client.gui.pages.PageImage;
import net.lomeli.cb.client.gui.pages.PageItemDescription;
import net.lomeli.cb.client.gui.pages.PageMultiBlock;
import net.lomeli.cb.client.gui.pages.PageSmelting;
import net.lomeli.cb.client.gui.pages.PageText;
import net.lomeli.cb.client.gui.pages.PageTitle;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.PageInfo;
import net.lomeli.cb.network.PacketHandler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BookPages {
    private static String title = PageInfo.base + "manual", author = PageInfo.base + "author", power = PageInfo.base + "powerName";
    private static ItemStack[] factoryMultiBlock;

    public static void setPages(GuiDatabase gui, EntityPlayer player) {
        gui.pagesLeft.clear();
        gui.pagesRight.clear();
        setupMultiBlock();

        addPage(gui, null, new PageTitle(gui, StatCollector.translateToLocal(author), title));
        addPage(gui, new PageText(gui, PageInfo.quote, PageInfo.quotePerson), new PageText(gui, PageInfo.intro, PageInfo.introP2));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModBlocks.crystal), PageInfo.crystalP1, PageInfo.crystalP2), new PageImage(gui, PageInfo.imageSheet1, 0, 0, 105,
                59, PageInfo.crystalP3));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModItems.shard), PageInfo.shardP1, PageInfo.shardP2), new PageSmelting(gui, ModBlocks.oreShards[0]));
        addPage(gui, new PageCrafting(gui, ModBlocks.shardBlocks[0]), new PageCrafting(gui, new ItemStack(ModItems.scanner, 1, 1)));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModBlocks.smeltry), PageInfo.smelter), new PageCrafting(gui, new ItemStack(ModBlocks.smeltry)));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModBlocks.crystalizer), PageInfo.crystalizer), new PageCrafting(gui, new ItemStack(ModBlocks.crystalizer)));
        addPage(gui, new PageTitle(gui, "", power), new PageText(gui, PageInfo.power));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModItems.battery, 1, 0), PageInfo.battery), new PageCrafting(gui, new ItemStack(ModItems.battery, 1, 0)));
        addPage(gui, new PageCrafting(gui, new ItemStack(ModItems.battery, 1, 1)), new PageItemDescription(gui, new ItemStack(ModBlocks.chargeBox), PageInfo.charge));
        addPage(gui, new PageCrafting(gui, new ItemStack(ModBlocks.chargeBox, 1, 0)), new PageCrafting(gui, new ItemStack(ModBlocks.chargeBox, 1, 1)));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModBlocks.solarPanel), PageInfo.solar), new PageCrafting(gui, new ItemStack(ModBlocks.solarPanel)));
        addPage(gui, new PageItemDescription(gui, new ItemStack(ModBlocks.crystalFactory), PageInfo.factoryP1), new PageCrafting(gui, new ItemStack(ModBlocks.crystalFactory)));
        addPage(gui, new PageMultiBlock(gui, "Multiblock", factoryMultiBlock), new PageText(gui, PageInfo.factoryP2));
    }

    private static void setupMultiBlock() {
        factoryMultiBlock = new ItemStack[27];
        for(int i = 0; i < 27; i++) {
            if(i != 13)
                factoryMultiBlock[i] = new ItemStack(ModBlocks.crystalFactory);
        }
    }

    private static void addPage(GuiDatabase gui, PageBase left, PageBase right) {
        gui.pagesLeft.add(left);
        gui.pagesRight.add(right);
    }
}
