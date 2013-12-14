package net.lomeli.cb.block;

import net.minecraft.block.Block;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.lib.BlockIds;

public class ModBlocks {
    public static Block darkStone, darkCobble, crystal, lavaStone, smeltry, crystalizer;

    public static void loadBlocks() {
        darkStone = new BlockDarkStone(BlockIds.darkStoneID, "stone").setUnlocalizedName("stone");
        darkCobble = new BlockDarkStone(BlockIds.darkCobbleID, "cobble").setUnlocalizedName("cobble");
        crystal = new BlockCrystal(BlockIds.crystalID).setUnlocalizedName("crystal");

        registerBlock();
    }

    private static void registerBlock() {
        GameRegistry.registerBlock(darkStone, "darkStone");
        GameRegistry.registerBlock(darkCobble, "darkCobble");
        GameRegistry.registerBlock(crystal, "crystal");
    }
}
