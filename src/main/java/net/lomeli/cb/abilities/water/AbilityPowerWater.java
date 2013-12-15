package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

public class AbilityPowerWater extends CrystalAbility {
    private int tick;

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof TileCrystal) {
                if(worldObj.getBlockId(x, y - 1, z) == Block.ice.blockID) {
                    if(++tick == 20) {
                        ((TileCrystal) tile).addPower(20);
                        tick = 0;
                        if(rand.nextInt(10000000) < 15)
                            worldObj.setBlockToAir(x, y - 1, z);
                    }
                }
            }
        }
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
