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
        this.setHardness(7.0F);
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
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9) {
        Random rand = new Random();
        TileCrystal tile = (TileCrystal) world.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile.red == 0)
                tile.red = rand.nextInt(200);
            if(tile.blue == 0)
                tile.blue = rand.nextInt(200);
            if(tile.green == 0)
                tile.green = rand.nextInt(200);
        }
        return super.onBlockPlaced(world, x, y, z, par5, par6, par7, par8, par9);
    }
}
