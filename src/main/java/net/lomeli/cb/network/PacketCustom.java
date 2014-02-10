package net.lomeli.cb.network;

import io.netty.buffer.ByteBuf;

public class PacketCustom implements IPacketCB {
    private byte id;

    private ByteBuf customData;

    public PacketCustom() {
    }
    
    public PacketCustom(byte id) {
        this.id = id;
    }

    @Override
    public byte getID() {
        return id;
    }

    @Override
    public void readData(ByteBuf data) {
        id = data.readByte();
        customData = data.readBytes(data);
    }

    @Override
    public void writeData(ByteBuf data) {
        data.writeByte(id);
        data.writeBytes(data);
    }

    public ByteBuf getData() {
        return customData;
    }

}
