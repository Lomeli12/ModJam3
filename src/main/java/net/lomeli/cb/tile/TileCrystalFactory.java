package net.lomeli.cb.tile;

import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.item.IShard;
import net.lomeli.cb.lib.Strings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCrystalFactory extends TileEntity implements IEnergy, IInventory, IFluidHandler {

    private FluidTank tank1, tank2, tank3;
    private ItemStack[] inventory;
    private boolean hasMaster, isMaster;
    private int masterX, masterY, masterZ, currentCharge, maxCharge;
    private boolean[] toDoStuff;
    private int[] cookingTime;

    public TileCrystalFactory() {
        inventory = new ItemStack[9];
        toDoStuff = new boolean[4];
        cookingTime = new int[4];
        maxCharge = 20000;
        hasMaster = false;
        isMaster = false;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (hasMaster()) {
                if (isMaster()) {
                    if (!checkMultiBlockForm())
                        resetMultiBlockStructure();
                    
                    for(int i = 0; i < 3; i++){
                        smeltCrystals(i);
                    }
                    
                } else {
                    if (!checkForMaster())
                        resetMultiBlockStructure(masterX, masterY, masterZ);
                }
            } else {
                if (checkMultiBlockForm())
                    setupMultiBlockStructure();
            }
        }
    }
    
    public void smeltCrystals(int group) {
        if (!worldObj.isRemote) {
            switch (group) {
            case 0:
                if (checkForShards(0)){
                    if(--cookingTime[group] <= 0){
                        toDoStuff[group] = false;
                        Fluid newElementFluid = FluidElements.getFluidBaseOnStack(getStackInSlot(0));
                        if(newElementFluid != null && tank1.getFluidAmount() == 0){
                            setInventorySlotContents(0, null);
                            tank1.fill(new FluidStack(newElementFluid, 1000), true);
                        }
                    }
                } else {
                    cookingTime[group] = 0;
                    toDoStuff[group] = false;
                }
                break;
            case 1:
                break;
            case 2:
                break;
            }
        }
    }

    public void process(int group) {
        if (!worldObj.isRemote) {
            if(checkForShards(group)){
                toDoStuff[group] = true;
                cookingTime[group] = 300;
            }
        }
    }

    public boolean checkForShards(int group) {
        if (group < 3) {
            ItemStack shard1 = inventory[group];
            if (shard1 != null && shard1.getItem() instanceof IShard)
                return (shard1.stackSize >= 40);
        }
        return false;
    }

    public boolean checkMultiBlockForm() {
        int i = 0;
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord; y < yCoord + 3; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileCrystalFactory))
                        i++;
                }

        return i > 25 && worldObj.isAirBlock(xCoord, yCoord + 1, zCoord);
    }

    public boolean checkForMaster() {
        TileEntity tile = worldObj.getBlockTileEntity(masterX, masterY, masterZ);
        return (tile != null && (tile instanceof TileCrystalFactory));
    }

    public void resetMultiBlockStructure(int par1, int par2, int par3) {
        for (int x = par1 - 1; x < par1 + 2; x++)
            for (int y = par2; y < par2 + 3; y++)
                for (int z = par3 - 1; z < par3 + 2; z++) {
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileCrystalFactory))
                        ((TileCrystalFactory) tile).reset();
                }
    }

    public void resetMultiBlockStructure() {
        resetMultiBlockStructure(xCoord, yCoord, zCoord);
    }

    public void setupMultiBlockStructure() {
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord; y < yCoord + 3; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    boolean master = (x == xCoord && y == yCoord && z == zCoord);
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileCrystalFactory))
                        ((TileCrystalFactory) tile).setMaster(xCoord, yCoord, zCoord, master);
                }
    }

    public boolean hasMaster() {
        return hasMaster;
    }

    public int getMasterX() {
        return masterX;
    }

    public int getMasterY() {
        return masterY;
    }

    public int getMasterZ() {
        return masterZ;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void reset() {
        masterX = 0;
        masterY = 0;
        masterZ = 0;
        hasMaster = false;
        isMaster = false;
    }

    public void setMaster(int x, int y, int z, boolean isMaster) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if (tile != null && (tile instanceof TileCrystalFactory)) {
            masterX = x;
            masterY = y;
            masterZ = z;
            hasMaster = true;
            this.isMaster = isMaster;
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (hasMaster()) {
            if (!isMaster()) {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.fill(from, resource, doFill) : 0;
            } else {
                if (from.equals(ForgeDirection.UP))
                    return tank1.fill(resource, doFill);
                else if (from.equals(ForgeDirection.DOWN))
                    return tank2.fill(resource, doFill);
                else
                    return tank3.fill(resource, doFill);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (hasMaster()) {
            if (!isMaster()) {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.drain(from, maxDrain, doDrain) : null;
            } else {
                if (from.equals(ForgeDirection.UP))
                    return tank1.drain(maxDrain, doDrain);
                else if (from.equals(ForgeDirection.DOWN))
                    return tank2.drain(maxDrain, doDrain);
                else
                    return tank3.drain(maxDrain, doDrain);
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return hasMaster();
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return hasMaster();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank1.getInfo(), tank2.getInfo(), tank3.getInfo() };
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack itemStack = getStackInSlot(slot);
        if (itemStack != null) {
            if (itemStack.stackSize <= amount)
                setInventorySlotContents(slot, null);
            else {
                itemStack.splitStack(amount);
                if (itemStack.stackSize == 0)
                    setInventorySlotContents(slot, null);
            }
        }
        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
    }

    @Override
    public String getInvName() {
        return Strings.CRYSTAL_FACTORY;
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
        return hasMaster();
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (hasMaster()) {
            if (isMaster()) {

            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.isItemValidForSlot(i, itemstack) : false;
            }
        }
        return false;
    }

    @Override
    public int getCurrentCharge() {
        if (hasMaster()) {
            if (isMaster()) {
                return currentCharge;
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.getCurrentCharge() : 0;
            }
        }
        return 0;
    }

    @Override
    public int getChargeCapcity() {
        return maxCharge;
    }

    @Override
    public int addCharge(int charge) {
        if (hasMaster()) {
            if (isMaster()) {
                currentCharge += charge;
                if (currentCharge > getChargeCapcity())
                    currentCharge = getChargeCapcity();
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.addCharge(charge) : 0;
            }
        }
        return 0;
    }

    @Override
    public int useCharge(int charge) {
        if (hasMaster()) {
            if (isMaster()) {
                if (canCompleteTask(charge)) {
                    currentCharge -= charge;
                    return charge;
                }
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.useCharge(charge) : 0;
            }
        }
        return 0;
    }

    @Override
    public boolean canCompleteTask(int charge) {
        if (hasMaster()) {
            if (isMaster()) {
                return getChargeCapcity() >= charge;
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.canCompleteTask(charge) : false;
            }
        }
        return false;
    }

    @Override
    public boolean isChargeBox() {
        return false;
    }

    @Override
    public boolean isGenerator() {
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound data) {
        super.readFromNBT(data);
        readNBT(data);
    }

    public void readNBT(NBTTagCompound data) {
        tank1.readFromNBT(data);
        tank2.readFromNBT(data);
        tank3.readFromNBT(data);
        NBTTagList tagList = data.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length)
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        tank1.writeToNBT(data);
        tank2.writeToNBT(data);
        tank3.writeToNBT(data);
        NBTTagList tagList = new NBTTagList();
        for (int currentIndex = 0; currentIndex < inventory.length; ++currentIndex) {
            if (inventory[currentIndex] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) currentIndex);

                inventory[currentIndex].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        data.setTag("Inventory", tagList);
    }

    @Override
    public Packet getDescriptionPacket() {
        Packet132TileEntityData packet = (Packet132TileEntityData) super.getDescriptionPacket();
        NBTTagCompound tag = packet != null ? packet.data : new NBTTagCompound();
        writeNBT(tag);
        return new Packet132TileEntityData(xCoord, yCoord, zCoord, 1, tag);
    }

    @Override
    public void onDataPacket(INetworkManager networkManager, Packet132TileEntityData packet) {
        super.onDataPacket(networkManager, packet);
        NBTTagCompound nbtTag = packet.data;
        readNBT(nbtTag);
    }
}
