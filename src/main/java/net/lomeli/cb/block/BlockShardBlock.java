package net.lomeli.cb.block;

import java.util.List;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockShardBlock extends Block {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockShardBlock(int par1) {
        super(par1, Material.iron);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":shardBlock");
        this.setCreativeTab(CrystalBearers.modTab);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[5];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":shardBlock_" + i);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 5; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }

    public static class ItemShardBlock extends ItemBlock {

        public ItemShardBlock(int par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        public Icon getIconFromDamage(int par1) {
            return ModBlocks.shardBlock.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }

        @Override
        public String getItemDisplayName(ItemStack stack) {
            String unlocalizedName = stack.getUnlocalizedName();
            return StatCollector.translateToLocal(unlocalizedName.substring(0, unlocalizedName.length() - 5) + "." + stack.getItemDamage());
        }
    }
}
