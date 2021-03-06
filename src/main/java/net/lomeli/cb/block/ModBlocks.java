package net.lomeli.cb.block;

import net.lomeli.cb.block.BlockChargeBlock.ItemChargeBlock;
import net.lomeli.cb.block.BlockOreShard.ItemOreBlock;
import net.lomeli.cb.block.BlockShardBlock.ItemShardBlock;
import net.lomeli.cb.lib.BlockIds;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
    public static Block darkStone, darkCobble, crystal, smeltry, crystalizer, shardBlock, oreShard, chargeBox, solarPanel, crystalFactory, modularCrystal;

    public static ItemStack[] shardBlocks, oreShards;

    public static void loadBlocks() {
        shardBlocks = new ItemStack[5];
        oreShards = new ItemStack[5];

        darkStone = new BlockDarkStone(BlockIds.darkStoneID, "stone").setUnlocalizedName("stone");
        darkCobble = new BlockDarkStone(BlockIds.darkCobbleID, "cobble").setUnlocalizedName("cobble");
        crystal = new BlockCrystal(BlockIds.crystalID).setUnlocalizedName("crystal");

        smeltry = new BlockSmelter(BlockIds.smelteryID);
        crystalizer = new BlockCrystalizer(BlockIds.crystalizerID);
        shardBlock = new BlockShardBlock(BlockIds.shardBlockID);
        oreShard = new BlockOreShard(BlockIds.oreShardID);
        chargeBox = new BlockChargeBlock(BlockIds.chargeBoxID);
        solarPanel = new BlockSolarPanel(BlockIds.solarPanelID);
        crystalFactory = new BlockCrystalFactory(BlockIds.crystalFactoryID);

        registerBlock();
    }

    private static void registerBlock() {
        GameRegistry.registerBlock(darkStone, "darkStone");
        GameRegistry.registerBlock(darkCobble, "darkCobble");
        GameRegistry.registerBlock(crystal, "crystal");
        GameRegistry.registerBlock(solarPanel, "solarPanel");
        GameRegistry.registerBlock(crystalFactory, "crystalFactory");

        GameRegistry.registerBlock(smeltry, "smelter");
        GameRegistry.registerBlock(crystalizer, "crystalizer");
        GameRegistry.registerBlock(shardBlock, ItemShardBlock.class, "shardBlock");
        GameRegistry.registerBlock(oreShard, ItemOreBlock.class, "oreShard");
        GameRegistry.registerBlock(chargeBox, ItemChargeBlock.class, "chargeBlock");

        for (int i = 0; i < 5; i++) {
            shardBlocks[i] = new ItemStack(shardBlock, 1, i);
            oreShards[i] = new ItemStack(oreShard, 1, i);
        }

        MinecraftForge.setBlockHarvestLevel(oreShard, "pickaxe", 1);

        OreDictionary.registerOre("oreFireShard", oreShards[0]);
        OreDictionary.registerOre("oreWaterShard", oreShards[1]);
        OreDictionary.registerOre("oreEarthShard", oreShards[2]);
        OreDictionary.registerOre("oreAirShard", oreShards[3]);
        OreDictionary.registerOre("oreDarkShard", oreShards[4]);
    }
}
