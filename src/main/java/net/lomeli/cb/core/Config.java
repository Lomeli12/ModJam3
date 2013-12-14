package net.lomeli.cb.core;

import java.io.File;

import net.minecraftforge.common.Configuration;

import net.lomeli.cb.lib.BlockIds;

public class Config {
    private static Configuration config;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        config.load();

        loadBlockID();

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
    }
}
