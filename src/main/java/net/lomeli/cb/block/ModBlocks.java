package net.lomeli.cb.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.block.BlockShardBlock.ItemShardBlock;
import net.lomeli.cb.lib.BlockIds;

public class ModBlocks {
    public static Block darkStone, darkCobble, crystal, lavaStone, smeltry, crystalizer, shardBlock;
    
    public static ItemStack[] shardBlocks;

    public static void loadBlocks() {
        shardBlocks = new ItemStack[5];
        
        darkStone = new BlockDarkStone(BlockIds.darkStoneID, "stone").setUnlocalizedName("stone");
        darkCobble = new BlockDarkStone(BlockIds.darkCobbleID, "cobble").setUnlocalizedName("cobble");
        crystal = new BlockCrystal(BlockIds.crystalID).setUnlocalizedName("crystal");
        
        
        shardBlock = new BlockShardBlock(BlockIds.shardBlockID);

        registerBlock();
    }

    private static void registerBlock() {
        GameRegistry.registerBlock(darkStone, "darkStone");
        GameRegistry.registerBlock(darkCobble, "darkCobble");
        GameRegistry.registerBlock(crystal, "crystal");
        
        GameRegistry.registerBlock(shardBlock, ItemShardBlock.class, "shardBlock");
        for(int i = 0; i < 5; i++){
            shardBlocks[i] = new ItemStack(shardBlock, 1, i);
        }
    }
}
