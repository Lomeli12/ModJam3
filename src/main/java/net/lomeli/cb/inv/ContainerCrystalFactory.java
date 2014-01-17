package net.lomeli.cb.inv;

import net.lomeli.cb.client.gui.SlotShard;
import net.lomeli.cb.client.gui.SlotOutput;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ContainerCrystalFactory extends Container {
    private TileCrystalFactory tile;
    private int lastCharge, lastCookingTime, lastTank1, lastTank2, lastTank3;

    public ContainerCrystalFactory(InventoryPlayer inventoryPlayer, TileCrystalFactory tileEntity) {
        tile = tileEntity;
        
        this.addSlotToContainer(new SlotShard(tile, 0, 40, 18));
        this.addSlotToContainer(new SlotShard(tile, 1, 82, 18));
        this.addSlotToContainer(new SlotShard(tile, 2, 116, 18));
        
        this.addSlotToContainer(new Slot(tile, 3, 31, 87));
        this.addSlotToContainer(new Slot(tile, 4, 49, 87));
        this.addSlotToContainer(new Slot(tile, 5, 73, 87));
        this.addSlotToContainer(new Slot(tile, 6, 91, 87));
        this.addSlotToContainer(new Slot(tile, 7, 116, 87));
        
        this.addSlotToContainer(new SlotOutput(tile, 8, 166, 42));
        
        for (int l = 0; l < 3; ++l) {
            for (int i1 = 0; i1 < 9; ++i1) {
                this.addSlotToContainer(new Slot(inventoryPlayer, i1 + l * 9 + 9, 26 + i1 * 18, 139 + l * 18));
            }
        }

        for (int j1 = 0; j1 < 9; j1++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, j1, 26 + j1 * 18, 197));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, lastCharge);
        par1ICrafting.sendProgressBarUpdate(this, 1, lastCookingTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, lastTank1);
        par1ICrafting.sendProgressBarUpdate(this, 3, lastTank2);
        par1ICrafting.sendProgressBarUpdate(this, 4, lastTank3);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i) {
            ICrafting icrafting = (ICrafting) this.crafters.get(i);

            if (lastCharge != tile.getCurrentCharge())
                icrafting.sendProgressBarUpdate(this, 0, tile.getCurrentCharge());

            if (lastCookingTime != tile.cookingTime[3])
                icrafting.sendProgressBarUpdate(this, 1, tile.cookingTime[3]);

            if (lastTank1 != tile.getTank1Amount())
                icrafting.sendProgressBarUpdate(this, 2, tile.getTank1Amount());

            if (lastTank2 != tile.getTank2Amount())
                icrafting.sendProgressBarUpdate(this, 3, tile.getTank2Amount());

            if (lastTank3 != tile.getTank3Amount())
                icrafting.sendProgressBarUpdate(this, 4, tile.getTank3Amount());
        }

        lastCharge = tile.getCurrentCharge();
        lastCookingTime = tile.cookingTime[3];
        lastTank1 = tile.getTank1Amount();
        lastTank2 = tile.getTank2Amount();
        lastTank3 = tile.getTank3Amount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        super.updateProgressBar(par1, par2);

        if (par1 == 0)
            tile.setCurrentCharge(par2);

        if (par1 == 1)
            tile.cookingTime[3] = par2;

        if (par1 == 2) {
            if (tile.tanks[0].getFluid() != null)
                tile.tanks[0].getFluid().amount = par2;
        }

        if (par1 == 3) {
            if (tile.tanks[0].getFluid() != null)
                tile.tanks[1].getFluid().amount = par2;
        }

        if (par1 == 4) {
            if (tile.tanks[0].getFluid() != null)
                tile.tanks[2].getFluid().amount = par2;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        return null;
    }

}
