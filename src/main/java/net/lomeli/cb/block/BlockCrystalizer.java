package net.lomeli.cb.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import net.lomeli.cb.tile.TileCrystalizer;

public class BlockCrystalizer extends BlockCB implements ITileEntityProvider {

    public BlockCrystalizer(int par1) {
        super(par1, Material.iron, "crystalizer_");
        this.setUnlocalizedName("crystalizer");
    }
    
    @Override
    public boolean hasTileEntity() {
        return true;
    }
    
    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystalizer();
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase,
            ItemStack par6ItemStack) {
        int l = MathHelper.floor_double((double) (par5EntityLivingBase.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if(l == 0)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);

        if(l == 1)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

        if(l == 2)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);

        if(l == 3)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);

        if(par6ItemStack.hasDisplayName()) {
            ((TileEntityFurnace) par1World.getBlockTileEntity(par2, par3, par4))
                    .setGuiDisplayName(par6ItemStack.getDisplayName());
        }
    }

    private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
        if(!par1World.isRemote) {
            int l = par1World.getBlockId(par2, par3, par4 - 1);
            int i1 = par1World.getBlockId(par2, par3, par4 + 1);
            int j1 = par1World.getBlockId(par2 - 1, par3, par4);
            int k1 = par1World.getBlockId(par2 + 1, par3, par4);
            byte b0 = 3;

            if(Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
                b0 = 3;

            if(Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
                b0 = 2;

            if(Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
                b0 = 5;

            if(Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
                b0 = 4;

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

}
