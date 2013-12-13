package net.lomeli.cb.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.cb.lib.Strings;

public class BlockCB extends Block {

    protected String blockTexture;

    public BlockCB(int par1, Material par2Material, String texture) {
        super(par1, par2Material);
        this.blockTexture = texture;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture);
    }

    @Override
    public Block setUnlocalizedName(String name) {
        return super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + name);
    }
}
