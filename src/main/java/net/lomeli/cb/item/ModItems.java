package net.lomeli.cb.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.lib.ItemIds;

public class ModItems {
    public static Item shard, crystalItem;
    public static ItemStack[] shards;

    public static void loadItems() {
        shards = new ItemStack[5];
        new ItemDebugTool(500);

        shard = new ItemShard(ItemIds.shardID);
        
        for(int i = 0; i < shards.length; i++) {
            shards[i] = new ItemStack(shard, 1, i);
        }
        crystalItem = new ItemCrystal(ItemIds.crystalItemID);

        GameRegistry.registerItem(shard, "crystalShard");
        GameRegistry.registerItem(crystalItem, "crystalItem");
        
        OreDictionary.registerOre("fireShard", shards[0]);
        OreDictionary.registerOre("waterShard", shards[1]);
        OreDictionary.registerOre("earthShard", shards[2]);
        OreDictionary.registerOre("airShard", shards[3]);
        OreDictionary.registerOre("darkShard", shards[4]);
    }
}
