package net.lomeli.cb.core;

import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.Strings;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabCrystals extends CreativeTabs {

    public CreativeTabCrystals(int j) {
        super(j, Strings.MOD_ID.toLowerCase());
    }

    @Override
    public Item getTabIconItem() {
        return ModItems.shard;
    }

}
