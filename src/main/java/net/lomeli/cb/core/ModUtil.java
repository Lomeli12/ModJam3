package net.lomeli.cb.core;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

public class ModUtil {
    public static ForgeDirection getDirectionFromTile(TileEntity baseTile, TileEntity targetTile) {
        int x = (targetTile.xCoord - baseTile.xCoord), z = (targetTile.zCoord - baseTile.zCoord);
        if (x != 0) {
            if (x > 0)
                return ForgeDirection.EAST;
            else if (x < 0)
                return ForgeDirection.WEST;
        } else if (z != 0) {
            if (z > 0)
                return ForgeDirection.NORTH;
            else if (z < 0)
                return ForgeDirection.SOUTH;
        }
        return ForgeDirection.UNKNOWN;
    }

    public static TileEntity[] getSurroundingTiles(World world, int x, int y, int z) {
        TileEntity[] tiles = new TileEntity[4];
        tiles[0] = world.getTileEntity(x + 1, y, z);
        tiles[1] = world.getTileEntity(x - 1, y, z);
        tiles[2] = world.getTileEntity(x, y, z + 1);
        tiles[3] = world.getTileEntity(x, y, z - 1);
        return tiles;
    }
    
    public static boolean areBlocksTheSame(Block baseBlock, Block targetBlock) {
        return baseBlock.getUnlocalizedName().equals(targetBlock.getUnlocalizedName());
    }
}
