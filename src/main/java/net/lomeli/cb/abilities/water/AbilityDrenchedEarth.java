package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityDrenchedEarth extends CrystalAbility {

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 10;

        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    TileEntity tile = worldObj.getBlockTileEntity(x1, y1, z1);
                    if(tile instanceof TileEntityFurnace) {
                        try {
                            int burnTime = TileEntityFurnace.class.getDeclaredField("furnaceBurnTime").getInt(tile);
                            if(burnTime > 0 && id == 62) {
                                int meta = worldObj.getBlockMetadata(x1, y1, z1);
                                TileEntityFurnace.class.getDeclaredField("furnaceBurnTime").set(tile, 0);
                                worldObj.setBlock(x1, y1, z1, 61, meta, 2);
                            }
                        }catch(Exception e) {

                        }
                    }else if(id != Block.glowStone.blockID) {
                        if(Block.lightValue[id] > 0.5F) {
                            EntityItem drop = new EntityItem(worldObj, x1, y1, z1, new ItemStack(id, 1,
                                    worldObj.getBlockMetadata(x1, y1, z1)));
                            if(drop != null)
                                worldObj.spawnEntityInWorld(drop);
                            worldObj.setBlockToAir(x1, y1, z1);
                        }
                    }
                }
    }
}
