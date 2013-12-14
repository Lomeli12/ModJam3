package net.lomeli.cb.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.DebugAbility;
import net.lomeli.cb.abilities.dark.AbilityMagnetism;
import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.ICrystal;

public class ItemDebugTool extends Item {

    private int blockID;
    public ItemDebugTool(int par1) {
        super(par1);
        this.setCreativeTab(CreativeTabs.tabTools);
        this.blockID = ModBlocks.crystal.blockID;
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":itemDebug");
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8,
            float par9, float par10) {
        boolean bool = useItem(itemStack, player, world, x, y, z, par7, par8, par9, par10);

        TileEntity tile = world.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof ICrystal) {
                ((ICrystal) tile).setAbilityOne(ElementRegistry.fire.recessiveNegative());
                ((ICrystal) tile).setAbilityTwo(ElementRegistry.air.recessivePositive());
                ((ICrystal) tile).setPowerAbility(new DebugAbility());
            }
        }

        return bool;
    }

    public boolean useItem(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6,
            int par7, float par8, float par9, float par10) {
        int i1 = par3World.getBlockId(par4, par5, par6);

        if(i1 == Block.snow.blockID && (par3World.getBlockMetadata(par4, par5, par6) & 7) < 1) {
            par7 = 1;
        }else if(i1 != Block.vine.blockID && i1 != Block.tallGrass.blockID && i1 != Block.deadBush.blockID
                && (Block.blocksList[i1] == null || !Block.blocksList[i1].isBlockReplaceable(par3World, par4, par5, par6))) {
            if(par7 == 0) {
                --par5;
            }

            if(par7 == 1) {
                ++par5;
            }

            if(par7 == 2) {
                --par6;
            }

            if(par7 == 3) {
                ++par6;
            }

            if(par7 == 4) {
                --par4;
            }

            if(par7 == 5) {
                ++par4;
            }
        }

        if(par1ItemStack.stackSize == 0) {
            return false;
        }else if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack)) {
            return false;
        }else if(par5 == 255 && Block.blocksList[this.blockID].blockMaterial.isSolid()) {
            return false;
        }else if(par3World.canPlaceEntityOnSide(this.blockID, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
            Block block = Block.blocksList[this.blockID];
            int j1 = this.getMetadata(par1ItemStack.getItemDamage());
            int k1 = Block.blocksList[this.blockID].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, j1);

            if(placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, k1)) {
                par3World.playSoundEffect((double) ((float) par4 + 0.5F), (double) ((float) par5 + 0.5F),
                        (double) ((float) par6 + 0.5F), block.stepSound.getPlaceSound(),
                        (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                --par1ItemStack.stackSize;
            }

            return true;
        }else {
            return false;
        }
    }
}
