package net.lomeli.cb.core.handler;

import net.lomeli.cb.client.gui.GuiCrystalFactory;
import net.lomeli.cb.client.gui.GuiDatabase;
import net.lomeli.cb.inv.ContainerCrystalFactory;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 99) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileCrystalFactory)
                    return new ContainerCrystalFactory(player.inventory, (TileCrystalFactory) tile);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 99) {
            TileEntity tile = world.getBlockTileEntity(x, y, z);
            if (tile != null) {
                if (tile instanceof TileCrystalFactory)
                    return new GuiCrystalFactory(player.inventory, (TileCrystalFactory) tile);
            }
        } else if (ID == 0){
            if (player.getCurrentEquippedItem() != null) {
                ItemStack stack = player.getCurrentEquippedItem();
                if(stack.getUnlocalizedName().equals(ModItems.scanner.getUnlocalizedName())){
                    if (stack.getItemDamage() == 0){
                        
                    }else if (stack.getItemDamage() == 1){
                        return new GuiDatabase(stack, player);
                    }
                }
            }
        }
        return null;
    }

}
