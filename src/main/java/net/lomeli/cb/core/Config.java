package net.lomeli.cb.core;

import java.io.File;

import net.minecraftforge.common.Configuration;

import net.lomeli.cb.entities.ModEntities;
import net.lomeli.cb.lib.BlockIds;
import net.lomeli.cb.lib.ItemIds;

public class Config {
    private static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        config.load();

        loadBlockID();
        loadItemID();
        loadEntityIDs();

        config.save();
    }

    private static void loadBlockID() {
        String block = "Blocks";

        BlockIds.darkStoneID = config.get(block, "darkStone", 450).getInt(450);
        BlockIds.darkCobbleID = config.get(block, "darkCobble", 451).getInt(451);
        BlockIds.laveStoneID = config.get(block, "lavaStone", 452).getInt(452);
        BlockIds.crystalID = config.get(block, "crystal", 453).getInt(453);
        BlockIds.smelteryID = config.get(block, "smeltery", 454).getInt(454);
        BlockIds.crystalizerID = config.get(block, "crystalizer", 455).getInt(455);
        BlockIds.shardBlockID = config.get(block, "shardBlock", 456).getInt(456);
        BlockIds.oreShardID = config.get(block, "oreShard", 457).getInt(457);
    }

    private static void loadItemID() {
        String item = "Items";

        ItemIds.shardID = config.get(item, "shard", 6000).getInt(6000);
        ItemIds.crystalItemID = config.get(item, "crystalItem", 6001).getInt(6001);
        ItemIds.recordID = config.get(item, "notaneasteregg", 6002).getInt(6002);
        ItemIds.scannerID = config.get(item, "scanner", 6003).getInt(6003);
    }

    private static void loadEntityIDs() {
        String entity = "Entity";

        ModEntities.fireWolfID = config.get(entity, "fireWolf", 110).getInt(110);
        ModEntities.darkChickenID = config.get(entity, "darkChicken", 111).getInt(111);
        ModEntities.thunderCowId = config.get(entity, "thunderCow", 112).getInt(112);
    }
}
