package net.lomeli.cb.core;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.item.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import cpw.mods.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void loadRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.smeltry, true, "CBC", "IFI", "III", 'C', "cobblestone", 'B', Items.bucket, 'I', Items.iron_ingot, 'F',
                Blocks.furnace));
        GameRegistry
                .addRecipe(new ShapedOreRecipe(ModBlocks.crystalizer, true, "CWC", "IFI", "III", 'C', "cobblestone", 'W', Blocks.wool, 'I', "crystalShard", 'F', Blocks.dropper));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.scanner, true, "ISI", "ICI", "III", 'I', Items.iron_ingot, 'C', "crystalShard", 'S', "stickWood"));

        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[0], "fireShard", "fireShard", "fireShard", "fireShard", "fireShard", "fireShard", "fireShard",
                "fireShard", "fireShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[1], "waterShard", "waterShard", "waterShard", "waterShard", "waterShard", "waterShard", "waterShard",
                "waterShard", "waterShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[2], "earthShard", "earthShard", "earthShard", "earthShard", "earthShard", "earthShard", "earthShard",
                "earthShard", "earthShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[3], "airShard", "airShard", "airShard", "airShard", "airShard", "airShard", "airShard", "airShard",
                "airShard"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(ModBlocks.shardBlocks[4], "darkShard", "darkShard", "darkShard", "darkShard", "darkShard", "darkShard", "darkShard",
                "darkShard", "darkShard"));

        GameRegistry.addSmelting(ModBlocks.darkCobble, new ItemStack(ModBlocks.darkStone), 2);

        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.oreShard, 1, 0), ModItems.shards[0], 5);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.oreShard, 1, 1), ModItems.shards[1], 5);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.oreShard, 1, 2), ModItems.shards[2], 5);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.oreShard, 1, 3), ModItems.shards[3], 5);
        FurnaceRecipes.smelting().func_151394_a(new ItemStack(ModBlocks.oreShard, 1, 4), ModItems.shards[4], 5);

        GameRegistry.addRecipe(new ShapedOreRecipe(ModItems.battery, true, "RIR", "ISI", "RIR", 'S', "crystalShard", 'I', Items.iron_ingot, 'R', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.battery, 1, 1), true, "EGE", "GBG", "SRS", 'E', Items.ender_eye, 'G', Items.gold_ingot, 'R',
                Blocks.redstone_block, 'S', "crystalShard", 'B', new ItemStack(ModItems.battery, 1, 0)));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModItems.scanner, 1, 1), Items.book, "crystalShard"));
        GameRegistry
                .addRecipe(new ShapedOreRecipe(ModBlocks.chargeBox, true, "RWR", "BBB", "WRW", 'R', Items.redstone, 'W', "plankWood", 'B', new ItemStack(ModItems.battery, 1, 0)));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.chargeBox, 1, 1), true, "IGI", "BCB", "III", 'I', Items.iron_ingot, 'G', Items.glowstone_dust, 'C',
                new ItemStack(ModBlocks.chargeBox, 1, 0), 'B', new ItemStack(ModItems.battery, 1, 1)));
        GameRegistry.addRecipe(new ShapedOreRecipe(ModBlocks.solarPanel, true, "GGG", "BSB", "OPO", 'G', Blocks.glass, 'S', Blocks.daylight_detector, 'O', Blocks.obsidian, 'B',
                new ItemStack(ModItems.battery, 1, 0), 'P', "crystalShard"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.crystalFactory, 2), true, "ICI", "GBG", "ISI", 'I', Items.iron_ingot, 'G', Items.gold_ingot, 'C',
                ModBlocks.crystalizer, 'S', ModBlocks.smeltry, 'B', new ItemStack(ModBlocks.chargeBox, 1, 1)));
    }

}
