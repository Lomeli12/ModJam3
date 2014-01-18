package net.lomeli.cb.core.handler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
    private static final byte tileCrystalPacket = 12;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
        byte dataId = data.readByte();

        switch (dataId) {
        case tileCrystalPacket:
            recieveTileCrystalFactoryPacket(data);
            break;
        }
    }

    public void recieveTileCrystalFactoryPacket(ByteArrayDataInput data) {
        int x = 0, y = 0, z = 0, group = 0;
        boolean bool = false;
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        group = data.readInt();
        bool = data.readBoolean();
        TileCrystalFactory tile = (TileCrystalFactory) MinecraftServer.getServer().getEntityWorld().getBlockTileEntity(x, y, z);
        if (tile != null) {
            if (bool)
                tile.startFormationProcess();
            else {
                if (group < 3)
                    tile.process(group);
            }
        }
    }

    public static void sendTileCrystalFactoryPacket(TileCrystalFactory tile, int group, boolean finalStep) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.writeByte(tileCrystalPacket);
            writeCoord(dos, tile.xCoord, tile.yCoord, tile.zCoord);
            dos.writeInt(group);
            dos.writeBoolean(finalStep);
            dos.close();
            packet.channel = Strings.PACKETS;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        } catch (Exception e) {
        }
    }

    private static void writeCoord(DataOutputStream stream, int x, int y, int z) {
        try {
            stream.writeInt(x);
            stream.writeInt(y);
            stream.writeInt(z);
        } catch (Exception e) {
        }
    }

}
