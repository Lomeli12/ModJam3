package net.lomeli.cb.core.handler;

import java.util.Random;

import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.entities.EntityFireWolf;
import net.lomeli.cb.entities.EntityGhostPig;
import net.lomeli.cb.entities.EntityThunderCow;
import net.lomeli.cb.item.IShard;
import net.lomeli.cb.item.ModItems;
import net.lomeli.cb.lib.PageInfo;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

public class EntityLivingHandler {
    @ForgeSubscribe
    public void onEntityDeath(LivingDeathEvent event) {
        if (!event.entityLiving.worldObj.isRemote) {
            if (event.entityLiving instanceof EntityDarkChicken) {
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(
                        ModItems.shard, event.entityLiving.worldObj.rand.nextInt(4), 4));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            } else if (event.entityLiving instanceof EntityThunderCow) {
                EntityLightningBolt bolt = new EntityLightningBolt(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ);
                event.entityLiving.worldObj.spawnEntityInWorld(bolt);
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX + 3, event.entityLiving.posY, event.entityLiving.posZ + 3, new ItemStack(
                        ModItems.shard, event.entityLiving.worldObj.rand.nextInt(4), 3));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            } else if (event.entityLiving instanceof EntityGhostPig) {
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, new ItemStack(
                        ModItems.shard, event.entityLiving.worldObj.rand.nextInt(4), 2));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            } else if (event.entityLiving instanceof EntityPig) {
                if (event.source.getSourceOfDamage() instanceof EntityPlayer) {
                    if (event.entityLiving.worldObj.rand.nextInt(100) < 10) {
                        EntityGhostPig ghost = new EntityGhostPig(event.entityLiving.worldObj);
                        ghost.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ,
                                event.entityLiving.worldObj.rand.nextFloat() * 360.0F, 0.0F);
                        event.entityLiving.worldObj.spawnEntityInWorld(ghost);

                    }
                }
            }
        }
    }

    @ForgeSubscribe
    public void itemPickUpEvent(EntityItemPickupEvent event) {
        if (event.item != null && event.item.getEntityItem() != null && event.entityPlayer != null) {
            EntityPlayer player = event.entityPlayer;
            ItemStack item = event.item.getEntityItem();
            if (item.getUnlocalizedName().equals(ModItems.crystalItem.getUnlocalizedName())) {
                if (!player.getEntityData().getBoolean(PageInfo.advCrystalTag) && player.getEntityData().getBoolean(PageInfo.crystalTag))
                    PacketHandler.sendPlayerDiscoveryPacket(PageInfo.advCrystalTag, (EntityPlayerMP) player);
            } else if (item.getItem() instanceof IShard) {
                if (!player.getEntityData().getBoolean(PageInfo.crystalTag))
                    PacketHandler.sendPlayerDiscoveryPacket(PageInfo.crystalTag, (EntityPlayerMP) player);
            }
        }
    }

    @ForgeSubscribe
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            if (!player.getEntityData().getBoolean(PageInfo.book)) {
                player.inventory.addItemStackToInventory(new ItemStack(ModItems.scanner, 1, 1));
                player.getEntityData().setBoolean(PageInfo.book, true);
            }
        }
    }

    @ForgeSubscribe
    public void onSpawnEvent(LivingSpawnEvent event) {
        Random rand = new Random();
        if (event.entityLiving instanceof EntityFireWolf) {
            if (rand.nextInt(100) < 5)
                ((EntityLiving) event.entityLiving).setCustomNameTag("Firewolf20");
        }
    }
}
