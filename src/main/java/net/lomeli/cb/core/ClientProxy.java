package net.lomeli.cb.core;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.client.gui.SmallFontRenderer;
import net.lomeli.cb.client.render.RenderCrystal;
import net.lomeli.cb.client.render.RenderDarkChicken;
import net.lomeli.cb.client.render.RenderFireWolf;
import net.lomeli.cb.client.render.RenderGhostPig;
import net.lomeli.cb.client.render.RenderThunderCow;
import net.lomeli.cb.core.handler.IconHandler;
import net.lomeli.cb.core.handler.SoundHandler;
import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.entities.EntityFireWolf;
import net.lomeli.cb.entities.EntityGhostPig;
import net.lomeli.cb.entities.EntityThunderCow;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.tile.TileCrystal;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    public static SmallFontRenderer smallFontRenderer;

    @Override
    public void registerTile() {
        super.registerTile();
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrystal.class, new RenderCrystal());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ModBlocks.crystal), new RenderCrystal());
        MinecraftForgeClient.registerItemRenderer(ModItems.crystalItem, new RenderCrystal());
    }

    @Override
    public void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFireWolf.class, new RenderFireWolf());
        RenderingRegistry.registerEntityRenderingHandler(EntityDarkChicken.class, new RenderDarkChicken());
        RenderingRegistry.registerEntityRenderingHandler(EntityThunderCow.class, new RenderThunderCow());
        RenderingRegistry.registerEntityRenderingHandler(EntityGhostPig.class, new RenderGhostPig());

        MinecraftForge.EVENT_BUS.register(SoundHandler.getSoundHandler());
        MinecraftForge.EVENT_BUS.register(new IconHandler());

        Minecraft mc = Minecraft.getMinecraft();
        smallFontRenderer = new SmallFontRenderer(mc.gameSettings, new ResourceLocation("minecraft:textures/font/ascii.png"), mc.renderEngine, false);
    }
}
