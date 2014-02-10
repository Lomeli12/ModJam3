package net.lomeli.cb.abilities.fire;

import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class AbilityBainOfIce extends CrystalAbility {

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 20;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    if (id.getUnlocalizedName().equals(Blocks.snow.getUnlocalizedName()) || id.getUnlocalizedName().equals(Blocks.snow_layer.getUnlocalizedName()))
                        worldObj.setBlockToAir(x1, y1, z1);
                    else if (id.getUnlocalizedName().equals(Blocks.ice.getUnlocalizedName()))
                        worldObj.setBlock(x1, y1, z1, Blocks.flowing_water);
                    worldObj.markBlockForUpdate(x1, y1, z1);
                }
    }

    @Override
    public int cost() {
        return 50;
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
