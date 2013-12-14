package net.lomeli.cb.abilities.dark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityOreater extends CrystalAbility {

    private List<Integer> blockList;

    public AbilityOreater() {
        this.blockList = new ArrayList<Integer>();
        this.blockList.add(Block.oreCoal.blockID);
        this.blockList.add(Block.oreDiamond.blockID);
        this.blockList.add(Block.oreEmerald.blockID);
        this.blockList.add(Block.oreGold.blockID);
        this.blockList.add(Block.oreIron.blockID);
        this.blockList.add(Block.oreLapis.blockID);
        this.blockList.add(Block.oreRedstone.blockID);
        this.blockList.add(Block.oreRedstoneGlowing.blockID);
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if(rand.nextInt(10000) < 4500) {
                        if(this.blockList.contains(id))
                            worldObj.setBlock(x1, y1, z1, Block.stone.blockID);
                    }
                }
    }
}
