package net.lomeli.cb.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.tile.TileCrystal;

public class BlockCrystal extends BlockCB implements ITileEntityProvider {

    public BlockCrystal(int par1) {
        super(par1, Material.glass, "crystal");
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystal();
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

}
