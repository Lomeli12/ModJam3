package net.lomeli.cb.network;

import net.lomeli.cb.tile.TileCrystalFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketTileUpdate extends AbstractPacket {
    private int x, y, z, buttonGroup;
    private boolean activate;

    public PacketTileUpdate() {
    }

    public PacketTileUpdate(int x, int y, int z, int buttonGroup, boolean activate) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.buttonGroup = buttonGroup;
        this.activate = activate;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
        buffer.writeInt(buttonGroup);
        buffer.writeBoolean(activate);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        buttonGroup = buffer.readInt();
        activate = buffer.readBoolean();
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        activateTile(player);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        activateTile(player);
    }

    public void activateTile(EntityPlayer player) {
        World world = player.worldObj;
        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile != null && (tile instanceof TileCrystalFactory)) {
            if(activate)
                ((TileCrystalFactory) tile).startFormationProcess();
            else {
                if(buttonGroup < 3)
                    ((TileCrystalFactory) tile).process(buttonGroup);
            }
        }
    }

}
