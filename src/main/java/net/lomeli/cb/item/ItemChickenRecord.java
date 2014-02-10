package net.lomeli.cb.item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemChickenRecord extends ItemRecord {

    @SuppressWarnings("rawtypes")
    private static final Map records = new HashMap();

    public final String recordName;

    @SuppressWarnings("unchecked")
    public ItemChickenRecord() {
        super("chickenTechno");
        this.setMaxStackSize(1);
        this.setCreativeTab(CrystalBearers.modTab);
        this.setUnlocalizedName("Record");
        recordName = "chickenTechno";
        records.put(recordName, this);
    }

    @Override
    public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            if (world.getBlock(x, y, z).getUnlocalizedName().equals(Blocks.jukebox.getUnlocalizedName()) && world.getBlockMetadata(x, y, z) == 0) {
                ((BlockJukebox) Blocks.jukebox).func_149926_b(world, x, y, z, itemstack);
                world.playAuxSFXAtEntity((EntityPlayer) null, 1005, x, y, z, Item.getIdFromItem(this));
                --itemstack.stackSize;
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":musicDisc");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getRecordNameLocal() {
        return "This is not a easter egg";
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        par3List.add(this.getRecordNameLocal());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack par1ItemStack) {
        return EnumRarity.rare;
    }

    @Override
    public Item setUnlocalizedName(String str) {
        super.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":" + str);
        return this;
    }

    @SideOnly(Side.CLIENT)
    public static ItemChickenRecord getRecord(String par1) {
        return (ItemChickenRecord) records.get(par1);
    }

}
