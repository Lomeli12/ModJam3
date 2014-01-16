package net.lomeli.cb.block;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileSolarPanel;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSolarPanel extends BlockCB implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockSolarPanel(int par1) {
        super(par1, Material.glass, "solarPanel_");
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.375F, 1.0F);
        setUnlocalizedName("solarpanel");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
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
    public Icon getIcon(int side, int meta) {
        return side == 0 ? iconArray[0] : side == 1 ? iconArray[1] : iconArray[2];
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[3];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

}
