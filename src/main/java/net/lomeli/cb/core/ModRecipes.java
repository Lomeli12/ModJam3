package net.lomeli.cb.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import cpw.mods.fml.common.registry.GameRegistry;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.item.ModItems;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ModRecipes {
    public static void loadRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.smeltry, true, "CBC", "IFI", "III", 'C', "cobblestone", 'B',
                Item.bucketEmpty, 'I', Item.ingotIron, 'F', Block.furnaceIdle));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.crystalizer, true, "CWC", "IFI", "III", 'C', "cobblestone", 'W',
                Block.cloth, 'I', "crystalShard", 'F', Block.dropper));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.scanner, true, "ISI", "ICI", "III", 'I', Item.ingotIron, 'C',
                "crystalShard", 'S', "stickWood"));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[0], "fireShard", "fireShard", "fireShard",
                "fireShard", "fireShard", "fireShard", "fireShard", "fireShard", "fireShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[1], "waterShard", "waterShard", "waterShard",
                "waterShard", "waterShard", "waterShard", "waterShard", "waterShard", "waterShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[2], "earthShard", "earthShard", "earthShard",
                "earthShard", "earthShard", "earthShard", "earthShard", "earthShard", "earthShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[3], "airShard", "airShard", "airShard", "airShard",
                "airShard", "airShard", "airShard", "airShard", "airShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[4], "darkShard", "darkShard", "darkShard",
                "darkShard", "darkShard", "darkShard", "darkShard", "darkShard", "darkShard"));

        GameRegistry.addSmelting(ModBlocks.darkCobble.blockID, new ItemStack(ModBlocks.darkStone), 2);

        FurnaceRecipes.smelting().addSmelting(ModBlocks.oreShard.blockID, 0, ModItems.shards[0], 5);
        FurnaceRecipes.smelting().addSmelting(ModBlocks.oreShard.blockID, 1, ModItems.shards[1], 5);
        FurnaceRecipes.smelting().addSmelting(ModBlocks.oreShard.blockID, 2, ModItems.shards[2], 5);
        FurnaceRecipes.smelting().addSmelting(ModBlocks.oreShard.blockID, 3, ModItems.shards[3], 5);
        FurnaceRecipes.smelting().addSmelting(ModBlocks.oreShard.blockID, 4, ModItems.shards[4], 5);
    }

}
