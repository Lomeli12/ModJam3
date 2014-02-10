package net.lomeli.cb.network;

import io.netty.buffer.ByteBuf;

public interface IPacketCB {
    public abstract byte getID();

    public abstract void readData(ByteBuf data);

    public abstract void writeData(ByteBuf data);
}
