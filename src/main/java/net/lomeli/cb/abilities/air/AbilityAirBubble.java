package net.lomeli.cb.abilities.air;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityAirBubble extends CrystalAbility {
    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;
        List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(radius, radius, radius));
        for(Object entityObj : entityList) {
            if(entityObj != null && entityObj instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) entityObj;
                if(entity.getAir() <= 0)
                    entity.setAir(20);
            }
        }
    }
}
