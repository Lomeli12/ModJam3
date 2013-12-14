package net.lomeli.cb.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.block.BlockOreShard.ItemOreBlock;
import net.lomeli.cb.block.BlockShardBlock.ItemShardBlock;
import net.lomeli.cb.lib.BlockIds;

public class ModBlocks {
    public static Block darkStone, darkCobble, crystal, lavaStone, smeltry, crystalizer, shardBlock, oreShard;

    public static ItemStack[] shardBlocks, oreShards;

    public static void loadBlocks() {
        shardBlocks = oreShards = new ItemStack[5];

        darkStone = new BlockDarkStone(BlockIds.darkStoneID, "stone").setUnlocalizedName("stone");
        darkCobble = new BlockDarkStone(BlockIds.darkCobbleID, "cobble").setUnlocalizedName("cobble");
        crystal = new BlockCrystal(BlockIds.crystalID).setUnlocalizedName("crystal");

        shardBlock = new BlockShardBlock(BlockIds.shardBlockID);
        oreShard = new BlockOreShard(BlockIds.oreShardID);

        registerBlock();
    }

    private static void registerBlock() {
        GameRegistry.registerBlock(darkStone, "darkStone");
        GameRegistry.registerBlock(darkCobble, "darkCobble");
        GameRegistry.registerBlock(crystal, "crystal");

        GameRegistry.registerBlock(shardBlock, ItemShardBlock.class, "shardBlock");
        GameRegistry.registerBlock(oreShard, ItemOreBlock.class, "oreShard");
        for(int i = 0; i < 5; i++) {
            shardBlocks[i] = new ItemStack(shardBlock, 1, i);
            oreShards[i] = new ItemStack(oreShard, 1, i);
        }
    }
}
