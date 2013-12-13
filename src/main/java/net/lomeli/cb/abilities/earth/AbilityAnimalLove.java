package net.lomeli.cb.abilities.earth;

import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityAnimalLove extends CrystalAbility {
    private Class[] entityClassList = { EntityPig.class, EntityHorse.class, EntityCow.class, EntityChicken.class,
            EntitySheep.class };

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        if(rand.nextInt(10000) < 150000) {
            System.out.println("Starting");
            Class<EntityLivingBase> entityClass = entityClassList[rand.nextInt(entityClassList.length)];
            try {
                System.out.println("Making entity");
                EntityLivingBase entity = (EntityLivingBase) entityClass.getConstructor(new Class[] { World.class }).newInstance(
                        new Object[] { worldObj });
                if(entity != null) {
                    entity.posX = x;
                    entity.posY = y;
                    entity.posZ = z;
                    System.out.println("Spawn");
                    worldObj.spawnEntityInWorld(entity);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
    }
}
