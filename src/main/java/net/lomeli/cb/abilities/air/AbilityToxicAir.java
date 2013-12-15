package net.lomeli.cb.abilities.air;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

public class AbilityToxicAir extends CrystalAbility {
    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;
        List<?> entityList = worldObj.getEntitiesWithinAABB(EntityLivingBase.class,
                AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1).expand(radius, radius, radius));
        for(Object entityObj : entityList) {
            if(entityObj != null && entityObj instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) entityObj;
                if(rand.nextInt(1000) < 405)
                    entity.addPotionEffect(new PotionEffect(Potion.poison.id, 5000, 2));
            }
        }
    }
    
    @Override
    public int cost() {
        return 400;
    }
    
    @Override
    public String getAbilityName() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }

}
