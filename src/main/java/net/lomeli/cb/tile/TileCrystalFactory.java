package net.lomeli.cb.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCrystalFactory extends TileEntity implements IEnergy, IInventory, IFluidHandler {

    private boolean hasMaster, isMaster;
    private int masterX, masterY, masterZ;

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (hasMaster()) {
                if (isMaster()) {
                    if (!checkMultiBlockForm())
                        resetMultiBlockStructure();

                }
            } else {
                if (checkMultiBlockForm())
                    setupMultiBlockStructure();
            }
        }
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

        return i > 7 && worldObj.isAirBlock(xCoord, yCoord + 1, zCoord);
    }

    public void resetMultiBlockStructure() {
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord; y < yCoord + 3; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileCrystalFactory))
                        ((TileCrystalFactory) tile).reset();
                }
    }

    public void setupMultiBlockStructure() {
        for (int x = xCoord - 1; x < xCoord + 2; x++)
            for (int y = yCoord; y < yCoord + 3; y++)
                for (int z = zCoord - 1; z < zCoord + 2; z++) {
                    boolean master = (x == xCoord && y == yCoord && z == zCoord);
                    TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
                    if (tile != null && (tile instanceof TileCrystalFactory))
                        ((TileCrystalFactory) tile).setMaster(x, y, z, master);
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
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getSizeInventory() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getInvName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void openChest() {
        // TODO Auto-generated method stub

    }

    @Override
    public void closeChest() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getCurrentCharge() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getChargeCapcity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int addCharge(int charge) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int useCharge(int charge) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean canCompleteTask(int charge) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChargeBox() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isGenerator() {
        // TODO Auto-generated method stub
        return false;
    }

}
