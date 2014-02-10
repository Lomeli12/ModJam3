package net.lomeli.cb.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModItems {
    public static Item shard, crystalItem, easterEgg, scanner, battery;
    public static ItemStack[] shards;

    public static void loadItems() {
        shards = new ItemStack[5];
        shard = new ItemShard();

        for (int i = 0; i < shards.length; i++) {
            shards[i] = new ItemStack(shard, 1, i);
        }

        crystalItem = new ItemCrystal();
        easterEgg = new ItemChickenRecord();
        scanner = new ItemScanner();
        battery = new ItemBattery();

        GameRegistry.registerItem(shard, "crystalShard");
        GameRegistry.registerItem(crystalItem, "crystalItem");
        GameRegistry.registerItem(easterEgg, "record");
        GameRegistry.registerItem(scanner, "scanner");
        GameRegistry.registerItem(battery, "battery");

        OreDictionary.registerOre("crystalShard", new ItemStack(shard, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("fireShard", shards[0]);
        OreDictionary.registerOre("waterShard", shards[1]);
        OreDictionary.registerOre("earthShard", shards[2]);
        OreDictionary.registerOre("airShard", shards[3]);
        OreDictionary.registerOre("darkShard", shards[4]);
    }
}
