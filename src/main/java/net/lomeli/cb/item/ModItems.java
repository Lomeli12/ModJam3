package net.lomeli.cb.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.lib.ItemIds;

public class ModItems {
    public static Item shard;
    public static ItemStack[] shards;

    public static void loadItems() {
        shards = new ItemStack[5];
        new ItemDebugTool(500);

        shard = new ItemShard(ItemIds.shardID);
        for(int i = 0; i < shards.length; i++) {
            shards[i] = new ItemStack(shard, 1, i);
        }

        GameRegistry.registerItem(shard, "crystalShard");
    }
}
