package net.lomeli.cb.tile;

import java.util.Random;

import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.element.IElement;
import net.lomeli.cb.item.IShard;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.Strings;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileCrystalFactory extends TileEntity implements IEnergy, ISidedInventory, IFluidHandler {

    public FluidTank[] tanks;
    private ItemStack[] inventory;
    private boolean hasMaster, isMaster;
    private int masterX, masterY, masterZ, currentCharge, maxCharge;
    public boolean[] toDoStuff;
    public int[] cookingTime;
    private IElement[] elements;

    public TileCrystalFactory() {
        inventory = new ItemStack[9];
        toDoStuff = new boolean[4];
        elements = new IElement[3];
        cookingTime = new int[4];
        maxCharge = 30000;
        tanks = new FluidTank[3];
        for (int i = 0; i < tanks.length; i++) {
            tanks[i] = new FluidTank(1000);
        }
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (hasMaster()) {
                if (isMaster()) {
                    if (!checkMultiBlockForm())
                        resetMultiBlockStructure();
                    
                    for (int i = 0; i < 3; i++) {
                        if(tanks[i].getFluid() != null && tanks[i].getFluid().getFluid() != null){
                            if(tanks[i].getFluidAmount() <= 0)
                                tanks[i].setFluid(null);
                        }
                        
                        if (canCompleteTask(5))
                            smeltCrystals(i);
                    }

                    if (canCompleteTask(10))
                        formCrystal();
                } else {
                    if (!checkForMaster())
                        resetMultiBlockStructure(masterX, masterY, masterZ);
                }
            } else {
                if (checkMultiBlockForm()) {
                    setupMultiBlockStructure();
                    setMetaForBlocks();
                }
            }
        }
    }

    public boolean isReadyToForm() {
        int j = 0;
        for (int i = 0; i < tanks.length; i++) {
            if (tanks[i].getFluidAmount() >= 1000)
                j++;
        }
        return j >= 3;
    }

    public void startFormationProcess() {
        if (!worldObj.isRemote) {
            toDoStuff[3] = isReadyToForm();
            cookingTime[3] = 1000;
        }
    }

    public void formCrystal() {
        if (!worldObj.isRemote) {
            if (toDoStuff[3]) {
                useCharge(10);
                if (--cookingTime[3] <= 0) {
                    cookingTime[3] = 1000;
                    Random rand = new Random();
                    ItemStack crystal = new ItemStack(ModItems.crystalItem);
                    for (int i = 0; i < tanks.length; i++) {
                        if (tanks[i].getFluid() != null && tanks[i].getFluid().getFluid() != null && tanks[i].getFluidAmount() > 0) {
                            elements[i] = FluidElements.getFluidElement(tanks[i].getFluid().getFluid());
                            tanks[i].setFluid(null);
                        }
                    }
                    boolean continueProcess = true;
                    for (int i = 0; i < elements.length; i++) {
                        if (elements[i] == null) {
                            continueProcess = false;
                            break;
                        }
                    }

                    if (continueProcess) {
                        int ability1 = rand.nextInt(2), ability2 = rand.nextInt(2);
                        boolean power = false;
                        if (getStackInSlot(3) != null) {
                            ItemStack slot = getStackInSlot(3);
                            if (slot.getUnlocalizedName().equals(Item.ingotGold.getUnlocalizedName())) {
                                if (getStackInSlot(4) != null && getStackInSlot(4).getUnlocalizedName().equals(Item.goldNugget.getUnlocalizedName())) {
                                    ability1 = 1;
                                    decrStackSize(4, 1);
                                } else
                                    ability1 = 0;
                            } else if (slot.getUnlocalizedName().equals(Item.rottenFlesh.getUnlocalizedName())) {
                                if (getStackInSlot(4) != null && getStackInSlot(4).getUnlocalizedName().equals(Item.goldNugget.getUnlocalizedName())) {
                                    ability1 = 3;
                                    decrStackSize(4, 1);
                                } else
                                    ability1 = 2;
                            }
                            decrStackSize(3, 1);
                        }
                        if (getStackInSlot(5) != null) {
                            ItemStack slot = getStackInSlot(5);
                            if (slot.getUnlocalizedName().equals(Item.ingotGold.getUnlocalizedName())) {
                                if (getStackInSlot(6) != null && getStackInSlot(6).getUnlocalizedName().equals(Item.goldNugget.getUnlocalizedName())) {
                                    ability2 = 1;
                                    decrStackSize(6, 1);
                                } else
                                    ability2 = 0;
                            } else if (slot.getUnlocalizedName().equals(Item.rottenFlesh.getUnlocalizedName())) {
                                if (getStackInSlot(6) != null && getStackInSlot(6).getUnlocalizedName().equals(Item.goldNugget.getUnlocalizedName())) {
                                    ability2 = 3;
                                    decrStackSize(6, 1);
                                } else
                                    ability2 = 2;
                            }
                            decrStackSize(5, 1);
                        }
                        if (getStackInSlot(7) != null) {
                            if (getStackInSlot(7).getUnlocalizedName().equals(Item.goldNugget.getUnlocalizedName())) {
                                power = true;
                                decrStackSize(7, 1);
                            }
                        }
                        ElementRegistry.writeElementToItem(crystal, elements[0].getElementID(), ability1, elements[1].getElementID(), ability2, elements[2].getElementID(), power);
                        setInventorySlotContents(8, crystal);
                    }
                    toDoStuff[3] = false;
                }
            }
        }
    }

    public void smeltCrystals(int group) {
        if (!worldObj.isRemote) {
            if (group < 3) {
                if (checkForShards(group) && toDoStuff[group]) {
                    useCharge(5);
                    if (--cookingTime[group] <= 0) {
                        toDoStuff[group] = false;
                        Fluid newElementFluid = FluidElements.getFluidBaseOnStack(getStackInSlot(group));
                        if (newElementFluid != null && tanks[group].getFluidAmount() == 0) {
                            decrStackSize(group, 40);
                            tanks[group].fill(new FluidStack(newElementFluid, 1000), true);
                        }
                        cookingTime[group] = 0;
                        toDoStuff[group] = false;
                    }
                } else
                    cookingTime[group] = 0;
            }
        }
    }

    public void process(int group) {
        if (!worldObj.isRemote) {
            if (checkForShards(group)) {
                toDoStuff[group] = true;
                cookingTime[group] = 500;
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
                    if (tile != null && (tile instanceof TileCrystalFactory)) {
                        ((TileCrystalFactory) tile).reset();
                        worldObj.setBlockMetadataWithNotify(x, y, z, 0, 2);
                    }
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

    public void setMetaForBlocks() {
        // Master
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);

        // Centers
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord, 5, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 1, zCoord, 5, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 1, zCoord, 5, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord + 1, 5, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 1, zCoord - 1, 5, 2);

        // Horizontal
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord, zCoord, 6, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord, zCoord, 6, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 2, zCoord, 6, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 2, zCoord, 6, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord - 1, 4, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord + 1, 4, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord - 1, 4, 2);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord + 2, zCoord + 1, 4, 2);

        // Vertical
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 1, zCoord - 1, 3, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 1, zCoord + 1, 3, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 1, zCoord - 1, 3, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 1, zCoord + 1, 3, 2);

        // Corners
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord, zCoord - 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord, zCoord + 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord, zCoord - 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord, zCoord + 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 2, zCoord - 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord - 1, yCoord + 2, zCoord + 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 2, zCoord - 1, 1, 2);
        worldObj.setBlockMetadataWithNotify(xCoord + 1, yCoord + 2, zCoord + 1, 1, 2);
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
                    return tanks[0].fill(resource, doFill);
                else if (from.equals(ForgeDirection.DOWN))
                    return tanks[1].fill(resource, doFill);
                else
                    return tanks[2].fill(resource, doFill);
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
                    return tanks[0].drain(maxDrain, doDrain);
                else if (from.equals(ForgeDirection.DOWN))
                    return tanks[1].drain(maxDrain, doDrain);
                else
                    return tanks[2].drain(maxDrain, doDrain);
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
        return new FluidTankInfo[] { tanks[0].getInfo(), tanks[1].getInfo(), tanks[2].getInfo() };
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        if (hasMaster()) {
            if (isMaster())
                return inventory[i];
            else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.getStackInSlot(i) : null;
            }
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (hasMaster()) {
            if (isMaster()) {
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
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.decrStackSize(slot, amount) : null;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        if (hasMaster()) {
            if (isMaster()) {
                if (inventory[i] != null) {
                    ItemStack returnStack = inventory[i].copy();
                    inventory[i] = null;
                    return returnStack;
                }
            } else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                return tile != null ? tile.getStackInSlotOnClosing(i) : null;
            }
        }
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        if (hasMaster()) {
            if (isMaster())
                inventory[i] = itemstack;
            else {
                TileCrystalFactory tile = (TileCrystalFactory) worldObj.getBlockTileEntity(masterX, masterY, masterZ);
                if (tile != null)
                    tile.setInventorySlotContents(i, itemstack);
            }
        }
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
            if (isMaster())
                return i < 3 ? (itemstack.getItem() instanceof IShard) : i == inventory.length ? false : true;
            else {
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
                return getCurrentCharge() >= charge;
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
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].readFromNBT(data);
        }
        NBTTagList tagList = data.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); ++i) {
            NBTTagCompound tagCompound = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tagCompound.getByte("Slot");
            if (slot >= 0 && slot < this.inventory.length)
                this.inventory[slot] = ItemStack.loadItemStackFromNBT(tagCompound);
        }

        currentCharge = data.getInteger("currentCharge");
        masterX = data.getInteger("masterX");
        masterY = data.getInteger("masterY");
        masterZ = data.getInteger("masterZ");
        hasMaster = data.getBoolean("hasMaster");
        isMaster = data.getBoolean("isMaster");
        cookingTime = data.getIntArray("cookingTimes");

        for (int i = 0; i < toDoStuff.length; i++) {
            toDoStuff[i] = data.getBoolean("toDoGroup_" + i);
        }

    }

    @Override
    public void writeToNBT(NBTTagCompound data) {
        super.writeToNBT(data);
        writeNBT(data);
    }

    public void writeNBT(NBTTagCompound data) {
        for (int i = 0; i < tanks.length; i++) {
            tanks[i].writeToNBT(data);
        }
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

        data.setInteger("currentCharge", currentCharge);
        data.setInteger("masterX", masterX);
        data.setInteger("masterY", masterY);
        data.setInteger("masterZ", masterZ);
        data.setBoolean("hasMaster", hasMaster);
        data.setBoolean("isMaster", isMaster);

        data.setIntArray("cookingTimes", cookingTime);

        for (int i = 0; i < toDoStuff.length; i++) {
            data.setBoolean("toDoGroup_" + i, toDoStuff[i]);
        }
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

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        int[] i = new int[1];
        i[0] = 0;
        if (var1 == 1)
            i[0] = 2;
        else if (var1 == 0)
            i[0] = 1;
        return i;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        if (j == 1)
            return this.isItemValidForSlot(2, itemstack);
        else if (j == 0)
            return this.isItemValidForSlot(1, itemstack);
        return isItemValidForSlot(0, itemstack);
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return i < 3;
    }

    public int getTank1Amount() {
        return tanks[0].getFluidAmount();
    }

    public int getTank2Amount() {
        return tanks[1].getFluidAmount();
    }

    public int getTank3Amount() {
        return tanks[2].getFluidAmount();
    }

    public void setCurrentCharge(int i) {
        currentCharge = i;
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int par1) {
        return ((cookingTime[3] - 1500) * par1) / 1500;
    }
}
