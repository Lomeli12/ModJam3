package net.lomeli.cb.block;

import net.lomeli.cb.block.BlockChargeBlock.ItemChargeBlock;
import net.lomeli.cb.block.BlockOreShard.ItemOreBlock;
import net.lomeli.cb.block.BlockShardBlock.ItemShardBlock;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

public class ModBlocks {
    public static Block darkStone, darkCobble, crystal, smeltry, crystalizer, shardBlock, oreShard, chargeBox, solarPanel, crystalFactory, modularCrystal;

    public static ItemStack[] shardBlocks, oreShards;

    public static void loadBlocks() {
        shardBlocks = new ItemStack[5];
        oreShards = new ItemStack[5];

        darkStone = new BlockDarkStone("stone").setBlockName("stone");
        darkCobble = new BlockDarkStone("cobble").setBlockName("cobble");
        crystal = new BlockCrystal().setBlockName("crystal");

        smeltry = new BlockSmelter();
        crystalizer = new BlockCrystalizer();
        shardBlock = new BlockShardBlock();
        oreShard = new BlockOreShard();
        chargeBox = new BlockChargeBlock();
        solarPanel = new BlockSolarPanel();
        crystalFactory = new BlockCrystalFactory();

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

        //MinecraftForge.setBlockHarvestLevel(oreShard, "pickaxe", 1);

        OreDictionary.registerOre("oreFireShard", oreShards[0]);
        OreDictionary.registerOre("oreWaterShard", oreShards[1]);
        OreDictionary.registerOre("oreEarthShard", oreShards[2]);
        OreDictionary.registerOre("oreAirShard", oreShards[3]);
        OreDictionary.registerOre("oreDarkShard", oreShards[4]);
    }
}
