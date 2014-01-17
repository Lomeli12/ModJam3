package net.lomeli.cb.core;

import net.lomeli.cb.tile.TileChargeBox;
import net.lomeli.cb.tile.TileCrystal;
import net.lomeli.cb.tile.TileCrystalFactory;
import net.lomeli.cb.tile.TileCrystalSmelter;
import net.lomeli.cb.tile.TileCrystalizer;
import net.lomeli.cb.tile.TileSolarPanel;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
    public void registerTile() {
        GameRegistry.registerTileEntity(TileCrystal.class, "net.lomeli.cb.tileCrystal");
        GameRegistry.registerTileEntity(TileCrystalizer.class, "net.lomeli.cb.tileCrystalizer");
        GameRegistry.registerTileEntity(TileCrystalSmelter.class, "net.lomeli.cb.tileCrystalSmelter");
        GameRegistry.registerTileEntity(TileCrystalFactory.class, "net.lomeli.cb.tileCrystalFactory");
        GameRegistry.registerTileEntity(TileChargeBox.class, "net.lomeli.cb.tileChargeBox");
        GameRegistry.registerTileEntity(TileSolarPanel.class, "net.lomeli.cb.tileSolarPanel");
    }

    public void registerRenders() {
    }
}
