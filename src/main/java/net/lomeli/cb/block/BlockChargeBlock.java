package net.lomeli.cb.block;

import java.util.List;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileChargeBox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChargeBlock extends BlockCB {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public BlockChargeBlock() {
        super(Material.iron, "chargeBox_");
        setBlockName("chargeBox");
        setHardness(2.0F);
        setResistance(10.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (!world.isRemote) {
            TileChargeBox tile = (TileChargeBox) world.getTileEntity(x, y, z);
            if (tile != null) {
                if (!player.isSneaking()) {
                    player.addChatMessage(new ChatComponentText("Charge: " + tile.getCurrentCharge() + "/" + tile.getChargeCapcity()));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileChargeBox(metadata);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        TileChargeBox tile = (TileChargeBox) world.getTileEntity(x, y, z);
        if (tile != null) {
            tile.setType(meta);
        }
        return meta;
    }

    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[3];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < 3; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public int damageDropped(int par1) {
        return par1;
    }

    @Override
    public IIcon getIcon(int par1, int par2) {
        return this.iconArray[par2 % this.iconArray.length];
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return this.damageDropped(world.getBlockMetadata(x, y, z));
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    public static class ItemChargeBlock extends ItemBlock {

        public ItemChargeBlock(Block par1) {
            super(par1);
            this.setMaxDamage(0);
            this.setHasSubtypes(true);
        }

        @Override
        public IIcon getIconFromDamage(int par1) {
            return this.field_150939_a.getIcon(0, par1);
        }

        @Override
        public int getMetadata(int meta) {
            return meta;
        }

        @Override
        public String getUnlocalizedName(ItemStack stack) {
            return this.field_150939_a.getUnlocalizedName() + "." + stack.getItemDamage();
        }
    }

}
