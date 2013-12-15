package net.lomeli.cb.core.handler;

import java.util.Random;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.EntityLiving;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.entities.EntityFireWolf;
import net.lomeli.cb.entities.EntityThunderCow;
import net.lomeli.cb.item.ModItems;

public class EntityLivingHandler {
    @ForgeSubscribe
    public void onEntityDeath(LivingDeathEvent event) {
        if(event.entityLiving instanceof EntityDarkChicken) {
            if(!event.entityLiving.worldObj.isRemote) {
                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY,
                        event.entityLiving.posZ, ModItems.shards[4]);
                event.entityLiving.worldObj.spawnEntityInWorld(item);
            }
        }else if(event.entityLiving instanceof EntityThunderCow) {
            if(!event.entityLiving.worldObj.isRemote) {

                EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX + 3,
                        event.entityLiving.posY, event.entityLiving.posZ + 3, ModItems.shards[3]);
                event.entityLiving.worldObj.spawnEntityInWorld(item);
                EntityLightningBolt bolt = new EntityLightningBolt(event.entityLiving.worldObj, event.entityLiving.posX,
                        event.entityLiving.posY, event.entityLiving.posZ);
                event.entityLiving.worldObj.spawnEntityInWorld(bolt);
            }
        }
    }

    @ForgeSubscribe
    public void onSpawnEvent(LivingSpawnEvent event) {
        Random rand = new Random();
        if(event.entityLiving instanceof EntityFireWolf) {
            if(rand.nextInt(100) < 5)
                ((EntityLiving) event.entityLiving).setCustomNameTag("Firewolf20");
        }
    }
}
