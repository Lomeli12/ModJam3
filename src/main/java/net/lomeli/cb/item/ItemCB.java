package net.lomeli.cb.item;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.lib.Strings;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCB extends Item {
    protected String itemTexture;

    public ItemCB(String texture) {
        super();
        this.itemTexture = texture;
        this.setCreativeTab(CrystalBearers.modTab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture);
    }

}
