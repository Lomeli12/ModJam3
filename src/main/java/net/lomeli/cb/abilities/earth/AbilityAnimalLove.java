package net.lomeli.cb.abilities.earth;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityAnimalLove extends CrystalAbility{

    @Override
    public EnumAbilityType abilityType(){
        return EnumAbilityType.POSITIVE;
    }
    
    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand){
        if(rand.nextInt(10000) < 150){
            List<?> spawnList = worldObj.getChunkProvider().getPossibleCreatures(EnumCreatureType.creature, x, y, z);
            for(Object entity : spawnList){
                if(entity != null) {
                    if(entity instanceof EntityLivingBase){
                        EntityLivingBase newAnimal = ((EntityLivingBase)entity);
                        worldObj.spawnEntityInWorld(newAnimal);
                    }
                }
            }
        }
    }
}
