package net.lomeli.cb.abilities.earth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

public class AbilityTerrainDesecrator extends CrystalAbility {

    private List<Block> blockList;

    public AbilityTerrainDesecrator() {
        this.blockList = new ArrayList<Block>();

        for(ItemStack saplings : OreDictionary.getOres("treeSapling")) {
            if(saplings != null)
                this.blockList.add(Block.getBlockFromName(saplings.getUnlocalizedName()));
        }
        for(ItemStack log : OreDictionary.getOres("logWood")) {
            if(log != null)
                this.blockList.add(Block.getBlockFromName(log.getUnlocalizedName()));
        }
        for(ItemStack leaf : OreDictionary.getOres("treeLeaves")) {
            if(leaf != null)
                this.blockList.add(Block.getBlockFromName(leaf.getUnlocalizedName()));
        }
        this.blockList.add(Blocks.grass);
        this.blockList.add(Blocks.tallgrass);
        this.blockList.add(Blocks.red_flower);
        this.blockList.add(Blocks.yellow_flower);
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for(int x1 = x - radius; x1 <= x + radius; x1++)
            for(int y1 = y - radius; y1 <= y + radius; y1++)
                for(int z1 = z - radius; z1 <= z + radius; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    if(this.blockList.contains(id)) {
                        if(rand.nextInt(10000) < 4500) {
                            if(id.getUnlocalizedName().equals(Blocks.grass.getUnlocalizedName())
                                    || id.getUnlocalizedName().equals(Blocks.dirt.getUnlocalizedName()))
                                worldObj.setBlock(x1, y1, z1, Blocks.cobblestone);
                            else
                                worldObj.setBlockToAir(x1, y1, z1);
                        }
                    }
                }
    }

    @Override
    public int cost() {
        return 250;
    }

    @Override
    public String getAbilityName() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }
}
