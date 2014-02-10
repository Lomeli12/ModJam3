package net.lomeli.cb.block;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCB extends Block {

    protected String blockTexture;

    public BlockCB(Material par2Material, String texture) {
        super(par2Material);
        this.blockTexture = texture;
        this.setCreativeTab(CrystalBearers.modTab);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture);
    }

    @Override
    public Block setBlockName(String name) {
        return super.setBlockName(Strings.MOD_ID.toLowerCase() + ":" + name);
    }
}
