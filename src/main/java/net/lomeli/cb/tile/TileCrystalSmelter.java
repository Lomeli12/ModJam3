package net.lomeli.cb.tile;

import net.lomeli.cb.core.ModUtil;
import net.lomeli.cb.element.FluidElements;
import net.lomeli.cb.item.IShard;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileCrystalSmelter extends TileEntity implements IInventory, IFluidHandler {
    private ItemStack[] inventory;
    private int heatLevel, cookTime;
    private FluidTank tank;

    public TileCrystalSmelter() {
        inventory = new ItemStack[1];
        tank = new FluidTank(1000);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            Block blockID = worldObj.getBlock(xCoord, yCoord - 1, zCoord);
            if (blockID.getUnlocalizedName().equals(Blocks.lava.getUnlocalizedName()) || blockID.getUnlocalizedName().equals(Blocks.flowing_lava.getUnlocalizedName()))
                heatUp();
            else {
                if (heatLevel > 0)
                    heatLevel--;
            }

            if (inventory[0] != null && inventory[0].getItem() instanceof IShard && heatLevel >= 200) {
                if (inventory[0].stackSize == 64) {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
                    if (++cookTime >= 500) {
                        Fluid itemFluid = FluidElements.getFluidBaseOnStack(inventory[0]);
                        if (itemFluid != null) {
                            FluidStack crystalFluid = new FluidStack(itemFluid, 1000);
                            if (crystalFluid != null) {
                                fill(null, crystalFluid, true);
                                setInventorySlotContents(0, null);
                                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
                            }
                        }
                        cookTime = 0;
                    }
                }
            }

            if (tank.getFluid() != null && tank.getFluid().getFluid() != null) {
                for (TileEntity tile : ModUtil.getSurroundingTiles(worldObj, xCoord, yCoord, zCoord)) {
                    if (tile != null) {
                        if (tile instanceof TileCrystalizer) {
                            if (((TileCrystalizer) tile).canFill(ModUtil.getDirectionFromTile(worldObj.getTileEntity(xCoord, yCoord, zCoord), tile), tank.getFluid()
                                    .getFluid())) {
                                tank.drain(
                                        ((TileCrystalizer) tile).fill(ModUtil.getDirectionFromTile(worldObj.getTileEntity(xCoord, yCoord, zCoord), tile),
                                                tank.getFluid(), true), true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public void heatUp() {
        if (heatLevel < 8000)
            heatLevel++;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        int amount = 0;
        if (doFill) {
            if (resource != null) {
                if (tank.getFluid() != null && tank.getFluid().getFluid() != null) {
                    if (resource.getFluid().equals(tank.getFluid().getFluid()))
                        amount = tank.fill(resource, doFill);
                } else
                    amount = tank.fill(resource, doFill);
            }
        }
        return amount;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (doDrain) {
            if (resource != null && tank.getFluid() != null) {
                if (resource.getFluid().equals(tank.getFluid().getFluid())) {
                    return (tank.getFluidAmount() >= resource.amount) ? tank.drain(resource.amount, doDrain) : tank.drain(tank.getFluidAmount(), doDrain);
                }
            }
        }
        return resource;
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
        return true;
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
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return "gui." + Strings.MOD_ID.toLowerCase() + ":crystalSmelter";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
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
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        if (itemstack != null && itemstack.getItem() instanceof IShard) {
            if (inventory[i] != null && (itemstack.getUnlocalizedName().equals(inventory[i].getUnlocalizedName()) && itemstack.getItemDamage() == inventory[i].getItemDamage()))
                return inventory[i].stackSize < 64;
            else
                return true;
        }
        return false;
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

        tag.setTag("Items", nbttaglist);

        tank.writeToNBT(tag);

        tag.setInteger("heatLevel", heatLevel);
        tag.setInteger("cookTime", cookTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        readNBT(tag);
    }

    public void readNBT(NBTTagCompound tag) {
        NBTTagList nbttaglist = tag.getTagList("Items", this.getSizeInventory());
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length) {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
        tank.readFromNBT(tag);

        cookTime = tag.getInteger("cookTime");
        heatLevel = tag.getInteger("heatLevel");
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

    public void addItemToSlot(ItemStack stack) {
        if (this.isItemValidForSlot(0, stack)) {
            if (inventory[0] != null) {
                if (stack.getUnlocalizedName().equals(inventory[0].getUnlocalizedName()) && stack.getItemDamage() == inventory[0].getItemDamage() && inventory[0].stackSize < 64) {
                    stack.stackSize--;
                    inventory[0].stackSize++;
                }
            } else {
                stack.stackSize--;
                inventory[0] = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
            }
        }
    }

}
