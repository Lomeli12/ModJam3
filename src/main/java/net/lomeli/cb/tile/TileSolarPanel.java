package net.lomeli.cb.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileSolarPanel extends TileEntity implements IEnergy {

    private int currentCharge, maxCharge = 4000, tick;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (worldObj.isDaytime() && !(worldObj.isThundering() || worldObj.isRaining()) && worldObj.canBlockSeeTheSky(xCoord, yCoord, zCoord)) {
                if (++tick == 10) {
                    int possibleCharge = (int) (worldObj.getTotalWorldTime() % 20L);
                    addCharge(possibleCharge);
                    tick = 0;
                }
            }
            for (int x = xCoord - 1; x < xCoord + 2; x++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    if (x != xCoord || z != zCoord) {
                        TileEntity tile = worldObj.getBlockTileEntity(x, yCoord, z);
                        if (tile != null && tile instanceof IEnergy) {
                            if (!((IEnergy) tile).isGenerator() && (((IEnergy) tile).getCurrentCharge() < ((IEnergy) tile).getChargeCapcity()) && canCompleteTask(1)) {
                                ((IEnergy) tile).addCharge(useCharge(1));
                            }
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
        if (currentCharge > getChargeCapcity())
            currentCharge = getChargeCapcity();
        return getCurrentCharge();
    }

    @Override
    public int useCharge(int charge) {
        if (canCompleteTask(charge)) {
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
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound dataTag = packet != null ? packet.data : new NBTTagCompound();
        writeTag(dataTag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, dataTag);
    }

    @Override
    public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound tag = pkt != null ? pkt.data : new NBTTagCompound();
        readNBT(tag);
    }

}
