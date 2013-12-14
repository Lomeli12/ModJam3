package net.lomeli.cb.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.lomeli.cb.abilities.CrystalAbility;

public class TileCrystal extends TileEntity implements ICrystal {

    private int energy;
    private boolean active, natural;
    private CrystalAbility[] abilities;

    public TileCrystal() {
        abilities = new CrystalAbility[3];
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote) {
            if(abilityOne() != null && abilityTwo() != null && powerAbility() != null) {
                if(getPower() < getMaxPower())
                    powerAbility().enviromentalEffect(worldObj, xCoord, yCoord, zCoord, worldObj.rand);

                this.usePower(abilityOne().cost());
                if(this.canActivate()) {
                    abilityOne().enviromentalEffect(worldObj, xCoord, yCoord, zCoord, worldObj.rand);
                    this.active = false;
                }

                this.usePower(abilityTwo().cost());
                if(this.canActivate()) {
                    abilityTwo().enviromentalEffect(worldObj, xCoord, yCoord, zCoord, worldObj.rand);
                    this.active = false;
                }
            }
        }
    }

    @Override
    public CrystalAbility abilityOne() {
        return abilities[0];
    }

    @Override
    public CrystalAbility abilityTwo() {
        return abilities[1];
    }

    @Override
    public CrystalAbility powerAbility() {
        return abilities[2];
    }

    @Override
    public boolean canActivate() {
        return active;
    }

    @Override
    public int getPower() {
        return this.energy;
    }

    @Override
    public int getMaxPower() {
        return 16000;
    }

    @Override
    public void addPower(int power) {
        this.energy += power;
        if(this.energy > this.getMaxPower())
            this.energy = this.getMaxPower();
    }

    @Override
    public void usePower(int power) {
        if(this.energy >= power) {
            active = true;
            this.energy -= power;
        }else
            active = false;
    }

    @Override
    public void setAbilityOne(CrystalAbility ability) {
        abilities[0] = ability;
    }

    @Override
    public void setAbilityTwo(CrystalAbility ability) {
        abilities[1] = ability;
    }

    @Override
    public void setPowerAbility(CrystalAbility ability) {
        abilities[2] = ability;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        tag.setInteger("power", this.energy);
        tag.setBoolean("active", this.active);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        this.energy = tag.getInteger("power");
        this.active = tag.getBoolean("active");
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

    @Override
    public boolean isNatural() {
        return natural;
    }

    @Override
    public void setIsNatural(boolean bool) {
        natural = bool;
    }
}
