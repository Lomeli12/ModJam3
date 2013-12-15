package net.lomeli.cb.block;

import java.util.Random;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.tile.TileCrystal;

public class BlockCrystal extends BlockCB implements ITileEntityProvider {

    public BlockCrystal(int par1) {
        super(par1, Material.glass, "blank");
        this.setHardness(10.0F);
        this.setResistance(50.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystal();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderBlockPass() {
        return 0;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3){
        return 0;
    }
}
