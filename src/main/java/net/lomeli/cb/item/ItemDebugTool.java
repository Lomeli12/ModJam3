package net.lomeli.cb.item;

import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.water.AbilityMorphaReach;

public class ItemDebugTool extends Item{

    public ItemDebugTool(int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10){
        if(!par3World.isRemote){
            new AbilityMorphaReach().enviromentalEffect(par3World, par4, par5, par6, new Random());
        }
        return true;
    }
    

}
