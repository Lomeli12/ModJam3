package net.lomeli.cb.item;

import net.lomeli.cb.block.ModBlocks;
import net.lomeli.cb.element.ElementRegistry;
import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystal;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemCrystal extends ItemCB {
    private Block block;

    public ItemCrystal() {
        super("crystal");
        this.block = ModBlocks.crystal;
        this.setMaxStackSize(1);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ":crystal");
    }

    @Override
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
            int par6, int par7, float par8, float par9, float par10) {
        Block block = par3World.getBlock(par4, par5, par6);

        if((block == Blocks.snow_layer) && ((par3World.getBlockMetadata(par4, par5, par6) & 0x7) < 1))
            par7 = 1;
        else if((block != Blocks.vine) && (block != Blocks.tallgrass) && (block != Blocks.deadbush)
                && (!block.isReplaceable(par3World, par4, par5, par6))) {
            if(par7 == 0)
                par5--;

            if(par7 == 1)
                par5++;

            if(par7 == 2)
                par6--;

            if(par7 == 3)
                par6++;

            if(par7 == 4)
                par4--;

            if(par7 == 5)
                par4++;
        }

        if(par1ItemStack.stackSize == 0)
            return false;
        
        if(!par2EntityPlayer.canPlayerEdit(par4, par5, par6, par7, par1ItemStack))
            return false;

        if (block == null)
            return false;
        
        if((par5 == 255) && (block.getMaterial().isSolid()))
            return false;
        
        if(par3World.canPlaceEntityOnSide(block, par4, par5, par6, false, par7, par2EntityPlayer, par1ItemStack)) {
            int i1 = getMetadata(par1ItemStack.getItemDamage());
            int j1 = block.onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, i1);

            if(placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, j1)) {
                par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, block.stepSound.func_150496_b(),
                        (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                par1ItemStack.stackSize -= 1;
            }

            return true;
        }

        return false;
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX,
            float hitY, float hitZ, int metadata) {
        if (block == null)
            return false;
        
        if(!world.setBlock(x, y, z, block, metadata, 3))
            return false;

        if(world.getBlock(x, y, z) == block) {
            block.onBlockPlacedBy(world, x, y, z, player, stack);
            block.onPostBlockPlaced(world, x, y, z, metadata);
        }

        TileEntity tile = world.getTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof TileCrystal) {
                if(stack.getTagCompound() != null) {
                    ((TileCrystal) tile).setAbilityOne(ElementRegistry.getAbilityOne(stack));
                    ((TileCrystal) tile).setAbilityTwo(ElementRegistry.getAbilityTwo(stack));
                    ((TileCrystal) tile).setPowerAbility(ElementRegistry.getPower(stack));
                    ((TileCrystal) tile).abilitiesSet = true;
                    ((TileCrystal) tile).firstEle = stack.getTagCompound().getInteger("element1");
                    ((TileCrystal) tile).secondEle = stack.getTagCompound().getInteger("element2");
                    ((TileCrystal) tile).thridEle = stack.getTagCompound().getInteger("powerElement");
                    ((TileCrystal) tile).ability1ID = stack.getTagCompound().getInteger("element1Ability");
                    ((TileCrystal) tile).ability2ID = stack.getTagCompound().getInteger("element2Ability");
                    ((TileCrystal) tile).passiveAbility = stack.getTagCompound().getBoolean("powerElementAbility");
                    ((TileCrystal) tile).setIsNatural(false);
                }
            }
        }

        return true;
    }

}
