package net.lomeli.cb.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.EnergyStorage;

public class TileChargeBox extends TileEntity implements IEnergy, IEnergyHandler {
    protected EnergyStorage energyStorage;
    private int type;
    
    public TileChargeBox() {
        init(0);
    }
    
    public TileChargeBox(int meta) {
        init(meta);
    }

    public void init(int meta) {
        type = meta;
        int maxCharge = 50;
        switch (meta) {
        case 0:
            maxCharge = 6000;
            break;
        case 1:
            maxCharge = 12000;
            break;
        default:
            maxCharge = 50;
            break;
        }
        energyStorage = new EnergyStorage(maxCharge);
    }
    
    public void setType(int meta) {
        type = meta;
        int maxCharge = 50;
        switch (meta) {
        case 0:
            maxCharge = 6000;
            break;
        case 1:
            maxCharge = 12000;
            break;
        default:
            maxCharge = 50;
            break;
        }
        energyStorage = new EnergyStorage(maxCharge);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            if (meta == 2)
                energyStorage.setEnergyStored(energyStorage.getMaxEnergyStored());

            for (int x = xCoord - 1; x < xCoord + 2; x++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    if (x != xCoord || z != zCoord) {
                        TileEntity tile = worldObj.getTileEntity(x, yCoord, z);
                        if (tile != null) {
                            if (tile instanceof ICrystal) {
                                if (((ICrystal) tile).getPower() < ((ICrystal) tile).getMaxPower()) {
                                    if (canCompleteTask(20))
                                        ((ICrystal) tile).addPower(useCharge(20));
                                }
                            } else if (tile instanceof IEnergy) {
                                if (!((IEnergy) tile).isGenerator() && !((IEnergy) tile).isChargeBox()
                                        && (((IEnergy) tile).getCurrentCharge() < ((IEnergy) tile).getChargeCapcity())) {
                                    if (canCompleteTask(1))
                                        ((IEnergy) tile).addCharge(useCharge(1));
                                }
                            }
                        }
                    }
                }
        }
    }

    @Override
    public int getCurrentCharge() {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getChargeCapcity() {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public int addCharge(int charge) {
        return energyStorage.receiveEnergy(charge, false);
    }

    @Override
    public int useCharge(int charge) {
        return energyStorage.extractEnergy(charge, false);
    }

    @Override
    public boolean canCompleteTask(int charge) {
        return energyStorage.getEnergyStored() >= charge;
    }

    @Override
    public boolean isChargeBox() {
        return true;
    }

    @Override
    public boolean isGenerator() {
        return false;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        energyStorage.writeToNBT(tag);
        tag.setInteger("Metadata", type);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        type = tag.getInteger("Metadata");
        setType(type);
        energyStorage.readFromNBT(tag);
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

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public boolean canInterface(ForgeDirection from) {
        return true;
    }

    @Override
    public int getEnergyStored(ForgeDirection from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return energyStorage.getMaxEnergyStored();
    }
}
