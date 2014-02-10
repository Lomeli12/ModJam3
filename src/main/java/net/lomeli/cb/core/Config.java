package net.lomeli.cb.core;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
    private static Configuration config;
    public static int crystalTick;

    public static void loadConfig(File configFile) {
        config = new Configuration(configFile);

        config.load();

        crystalTick = config.get("Options", "crystalTick", 600, "How many ticks of cool time between each crystal's ability usage (default 600)").getInt(600);

        config.save();
    }
}
