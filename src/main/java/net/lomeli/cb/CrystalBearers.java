package net.lomeli.cb;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.block.WorldGen;
import net.lomeli.cb.core.CommonProxy;
import net.lomeli.cb.core.Config;
import net.lomeli.cb.core.CreativeTabCrystals;
import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.Strings;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class CrystalBearers {

    @SidedProxy(clientSide = "net.lomeli.cb.core.ClientProxy", serverSide = "net.lomeli.cb.core.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs modTab = new CreativeTabCrystals(CreativeTabs.getNextID());

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());

        ElementRegistry.registerElements();

        ModBlocks.loadBlocks();
        ModItems.loadItems();

        FluidElements.loadFluids();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.registerTile();
        
        GameRegistry.registerWorldGenerator(new WorldGen());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }
}
