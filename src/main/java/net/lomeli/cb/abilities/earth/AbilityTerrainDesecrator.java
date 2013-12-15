package net.lomeli.cb.abilities.earth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

public class AbilityTerrainDesecrator extends CrystalAbility {

    private List<Integer> blockList;

    public AbilityTerrainDesecrator() {
        this.blockList = new ArrayList<Integer>();

        for(ItemStack saplings : OreDictionary.getOres("treeSapling")) {
            if(saplings != null)
                this.blockList.add(saplings.itemID);
        }
        for(ItemStack log : OreDictionary.getOres("logWood")) {
            if(log != null)
                this.blockList.add(log.itemID);
        }
        for(ItemStack leaf : OreDictionary.getOres("treeLeaves")) {
            if(leaf != null)
                this.blockList.add(leaf.itemID);
        }
        this.blockList.add(Block.grass.blockID);
        this.blockList.add(Block.tallGrass.blockID);
        this.blockList.add(Block.plantRed.blockID);
        this.blockList.add(Block.plantYellow.blockID);
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
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if(this.blockList.contains(id)) {
                        if(rand.nextInt(10000) < 4500) {
                            if(id == Block.grass.blockID || id == Block.dirt.blockID)
                                worldObj.setBlock(x1, y1, z1, Block.cobblestone.blockID);
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
