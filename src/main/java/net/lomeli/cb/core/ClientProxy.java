package net.lomeli.cb.core;

import cpw.mods.fml.client.registry.ClientRegistry;

import net.lomeli.cb.client.render.RenderCrystal;
import net.lomeli.cb.tile.TileCrystal;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTile() {
        super.registerTile();
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new RenderCrystal());
    }
}
