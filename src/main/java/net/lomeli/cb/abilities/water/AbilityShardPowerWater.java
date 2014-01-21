package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class AbilityShardPowerWater extends CrystalAbility {

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof TileCrystal) {
                if (worldObj.getBlockId(x, y - 1, z) == ModBlocks.shardBlock.blockID && worldObj.getBlockMetadata(x, y - 1, z) == 0) {
                    ((TileCrystal) tile).addPower(20);
                    if (rand.nextInt(10000) < 15)
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
