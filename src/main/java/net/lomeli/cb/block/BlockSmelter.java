package net.lomeli.cb.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.tile.TileCrystalSmelter;

public class BlockSmelter extends BlockCB implements ITileEntityProvider{

    public BlockSmelter(int par1) {
        super(par1, Material.iron, "crystalSmelter");
        this.setUnlocalizedName("smelter");
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int j, float f, float k, float b){
        if(!world.isRemote && !player.isSneaking()){
            TileCrystalSmelter tile = (TileCrystalSmelter)world.getBlockTileEntity(x, y, z);
            if(tile != null){
                ItemStack stack =  player.getCurrentEquippedItem();
                if(stack != null && tile.isItemValidForSlot(0, stack)){
                    tile.addItemToSlot(player.getCurrentEquippedItem());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystalSmelter();
    }

}
