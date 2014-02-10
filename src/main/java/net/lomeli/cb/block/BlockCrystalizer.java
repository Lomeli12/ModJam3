package net.lomeli.cb.block;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalizer;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystalizer extends BlockCB implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockCrystalizer() {
        super(Material.iron, "crystalizer_");
        this.setBlockName("crystalizer");
        this.setHardness(2.0F);
        this.setResistance(10.0F);
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[4];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return side == 0 ? iconArray[3] : side == 1 ? iconArray[1] : side == meta ? iconArray[0] : iconArray[2];
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileCrystalizer();
    }

    @Override
    public void onBlockAdded(World par1World, int par2, int par3, int par4) {
        super.onBlockAdded(par1World, par2, par3, par4);
        this.setDefaultDirection(par1World, par2, par3, par4);
    }

    @Override
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
        int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if (l == 0)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);

        if (l == 1)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

        if (l == 2)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);

        if (l == 3)
            par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
    }

    private void setDefaultDirection(World par1World, int par2, int par3, int par4) {
        if (!par1World.isRemote) {
            Block l = par1World.getBlock(par2, par3, par4 - 1);
            Block i1 = par1World.getBlock(par2, par3, par4 + 1);
            Block j1 = par1World.getBlock(par2 - 1, par3, par4);
            Block k1 = par1World.getBlock(par2 + 1, par3, par4);
            byte b0 = 3;

            if (l.isOpaqueCube() && !i1.isOpaqueCube())
                b0 = 3;

            if (i1.isOpaqueCube() && !l.isOpaqueCube())
                b0 = 2;

            if (j1.isOpaqueCube() && !k1.isOpaqueCube())
                b0 = 5;

            if (k1.isOpaqueCube() && !j1.isOpaqueCube())
                b0 = 4;

            par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
        }
    }

}
