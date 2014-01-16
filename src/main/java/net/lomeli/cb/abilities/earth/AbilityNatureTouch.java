package net.lomeli.cb.abilities.earth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.world.World;

public class AbilityNatureTouch extends CrystalAbility {

    private List<Integer> blockList;

    public AbilityNatureTouch() {
        this.blockList = new ArrayList<Integer>();
        this.blockList.add(Block.sapling.blockID);
        this.blockList.add(Block.tallGrass.blockID);
        this.blockList.add(Block.plantRed.blockID);
        this.blockList.add(Block.plantYellow.blockID);
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if (id == 0) {
                        if (Block.plantRed.canPlaceBlockAt(worldObj, x1, y1, z1)) {
                            if (rand.nextInt(10000) < 1) {
                                int i = rand.nextInt(this.blockList.size());
                                if (i < this.blockList.size())
                                    worldObj.setBlock(x1, y1, z1, this.blockList.get(i));
                            }
                        }
                    }
                }
    }

    @Override
    public int cost() {
        return 500;
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
