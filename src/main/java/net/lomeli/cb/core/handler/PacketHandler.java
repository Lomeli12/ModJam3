package net.lomeli.cb.core.handler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.lomeli.cb.lib.PageInfo;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketHandler implements IPacketHandler {
    private static final byte tileCrystalPacket = 12, playerDiscoveryPacket = 13;

    @Override
    public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput data = ByteStreams.newDataInput(packet.data);
        byte dataId = data.readByte();

        switch (dataId) {
        case tileCrystalPacket:
            recieveTileCrystalFactoryPacket(data);
            break;
        case playerDiscoveryPacket:
            recievePlayerDiscoveryPacket(data, player);
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

    public void recievePlayerDiscoveryPacket(ByteArrayDataInput data, Player player) {
        short keySize = data.readShort();
        StringBuilder sBuilder = new StringBuilder();
        for (int i = 0; i < keySize; i++) {
            sBuilder.append(data.readChar());
        }
        String key = sBuilder.toString();
        if (key != null && key.length() > 0) {
            if (key.startsWith(PageInfo.baseTag)) {
                if (player instanceof EntityPlayer) {
                    if (((EntityPlayer) player).entityId == data.readInt())
                        ((EntityPlayer) player).getEntityData().setBoolean(key, true);
                }
            }
        }
    }

    public static void sendPlayerDiscoveryPacket(String key, EntityPlayerMP player) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            Packet250CustomPayload packet = new Packet250CustomPayload();
            dos.write(playerDiscoveryPacket);
            dos.writeShort(key.length());
            dos.writeChars(key);
            dos.writeInt(player.entityId);
            dos.close();
            packet.channel = Strings.PACKETS;
            packet.data = baos.toByteArray();
            packet.length = baos.size();
            packet.isChunkDataPacket = false;
            PacketDispatcher.sendPacketToServer(packet);
        } catch (Exception e) {
        }
    }

    @SideOnly(Side.CLIENT)
    public static boolean doesPlayerHaveTag(EntityPlayer player, String tag) {
        return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(player.username).getEntityData().getBoolean(tag);
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
