package net.lomeli.cb.tile;

import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.item.ModItems;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class TileCrystalizer extends TileEntity implements IFluidHandler {
    private FluidTank tank1, tank2, tank3;
    private ItemStack heldItem;
    private int tick, firstEle, secondEle, thirdEle;
    private boolean crystal;

    public TileCrystalizer() {
        tank1 = tank2 = tank3 = new FluidTank(1000);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (heldItem == null) {
                TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
                if (tile != null) {
                    if (tile instanceof IInventory) {
                        for (int slot = 0; slot < ((IInventory) tile).getSizeInventory(); slot++) {
                            if (((IInventory) tile).getStackInSlot(slot) == null) {
                                ((IInventory) tile).setInventorySlotContents(slot, heldItem);
                                heldItem = null;
                                break;
                            }
                        }
                    }
                }
            }
            if (crystal && ++tick >= 500) {
                tick = 0;
                crystal = false;
                throwCrystalIntoChest(secondEle, thirdEle, firstEle);
            }

            if (tank1.getFluid() != null && tank1.getFluid().getFluid() != null && tank2.getFluid() != null && tank2.getFluid().getFluid() != null && tank3.getFluid() != null
                    && tank3.getFluid().getFluid() != null) {
                if (tank1.getFluidAmount() >= 1000 && tank2.getFluidAmount() >= 1000 && tank3.getFluidAmount() >= 1000) {
                    if (tank1.getFluidAmount() >= 1000) {
                        firstEle = FluidElements.getFluidElement(tank1.getFluid().getFluid()).getElementID();
                        tank1.drain(1000, true);
                    }
                    if (tank2.getFluidAmount() >= 1000) {
                        secondEle = FluidElements.getFluidElement(tank1.getFluid().getFluid()).getElementID();
                        tank2.drain(1000, true);
                    }
                    if (tank3.getFluidAmount() >= 1000) {
                        thirdEle = FluidElements.getFluidElement(tank3.getFluid().getFluid()).getElementID();
                        tank3.drain(1000, true);
                    }
                    crystal = true;
                }
            }

        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
        ForgeDirection left = ForgeDirection.UNKNOWN, right = ForgeDirection.UNKNOWN;

        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
            left = ForgeDirection.EAST;
            right = ForgeDirection.WEST;
        } else if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
            left = ForgeDirection.NORTH;
            right = ForgeDirection.SOUTH;
        }

        if (from == dir)
            return tank1.fill(resource, doFill);
        else if (from == left)
            return tank2.fill(resource, doFill);
        else if (from == right)
            return tank3.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return resource;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank1.drain(maxDrain, false);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        ForgeDirection dir = ForgeDirection.getOrientation(worldObj.getBlockMetadata(xCoord, yCoord, zCoord));
        ForgeDirection left = ForgeDirection.UNKNOWN, right = ForgeDirection.UNKNOWN;

        if (dir == ForgeDirection.NORTH || dir == ForgeDirection.SOUTH) {
            left = ForgeDirection.EAST;
            right = ForgeDirection.WEST;
        } else if (dir == ForgeDirection.EAST || dir == ForgeDirection.WEST) {
            left = ForgeDirection.NORTH;
            right = ForgeDirection.SOUTH;
        }

        if (fluid != null) {
            if (from == dir) {
                if (tank1.getFluid() != null && tank1.getFluid().getFluid() != null)
                    return tank1.getFluid().getFluid().equals(fluid);
                return true;
            } else if (from == left) {
                if (tank2.getFluid() != null && tank2.getFluid().getFluid() != null)
                    return tank2.getFluid().getFluid().equals(fluid);
                return true;
            } else if (from == right) {
                if (tank3.getFluid() != null && tank3.getFluid().getFluid() != null)
                    return tank3.getFluid().getFluid().equals(fluid);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[] { tank1.getInfo(), tank2.getInfo(), tank3.getInfo() };
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        writeTag(tag);
    }

    public void writeTag(NBTTagCompound tag) {
        tank1.writeToNBT(tag);
        tank2.writeToNBT(tag);
        tank3.writeToNBT(tag);
        if (heldItem != null) {
            tag.setInteger("HeldItemID", heldItem.itemID);
            tag.setInteger("HeldItemMeta", heldItem.getItemDamage());
        }
        tag.setInteger("firstEle", firstEle);
        tag.setInteger("secondEle", secondEle);
        tag.setInteger("neutralEle", thirdEle);
        tag.setBoolean("crystal", crystal);
        tag.setInteger("tick", tick);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        tank1.readFromNBT(tag);
        tank2.readFromNBT(tag);
        tank3.readFromNBT(tag);
        heldItem = new ItemStack(tag.getInteger("HeldItemID"), 1, tag.getInteger("HeldItemMeta"));

        firstEle = tag.getInteger("firstEle");
        secondEle = tag.getInteger("secondEle");
        thirdEle = tag.getInteger("neutralEle");
        crystal = tag.getBoolean("crystal");
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

    public void throwCrystalIntoChest(int i, int j, int k) {
        boolean placedItem = false;
        ItemStack finishedCrystal = new ItemStack(ModItems.crystalItem.itemID, 1, 1);
        ElementRegistry.writeBasic(finishedCrystal, i, j, k);
        TileEntity tile = worldObj.getBlockTileEntity(xCoord, yCoord + 1, zCoord);
        if (tile != null) {
            if (tile instanceof IInventory) {
                for (int slot = 0; slot < ((IInventory) tile).getSizeInventory(); slot++) {
                    if (((IInventory) tile).getStackInSlot(slot) == null) {
                        placedItem = true;
                        ((IInventory) tile).setInventorySlotContents(slot, finishedCrystal);
                        break;
                    }
                }
            }
        }
        if (!placedItem)
            heldItem = finishedCrystal;
    }

}
