package net.lomeli.cb.core;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.tile.TileCrystal;

public class CommonProxy {
    public void registerTile() {
        GameRegistry.registerTileEntity(TileCrystal.class, "net.lomeli.cb.tileCrystal");
    }
}
