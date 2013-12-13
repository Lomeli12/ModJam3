package net.lomeli.cb;

import net.minecraft.item.Item;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.core.CommonProxy;
import net.lomeli.cb.core.Config;
import net.lomeli.cb.item.ItemDebugTool;
import net.lomeli.cb.lib.Strings;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CrystalBearers {

    @SidedProxy(clientSide = "net.lomeli.cb.core.ClientProxy", serverSide = "net.lomeli.cb.core.CommonProxy")
    public static CommonProxy proxy;
    
    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
        
        ModBlocks.loadBlocks();
        
        Item debug = new ItemDebugTool(500);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerTile();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
