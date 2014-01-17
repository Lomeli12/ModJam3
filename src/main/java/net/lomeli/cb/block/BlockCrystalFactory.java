package net.lomeli.cb.block;

import java.util.Random;

import net.lomeli.cb.lib.Strings;
import net.lomeli.cb.tile.TileCrystalFactory;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystalFactory extends BlockCB implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;

    public BlockCrystalFactory(int par1) {
        super(par1, Material.rock, "crystalFactory_");
        setUnlocalizedName("factory");
        setHardness(2.0F);
        setResistance(10.0F);
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        iconArray = new Icon[5];
        for (int i = 0; i < iconArray.length; i++) {
            iconArray[i] = par1IconRegister.registerIcon(Strings.MOD_ID.toLowerCase() + ":" + this.blockTexture + i);
        }
    }

    @Override
    public Icon getIcon(int par1, int par2) {
        if (par2 == 6) {
            if (par1 < 2)
                return this.iconArray[3];
        } else if (par2 == 3) {
            if (par1 < 2)
                return this.iconArray[5];
        }
        return this.iconArray[par2];
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                TileCrystalFactory tile = (TileCrystalFactory) world.getBlockTileEntity(x, y, z);
                if (tile != null) {
                    if (tile.hasMaster()) {
                        if (tile.isMaster()) {
                            player.addChatMessage("I am the master!");
                        } else {
                            player.addChatMessage("I'm a servant block! My master is at (" + tile.getMasterX() + ", " + tile.getMasterY() + ", " + tile.getMasterZ() + ")");
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileCrystalFactory();
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int id, int meta) {
        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, id, meta);
    }

    private Random rand = new Random();

    private void dropInventory(World world, int x, int y, int z) {

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
            return;

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++) {

            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0) {
                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, new ItemStack(itemStack.itemID, itemStack.stackSize, itemStack.getItemDamage()));

                if (itemStack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}
