package net.lomeli.cb.abilities.dark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class AbilityMagnetism extends CrystalAbility {

    private List<Block> blockList;

    public AbilityMagnetism() {
        this.blockList = new ArrayList<Block>();
        this.blockList.add(Blocks.coal_ore);
        this.blockList.add(Blocks.iron_ore);
        this.blockList.add(Blocks.lapis_ore);
        this.blockList.add(Blocks.redstone_ore);
        this.blockList.add(Blocks.diamond_ore);
        this.blockList.add(Blocks.emerald_ore);
        this.blockList.add(Blocks.gold_ore);
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 40;
        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    if (rand.nextInt(10000) < 15) {
                        int xDis = x1 - x, yDis = y1 - y, zDis = z1 - z;
                        int xMove = xDis < 0 ? 1 : xDis > 0 ? -1 : x1;
                        int yMove = yDis < 0 ? 1 : yDis > 0 ? -1 : y1;
                        int zMove = zDis < 0 ? 1 : zDis > 0 ? -1 : z1;
                        if (this.blockList.contains(id)) {
                            Block id2 = worldObj.getBlock(x1 + xMove, y1 + yMove, z1 + zMove);
                            if (id2.getUnlocalizedName().equals(Blocks.dirt.getUnlocalizedName()) || 
                                    id2.getUnlocalizedName().equals(Blocks.stone.getUnlocalizedName()) || 
                                    id2.getUnlocalizedName().equals(Blocks.sand.getUnlocalizedName()) || 
                                    id2.getUnlocalizedName().equals(Blocks.gravel.getUnlocalizedName())) {
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
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }
}
