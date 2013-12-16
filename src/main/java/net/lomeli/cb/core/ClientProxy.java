package net.lomeli.cb.core;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.client.render.RenderGhostPig;
import net.lomeli.cb.client.render.RenderCrystal;
import net.lomeli.cb.client.render.RenderDarkChicken;
import net.lomeli.cb.client.render.RenderFireWolf;
import net.lomeli.cb.client.render.RenderThunderCow;
import net.lomeli.cb.core.handler.SoundHandler;
import net.lomeli.cb.entities.EntityGhostPig;
import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.entities.EntityFireWolf;
import net.lomeli.cb.entities.EntityThunderCow;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.tile.TileCrystal;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerTile() {
        super.registerTile();
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new RenderCrystal());
        MinecraftForgeClient.registerItemRenderer(ModBlocks.crystal.blockID, new RenderCrystal());
        MinecraftForgeClient.registerItemRenderer(ModItems.crystalItem.itemID, new RenderCrystal());
    }

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFireWolf.class, new RenderFireWolf());
        RenderingRegistry.registerEntityRenderingHandler(EntityDarkChicken.class, new RenderDarkChicken());
        RenderingRegistry.registerEntityRenderingHandler(EntityThunderCow.class, new RenderThunderCow());
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostPig.class, new RenderGhostPig());

        MinecraftForge.EVENT_BUS.register(SoundHandler.getSoundHandler());
    }
}
