package net.lomeli.cb.abilities.dark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

public class AbilityMagnetism extends CrystalAbility {

    private List<Integer> blockList;

    public AbilityMagnetism() {
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
        int radius = 40;
        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if(rand.nextInt(10000) < 15) {
                        int xDis = x1 - x, yDis = y1 - y, zDis = z1 - z;
                        int xMove = xDis < 0 ? 1 : xDis > 0 ? -1 : x1;
                        int yMove = yDis < 0 ? 1 : yDis > 0 ? -1 : y1;
                        int zMove = zDis < 0 ? 1 : zDis > 0 ? -1 : z1;
                        if(this.blockList.contains(id)) {
                            int id2 = worldObj.getBlockId(x1 + xMove, y1 + yMove, z1 + zMove);
                            if(id2 == Block.dirt.blockID || id2 == Block.stone.blockID || id2 == Block.sand.blockID
                                    || id2 == Block.gravel.blockID) {
                                worldObj.setBlock(x1, y1, z1, id2);
                                worldObj.setBlock(x1 + xMove, y1 + yMove, z1 + zMove, id);
                            }
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
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }
}
