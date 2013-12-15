package net.lomeli.cb.core.handler;

import java.util.Random;

import net.minecraft.entity.item.EntityItem;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.lomeli.cb.entities.EntityDarkChicken;
import net.lomeli.cb.item.ModItems;

public class EntityDropHandler {
    @ForgeSubscribe
    public void onEntityDeath(LivingDeathEvent event) {
        Random rand = new Random();
        if(event.entityLiving instanceof EntityDarkChicken) {
            if(rand.nextInt(100) < 14) {
                if(!event.entityLiving.worldObj.isRemote) {
                    EntityItem item = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX,
                            event.entityLiving.posY, event.entityLiving.posZ, ModItems.shards[4]);
                    event.entityLiving.worldObj.spawnEntityInWorld(item);
                }
            }
        }
    }
}
