package net.lomeli.cb.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileSolarPanel extends TileEntity implements IEnergy {

    private int currentCharge, maxCharge = 4000, tick;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote) {
            if(worldObj.isDaytime() && !(worldObj.isThundering() || worldObj.isRaining())
                    && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
                if(++tick >= 15) {
                    int possibleCharge = (int) (worldObj.getTotalWorldTime() % 20L);
                    addCharge(possibleCharge);
                    tick = 0;
                }
            }
            TileEntity[] tiles = new TileEntity[6];
            tiles[0] = worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);
            tiles[1] = worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);
            tiles[2] = worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);
            tiles[3] = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            tiles[4] = worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);
            tiles[5] = worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);

            for(TileEntity tile : tiles) {
                if(tile != null && tile instanceof IEnergy) {
                    if(!((IEnergy) tile).isGenerator()
                            && (((IEnergy) tile).getCurrentCharge() < ((IEnergy) tile).getChargeCapcity()) && canCompleteTask(1)) {
                        ((IEnergy) tile).addCharge(useCharge(1));
                    }
                }
            }
        }
    }

    @Override
    public int getCurrentCharge() {
        return currentCharge;
    }

    @Override
    public int getChargeCapcity() {
        return maxCharge;
    }

    @Override
    public int addCharge(int charge) {
        currentCharge += charge;
        if(currentCharge > getChargeCapcity())
            currentCharge = getChargeCapcity();
        return getCurrentCharge();
    }

    @Override
    public int useCharge(int charge) {
        if(canCompleteTask(charge)) {
            currentCharge -= charge;
            return charge;
        }
        return 0;
    }

    @Override
    public boolean canCompleteTask(int charge) {
        return currentCharge >= charge;
    }

    @Override
    public boolean isChargeBox() {
        return true;
    }

    @Override
    public boolean isGenerator() {
        return true;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        tag.setInteger("CrystalCharge", currentCharge);
        tag.setInteger("tick", tick);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        currentCharge = tag.getInteger("CrystalCharge");
        tick = tag.getInteger("tick");
    }

    @Override
    public Packet getDescriptionPacket() {
        S35PacketUpdateTileEntity packet = (S35PacketUpdateTileEntity) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.func_148857_g() : new NBTTagCompound();
        writeTag(dataTag);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.func_148857_g() : new NBTTagCompound();
        readNBT(tag);
    }

}
