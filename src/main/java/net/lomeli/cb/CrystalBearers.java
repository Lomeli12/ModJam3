package net.lomeli.cb;

import java.util.EnumMap;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.block.WorldGen;
import net.lomeli.cb.core.CommonProxy;
import net.lomeli.cb.core.Config;
import net.lomeli.cb.core.CreativeTabCrystals;
import net.lomeli.cb.core.ModRecipes;
import net.lomeli.cb.core.handler.BlockBreakHandler;
import net.lomeli.cb.core.handler.EntityLivingHandler;
import net.lomeli.cb.core.handler.GuiHandler;
import net.lomeli.cb.core.handler.PacketHandler;
import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.entities.ModEntities;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.Strings;

import net.minecraft.creativetab.CreativeTabs;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION)
public class CrystalBearers {

    @Mod.Instance(Strings.MOD_ID)
    public static CrystalBearers instance;

    @SidedProxy(clientSide = "net.lomeli.cb.core.ClientProxy", serverSide = "net.lomeli.cb.core.CommonProxy")
    public static CommonProxy proxy;

    public static CreativeTabs modTab = new CreativeTabCrystals(CreativeTabs.getNextID());

    public static PacketHandler pktHandler;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());

        ElementRegistry.registerElements();

        ModBlocks.loadBlocks();
        ModItems.loadItems();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        pktHandler = new PacketHandler();
        pktHandler.init();

        proxy.registerTile();
        proxy.registerRenders();
        FluidElements.loadFluids();
        FluidElements.registerFluids();

        ModEntities.loadEntities();

        MinecraftForge.EVENT_BUS.register(new EntityLivingHandler());
        MinecraftForge.EVENT_BUS.register(new BlockBreakHandler());

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        GameRegistry.registerWorldGenerator(new WorldGen(), 0);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModRecipes.loadRecipes();
        pktHandler.postInit();
    }
}
