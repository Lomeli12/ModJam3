package net.lomeli.cb.abilities.water;

import java.util.List;
import java.util.Random;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityMorphaReach extends CrystalAbility {

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
                EntityLivingBase entity = (EntityLivingBase)entityObj;
                boolean flag = entity instanceof EntityPlayer && ((EntityPlayer) entity).capabilities.disableDamage;
                if(!entity.canBreatheUnderwater() && entity.isPotionActive(Potion.waterBreathing.id) && !flag) {
                    entity.setAir(decreaseAirSupply(entity.getAir(), entity, rand));

                    if(entity.getAir() == -20) {
                        entity.setAir(0);

                        for(int i = 0; i < 8; ++i) {
                            float f = rand.nextFloat() - rand.nextFloat();
                            float f1 = rand.nextFloat() - rand.nextFloat();
                            float f2 = rand.nextFloat() - rand.nextFloat();
                            worldObj.spawnParticle("bubble", entity.posX + (double) f, entity.posY + (double) f1, entity.posZ
                                    + (double) f2, entity.motionX, entity.motionY, entity.motionZ);
                        }

                        entity.attackEntityFrom(DamageSource.drown, 2.0F);
                    }
                }
            }
        }
    }

    private int decreaseAirSupply(int par1, EntityLivingBase entity, Random rand) {
        int j = EnchantmentHelper.getRespiration(entity);
        return j > 0 && rand.nextInt(j + 1) > 0 ? par1 : par1 - 1;
    }
}
