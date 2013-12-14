package net.lomeli.cb.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import net.lomeli.cb.item.IShard;
import net.lomeli.cb.lib.Strings;

public class TileCrystalSmelter extends TileEntity implements IInventory, IFluidHandler {
    private ItemStack[] inventory;
    private int heatLevel, cookTime;
    private FluidTank tank;

    public TileCrystalSmelter() {
        inventory = new ItemStack[1];
        tank = new FluidTank(1000);
    }
    
    public void heatUp(){
        heatLevel++;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int amount = 0;
        if(doFill){
            if(resource != null) {
                if(tank.getFluid() != null && tank.getFluid().getFluid() != null){
                    if(resource.getFluid().equals(tank.getFluid().getFluid()))
                        amount = tank.fill(resource, doFill);
                }else
                    amount = tank.fill(resource, doFill);
            }
        }
        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if(doDrain){
            if(resource != null && tank.getFluid() != null){
                if(resource.getFluid().equals(tank.getFluid().getFluid())){
                    return (tank.getFluidAmount() >= resource.amount) ? tank.drain(resource.amount, doDrain) : tank.drain(tank.getFluidAmount(), doDrain);
                }
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return ((tank.getFluid() != null || tank.getFluid().getFluid() != null) ? ((tank.getFluidAmount() < tank.getCapacity()) && fluid.equals(tank.getFluid().getFluid())) : true);
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank.getInfo() };
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventory[par1] != null)
        {
            ItemStack itemstack;

            if (this.inventory[par1].stackSize <= par2)
            {
                itemstack = this.inventory[par1];
                this.inventory[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[par1].splitStack(par2);

                if (this.inventory[par1].stackSize == 0)
                    this.inventory[par1] = null;

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.inventory[i] != null)
        {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        }
        else
            return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.inventory[i] = itemstack;
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return StatCollector.translateToLocal("gui." + Strings.MOD_ID.toLowerCase() + ":crystalSmelter");
    }

    @Override
    public boolean isInvNameLocalized() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return itemstack.getItem() instanceof IShard;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.inventory.length; ++i) {
            if(this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setTag("Items", nbttaglist);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        NBTTagList nbttaglist = tag.getTagList("Items");
        for(int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if(j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
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
