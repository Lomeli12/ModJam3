package net.lomeli.cb.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalSmelter;

public class BlockSmelter extends BlockCB implements ITileEntityProvider{
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockSmelter(int par1) {
        super(par1, Material.iron, "crystalsmelter_");
        this.setUnlocalizedName("smelter");
    }
    
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[3];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ?  iconArray[2] : meta == 1 ? iconArray[0] : iconArray[1];
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
