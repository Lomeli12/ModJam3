package net.lomeli.cb.core.handler;

import java.util.Random;

import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.entities.EntityFireWolf;
import net.lomeli.cb.entities.EntityGhostPig;
import net.lomeli.cb.entities.EntityThunderCow;
import net.lomeli.cb.item.ModItems;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class EntityLivingHandler {
    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        if(!event.entityLiving.worldObj.isRemote) {
            if(event.entityLiving instanceof EntityDarkChicken) {
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY,
                        event.entityLiving.posZ, new ItemStack(ModItems.shard, event.entityLiving.worldObj.rand.nextInt(4), 4));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            }else if(event.entityLiving instanceof EntityThunderCow) {
                EntityLightningBolt bolt = new EntityLightningBolt(event.entityLiving.worldObj, event.entityLiving.posX,
                        event.entityLiving.posY, event.entityLiving.posZ);
                event.entityLiving.worldObj.spawnEntityInWorld(bolt);
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX + 3,
                        event.entityLiving.posY, event.entityLiving.posZ + 3, new ItemStack(ModItems.shard,
                                event.entityLiving.worldObj.rand.nextInt(4), 3));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            }else if(event.entityLiving instanceof EntityGhostPig) {
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY,
                        event.entityLiving.posZ, new ItemStack(ModItems.shard, event.entityLiving.worldObj.rand.nextInt(4), 2));
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            }else if(event.entityLiving instanceof EntityPig) {
                if(event.source.getSourceOfDamage() instanceof EntityPlayer) {
                    if(event.entityLiving.worldObj.rand.nextInt(100) < 10) {
                        EntityGhostPig ghost = new EntityGhostPig(event.entityLiving.worldObj);
                        ghost.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ,
                                event.entityLiving.worldObj.rand.nextFloat() * 360.0F, 0.0F);
                        event.entityLiving.worldObj.spawnEntityInWorld(ghost);

                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onSpawnEvent(LivingSpawnEvent event) {
        Random rand = new Random();
        if(event.entityLiving instanceof EntityFireWolf) {
            if(rand.nextInt(100) < 5)
                ((EntityLiving) event.entityLiving).setCustomNameTag("Firewolf20");
        }
    }
}
