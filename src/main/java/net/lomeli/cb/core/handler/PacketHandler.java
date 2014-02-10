package net.lomeli.cb.core.handler;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import net.lomeli.cb.lib.PageInfo;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.network.IPacketCB;
import net.lomeli.cb.network.PacketCustom;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.Packet;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;

@ChannelHandler.Sharable
public class PacketHandler extends MessageToMessageCodec<FMLProxyPacket, IPacketCB> {
    private static final byte tileCrystalPacket = 12, playerDiscoveryPacket = 13;
    private boolean isInit = false;
    public EnumMap<Side, FMLEmbeddedChannel> channels;
    private LinkedList<Class<? extends IPacketCB>> packets = new LinkedList<Class<? extends IPacketCB>>();

    public PacketHandler() {

    }

    public void init() {
        channels = NetworkRegistry.INSTANCE.newChannel(Strings.PACKETS, this);
    }

    @Override
    public void decode(ChannelHandlerContext chc, FMLProxyPacket packet, List<Object> out) throws Exception {
        ByteBuf payload = packet.payload();
        byte discriminator = payload.readByte();
        Class<? extends IPacketCB> clazz = packets.get(discriminator);
        if(clazz == null) {
            throw new NullPointerException("No packet registered for discriminator: " + discriminator);
        }
        IPacketCB pkt = clazz.newInstance();
        pkt.readData(payload);
        EntityPlayer player;

        try {
            System.out.println(pkt.toString());
            INetHandler netHandler = chc.channel().attr(NetworkRegistry.NET_HANDLER).get();
            player = ((NetHandlerPlayServer) netHandler).playerEntity;
            switch(pkt.getID()) {
            case tileCrystalPacket:
                recieveTileCrystalFactoryPacket(((PacketCustom) pkt).getData());
                break;
            case playerDiscoveryPacket:
                System.out.println("Stuff");
                recievePlayerDiscoveryPacket(((PacketCustom) pkt).getData(), player);
                break;
            default:
                FMLLog.warning("Could not identify packet!", pkt);
                break;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }

        out.add(pkt);
    }

    @Override
    public void encode(ChannelHandlerContext chc, IPacketCB packet, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();
        Class<? extends IPacketCB> clazz = packet.getClass();
        if(!packets.contains(packet.getClass())) {
            throw new NullPointerException("No Packet registered for: " + packet.getClass().getCanonicalName());
        }
        byte discriminator = (byte) packets.indexOf(clazz);
        buffer.writeByte(discriminator);
        packet.writeData(buffer);
        FMLProxyPacket proxyPacket = new FMLProxyPacket(buffer.copy(), chc.channel().attr(NetworkRegistry.FML_CHANNEL).get());
        out.add(proxyPacket);
    }

    public boolean registerPacket(Class<? extends IPacketCB> clazz) {
        if(packets.size() > 256) {
            FMLLog.warning("Packet queue full, ignoring " + clazz.toString(), clazz);
            return false;
        }

        if(packets.contains(clazz)) {
            FMLLog.warning("Packet " + clazz.toString() + " already registered!", clazz);
            return false;
        }

        if(isInit) {
            return false;
        }

        packets.add(clazz);
        return true;
    }

    public void postInit() {
        if(isInit)
            return;

        registerPacket(PacketCustom.class);

        isInit = true;

        Collections.sort(packets, new Comparator<Class<? extends IPacketCB>>() {
            @Override
            public int compare(Class<? extends IPacketCB> clazz1, Class<? extends IPacketCB> clazz2) {
                int com = String.CASE_INSENSITIVE_ORDER.compare(clazz1.getCanonicalName(), clazz2.getCanonicalName());
                if(com == 0) {
                    com = clazz1.getCanonicalName().compareTo(clazz2.getCanonicalName());
                }
                return com;
            }
        });

    }

    public void recieveTileCrystalFactoryPacket(ByteBuf data) {
        int x = 0, y = 0, z = 0, group = 0;
        boolean bool = false;
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        group = data.readInt();
        bool = data.readBoolean();
        TileCrystalFactory tile = (TileCrystalFactory) MinecraftServer.getServer().getEntityWorld().getTileEntity(x, y, z);
        if(tile != null) {
            if(bool)
                tile.startFormationProcess();
            else {
                if(group < 3)
                    tile.process(group);
            }
        }
    }

    public void sendTileCrystalFactoryPacket(TileCrystalFactory tile, int group, boolean finalStep) {
        try {
            ByteBuf buffer = Unpooled.buffer();
            
            buffer.writeInt(tile.xCoord);
            buffer.writeInt(tile.yCoord);
            buffer.writeInt(tile.zCoord);
            buffer.writeInt(group);
            buffer.writeBoolean(finalStep);

            PacketCustom pack = new PacketCustom(tileCrystalPacket);
            pack.writeData(buffer);

            sendPacketToServer(pack);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void recievePlayerDiscoveryPacket(ByteBuf data, EntityPlayer player) {
        int keySize = data.readInt();
        System.out.println(keySize);
        StringBuilder sBuilder = new StringBuilder();
        for(int i = 0; i < keySize; i++) {
            sBuilder.append(data.readChar());
        }
        String key = sBuilder.toString();
        if(key != null && key.length() > 0) {
            System.out.println(key);
            if(key.startsWith(PageInfo.baseTag)) {
                if(player != null && player.getEntityId() == data.readInt())
                    player.getEntityData().setBoolean(key, data.readBoolean());
            }
        }
    }

    public void sendPlayerDiscoveryPacket(String key, EntityPlayerMP player, boolean value) {
        try {
            ByteBuf buffer = Unpooled.buffer();
            
            buffer.writeInt(key.length());
            for(int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);
                buffer.writeChar(keyChar);
            }
            buffer.writeInt(player.getEntityId());
            buffer.writeBoolean(value);

            PacketCustom pack = new PacketCustom(playerDiscoveryPacket);

            pack.writeData(buffer);

            sendPacketToServer(pack);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void sendPacketToServer(IPacketCB packet) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeOutbound(packet);
    }

    public void sendPacketToPlayer(IPacketCB packet, EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public void sendPacketToAll(IPacketCB packet) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeOutbound(packet);
    }

    public void sendPacketAllAround(IPacketCB packet, NetworkRegistry.TargetPoint point) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET)
                .set(FMLOutboundHandler.OutboundTarget.ALLAROUNDPOINT);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(point);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public void sendPacketToDimension(IPacketCB packet, int dimensionId) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.DIMENSION);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(dimensionId);
        channels.get(Side.SERVER).writeAndFlush(packet);
    }

    public boolean doesPlayerHaveTag(EntityPlayer player, String tag) {
        return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(player.getDisplayName()).getEntityData()
                .hasKey(tag)
                && MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(player.getDisplayName()).getEntityData()
                        .getBoolean(tag);
    }
}
