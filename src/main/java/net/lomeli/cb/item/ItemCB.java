package net.lomeli.cb.item;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.lib.Strings;

public class ItemCB extends Item {
    protected String itemTexture;

    public ItemCB(int par1, String texture) {
        super(par1);
        this.itemTexture = texture;
        this.setCreativeTab(CrystalBearers.modTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture);
    }

}
