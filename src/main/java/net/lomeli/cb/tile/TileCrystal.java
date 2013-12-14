package net.lomeli.cb.tile;

import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.DebugAbility;
import net.lomeli.cb.element.ElementRegistry;

public class TileCrystal extends TileEntity implements ICrystal {
    public Random rand;
    private int energy;
    private boolean active, natural;
    private CrystalAbility[] abilities;
    public int firstEle, secondEle, thridEle, ability1ID, ability2ID;
    public boolean abilitiesSet, passiveAbility;
    public float red, green, blue;

    public TileCrystal() {
        rand = new Random();
        abilities = new CrystalAbility[3];
        if(!abilitiesSet) {
            firstEle = rand.nextInt(ElementRegistry.elements.size());
            secondEle = rand.nextInt(ElementRegistry.elements.size());
            ability1ID = rand.nextInt(4);
            ability2ID = rand.nextInt(4);
            abilitiesSet = true;
            setIsNatural(true);
        }
        red = rand.nextInt(256);
        green = rand.nextInt(256);
        blue = rand.nextInt(256);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(!worldObj.isRemote) {
            System.out.println(xCoord + ", " + yCoord + ", " + zCoord);
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
            }else {
                this.setAbilityOne(ElementRegistry.elements.get(firstEle).abilities()[ability1ID]);
                this.setAbilityTwo(ElementRegistry.elements.get(secondEle).abilities()[ability2ID]);
                this.setPowerAbility(new DebugAbility());
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
        tag.setBoolean("abilitiesSet", abilitiesSet);
        tag.setBoolean("natural", this.natural);
        tag.setInteger("element1", firstEle);
        tag.setInteger("element2", secondEle);

        tag.setInteger("ability1", ability1ID);
        tag.setInteger("ability2", ability2ID);
        if(!this.natural) {
            tag.setBoolean("ability3", passiveAbility);
            tag.setInteger("element3", thridEle);
        }
        tag.setFloat("red", red);
        tag.setFloat("blue", blue);
        tag.setFloat("green", green);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        this.energy = tag.getInteger("power");
        this.active = tag.getBoolean("active");
        this.natural = tag.getBoolean("natural");
        this.abilitiesSet = tag.getBoolean("abilitiesSet");
        this.firstEle = tag.getInteger("element1");
        this.secondEle = tag.getInteger("element2");
        this.ability1ID = tag.getInteger("ability1");
        this.ability2ID = tag.getInteger("ability2");
        if(!natural) {
            this.thridEle = tag.getInteger("element3");
            this.passiveAbility = tag.getBoolean("ability3");
        }
        this.red = tag.getFloat("red");
        this.green = tag.getFloat("green");
        this.blue = tag.getFloat("blue");
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
    
    public float red(){
        return red;
    }
    
    public float green(){
        return green;
    }
    
    public float blue(){
        return blue;
    }
}
