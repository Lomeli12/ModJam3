package net.lomeli.cb.abilities.earth;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnListEntry;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

public class AbilityAnimalLove extends CrystalAbility {

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        if(rand.nextInt(10000) < 150000) {
            List<?> list = worldObj.getChunkProvider().getPossibleCreatures(EnumCreatureType.creature, x, y, z);
            if(list.size() > 0) {
                try {

                    EntityLivingBase entity = (EntityLivingBase) ((SpawnListEntry) list.get(rand.nextInt(list.size()))).entityClass
                            .getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldObj });
                    if(entity != null) {
                        if(rand.nextInt(10000) < 10) {
                            entity.setLocationAndAngles(x, y + 1, z, rand.nextFloat() * 360.0F, 0.0F);
                            worldObj.spawnEntityInWorld(entity);
                        }
                    }
                }catch(Exception e) {
                }
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
