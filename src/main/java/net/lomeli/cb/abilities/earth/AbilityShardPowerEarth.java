package net.lomeli.cb.abilities.earth;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.tile.TileCrystal;

public class AbilityShardPowerEarth extends CrystalAbility{
    private int tick;
    
    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof TileCrystal) {
                if(worldObj.getBlockId(x, y - 1, z) == ModBlocks.shardBlock.blockID && worldObj.getBlockMetadata(x, y - 1, z) == 2){
                    if(++tick == 20){
                        ((TileCrystal)tile).addPower(20);
                        tick = 0;
                        if(rand.nextInt(10000) < 15)
                            worldObj.setBlockToAir(x, y - 1, z);
                    }
                }
            }
        }
    }
}
