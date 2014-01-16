package net.lomeli.cb.tile;

import net.lomeli.cb.item.IEnergyItem;
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

public class TileChargeBox extends TileEntity implements IInventory, IEnergy {
    private ItemStack[] inventory;
    private int currentCharge, maxCharge;
    
    public TileChargeBox(){
        inventory = new ItemStack[1];
    }
    

    public void init() {
        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
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
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            init();
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            if (meta == 2)
                currentCharge = maxCharge;

            for (int x = xCoord - 1; x < xCoord + 2; x++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    if (x != xCoord || z != zCoord) {
                        TileEntity tile = worldObj.getBlockTileEntity(x, yCoord, z);
                        if (tile != null && tile instanceof ICrystal) {
                            if (((ICrystal) tile).getPower() < ((ICrystal) tile).getMaxPower()) {
                                if (canCompleteTask(20))
                                    ((ICrystal) tile).addPower(useCharge(20));
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
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return this.inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventory[par1] != null) {
            ItemStack itemstack;

            if (this.inventory[par1].stackSize <= par2) {
                itemstack = this.inventory[par1];
                this.inventory[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            } else {
                itemstack = this.inventory[par1].splitStack(par2);

                if (this.inventory[par1].stackSize == 0)
                    this.inventory[par1] = null;

                this.onInventoryChanged();
                return itemstack;
            }
        } else
            return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (this.inventory[i] != null) {
            ItemStack itemstack = this.inventory[i];
            this.inventory[i] = null;
            return itemstack;
        } else
            return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.inventory[i] = itemstack;
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return Strings.CHARGE_BOX_GUI + worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
    }

    @Override
    public boolean isInvNameLocalized() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
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
        return itemstack.getItem() instanceof IEnergyItem;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i) {
            if (this.inventory[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tag.setInteger("CrystalCharge", currentCharge);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        NBTTagList nbttaglist = tag.getTagList("Items");
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.tagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        currentCharge = tag.getInteger("CrystalCharge");
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
