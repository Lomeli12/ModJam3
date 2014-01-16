package net.lomeli.cb.block;

import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCrystalFactory extends BlockCB implements ITileEntityProvider {

    public BlockCrystalFactory(int par1) {
        super(par1, Material.rock, "crystalFactory_");
        setUnlocalizedName("factory");
        setHardness(2.0F);
        setResistance(10.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                TileCrystalFactory tile = (TileCrystalFactory) world.getBlockTileEntity(x, y, z);
                if (tile != null) {
                    if (tile.hasMaster()) {
                        if (tile.isMaster()) {
                            player.addChatMessage("I am the master!");
                        } else {
                            player.addChatMessage("I'm a servant block! My master is at (" + tile.getMasterX() + ", " + tile.getMasterY() + ", " + tile.getMasterZ() + ")");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystalFactory();
    }

}
