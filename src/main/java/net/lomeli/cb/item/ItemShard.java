package net.lomeli.cb.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.lib.Strings;

public class ItemShard extends ItemCB{
    
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
    public ItemShard(int par1) {
        super(par1, "shard_");
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":shard");
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[5];
        for(int i = 0; i < iconArray.length; i++){
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture + i);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIconFromDamage(int meta){
        return this.iconArray[meta];
    }
    
    @Override
    public int getMetadata(int meta){
        return meta;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List){
        for(int i = 0; i < 5; i++){
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
    
    @Override
    public String getItemDisplayName(ItemStack stack){
        String unlocalizedName = stack.getUnlocalizedName();
        return StatCollector.translateToLocal(unlocalizedName + "." + stack.getItemDamage());
    }
}
