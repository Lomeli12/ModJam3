package net.lomeli.cb.core;

import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.Strings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabCrystals extends CreativeTabs {

    public CreativeTabCrystals(int j) {
        super(j, Strings.MOD_ID.toLowerCase());
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModItems.shard);
    }

}
