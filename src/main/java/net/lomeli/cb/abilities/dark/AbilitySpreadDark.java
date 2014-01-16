package net.lomeli.cb.abilities.dark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

public class AbilitySpreadDark extends CrystalAbility {

    private List<Integer> blockList;

    public AbilitySpreadDark() {
        this.blockList = new ArrayList<Integer>();
        for (ItemStack cobblestone : OreDictionary.getOres("cobblestone")) {
            if (cobblestone != null)
                this.blockList.add(cobblestone.itemID);
        }
        for (ItemStack stone : OreDictionary.getOres("stone")) {
            if (stone != null)
                this.blockList.add(stone.itemID);
        }
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 9;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if (this.blockList.contains(id)) {
                        if (rand.nextInt(10000) < 4500) {
                            if (OreDictionary.getOres("stone").contains(new ItemStack(Block.blocksList[id])))
                                worldObj.setBlock(x1, y1, z1, ModBlocks.darkStone.blockID);
                            else
                                worldObj.setBlock(x1, y1, z1, ModBlocks.darkCobble.blockID);
                        }
                    }
                }
    }

    @Override
    public int cost() {
        return 300;
    }

    @Override
    public String getAbilityName() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }

}
