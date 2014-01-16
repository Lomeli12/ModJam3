package net.lomeli.cb.core.handler;

import net.lomeli.cb.block.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler {

    @Override
    public void onCrafting(EntityPlayer player, ItemStack item, IInventory craftMatrix) {
        String author = "Lomeli12";
        String title1 = StatCollector.translateToLocal("book.crystalbearers:SmeltBookName"), title2 = StatCollector.translateToLocal("book.crystalbearers:CrystalizerName");
        String page1 = StatCollector.translateToLocal("book.crystalbearers:SmeltBook"), page2 = StatCollector.translateToLocal("book.crystalbearers:Crystalizer");
        if (item.itemID == ModBlocks.crystalizer.blockID) {
            if (!player.getEntityData().getBoolean("hasCrystalizerBook")) {
                player.inventory.addItemStackToInventory(newBook(author, title2, page2));
                player.getEntityData().setBoolean("hasCrystalizerBook", true);
            }
        } else if (item.itemID == ModBlocks.smeltry.blockID) {
            if (!player.getEntityData().getBoolean("hasCrystalSmelterBook")) {
                player.inventory.addItemStackToInventory(newBook(author, title1, page1));
                player.getEntityData().setBoolean("hasCrystalSmelterBook", true);
            }
        }
    }

    @Override
    public void onSmelting(EntityPlayer player, ItemStack item) {
    }

    public static ItemStack newBook(String author, String title, String text) {
        ItemStack book = new ItemStack(Item.writtenBook);
        if (book.getTagCompound() == null)
            book.stackTagCompound = new NBTTagCompound();
        book.getTagCompound().setString("author", author);
        book.getTagCompound().setString("title", title);
        NBTTagList page = new NBTTagList();
        page.appendTag(new NBTTagString("1", text));
        book.setTagInfo("pages", page);
        return book;
    }

}
