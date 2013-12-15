package net.lomeli.cb.abilities;

import java.util.Random;

import net.minecraft.world.World;

public class CrystalAbility {
    public static enum EnumAbilityType {
        POSITIVE, NEUTRAL, NEGATIVE;
    }

    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEUTRAL;
    }

    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {

    }
    
    public String getAbilityName(){
        return null;
    }
    
    public String getAbilityDesc(){
        return null;
    }

    public int cost() {
        return 10;
    }

}
