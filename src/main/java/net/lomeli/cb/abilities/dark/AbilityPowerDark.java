package net.lomeli.cb.abilities.dark;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.tile.TileCrystal;

public class AbilityPowerDark extends CrystalAbility{
    private int tick;
    
    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof TileCrystal) {
                if(worldObj.getBlockId(x, y - 1, z) == Block.obsidian.blockID){
                    if(++tick == 20){
                        ((TileCrystal)tile).addPower(40);
                        tick = 0;
                        if(rand.nextInt(10000000) < 15)
                            worldObj.setBlockToAir(x, y - 1, z);
                    }
                }
            }
        }
    }
}
