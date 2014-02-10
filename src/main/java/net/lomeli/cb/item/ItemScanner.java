package net.lomeli.cb.item;

import java.util.List;

import net.lomeli.cb.CrystalBearers;
import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.ICrystal;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemScanner extends ItemCB {
    @SideOnly(Side.CLIENT)
    private IIcon[] iconArray;

    public ItemScanner() {
        super("scanner_");
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":scanner");
        this.setMaxStackSize(1);
        this.setHasSubtypes(true);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if(player != null) {
            player.openGui(CrystalBearers.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return itemStack;
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8,
            float par9, float par10) {
        if(!world.isRemote) {
            if(itemStack.getItemDamage() == 0) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if(tile != null) {
                    if(tile instanceof ICrystal) {
                        CrystalAbility[] abilities = new CrystalAbility[] { ((ICrystal) tile).abilityOne(),
                                ((ICrystal) tile).abilityTwo(), ((ICrystal) tile).powerAbility() };
                        if(abilities.length > 0) {
                            for(CrystalAbility ability : abilities) {
                                if(ability != null) {
                                    player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(ability
                                            .getAbilityName())));
                                    player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal(ability
                                            .getAbilityDesc())));
                                }
                            }
                        }
                    }
                }
            }else if(itemStack.getItemDamage() == 1) {
                player.openGui(CrystalBearers.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister par1IconRegister) {
        iconArray = new IIcon[2];
        for(int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.itemTexture + i);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIconFromDamage(int meta) {
        return meta < iconArray.length ? iconArray[meta] : iconArray[0];
    }

    @Override
    public int getMetadata(int meta) {
        return meta;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for(int i = 0; i < 2; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.getUnlocalizedName() + "." + stack.getItemDamage();
    }
}
