package net.lomeli.cb.block;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileSolarPanel;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSolarPanel extends BlockCB implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockSolarPanel() {
        super(Material.glass, "solarPanel_");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        setBlockName("solarpanel");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileSolarPanel();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 0 ? iconArray[0] : side == 1 ? iconArray[1] : iconArray[2];
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[3];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

}
