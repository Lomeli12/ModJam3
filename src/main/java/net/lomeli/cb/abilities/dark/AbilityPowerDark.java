package net.lomeli.cb.abilities.dark;

import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AbilityPowerDark extends CrystalAbility {

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileCrystal) {
                if (worldObj.getBlock(x, y - 1, z).getUnlocalizedName().equals(Blocks.obsidian.getUnlocalizedName())) {
                    ((TileCrystal) tile).addPower(40);
                    if (rand.nextInt(10000000) < 15)
                        worldObj.setBlockToAir(x, y - 1, z);
                }
            }
        }
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
