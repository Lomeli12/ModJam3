package net.lomeli.cb.core;

import net.lomeli.cb.tile.TileCrystal;
import net.lomeli.cb.tile.TileCrystalSmelter;
import net.lomeli.cb.tile.TileCrystalizer;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerTile() {
        GameRegistry.registerTileEntity(TileCrystal.class, "net.lomeli.cb.tileCrystal");
        GameRegistry.registerTileEntity(TileCrystalizer.class, "net.lomeli.cb.tileCrystalizer");
        GameRegistry.registerTileEntity(TileCrystalSmelter.class, "net.lomeli.cb.tileCrystalSmelter");
    }

    public void registerRenders() {
    }
}
