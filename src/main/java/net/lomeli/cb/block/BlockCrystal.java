package net.lomeli.cb.block;

import java.util.Random;

import net.lomeli.cb.tile.TileCrystal;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystal extends BlockCB implements ITileEntityProvider {

    public BlockCrystal() {
        super(Material.glass, "blank");
        this.setHardness(7.0F);
        this.setResistance(50.0F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
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
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int par5, float par6, float par7, float par8, int par9) {
        Random rand = new Random();
        TileCrystal tile = (TileCrystal) world.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile.red == 0)
                tile.red = rand.nextFloat();
            if (tile.blue == 0)
                tile.blue = rand.nextFloat();
            if (tile.green == 0)
                tile.green = rand.nextFloat();
        }
        return super.onBlockPlaced(world, x, y, z, par5, par6, par7, par8, par9);
    }
}
