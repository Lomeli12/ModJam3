package net.lomeli.cb.tile;

import net.lomeli.cb.abilities.CrystalAbility;

public interface ICrystal {
    public abstract CrystalAbility abilityOne();
    
    public abstract CrystalAbility abilityTwo();
    
    public abstract CrystalAbility powerAbility();
    
    public abstract boolean canActivate();
    
    public abstract int getPower();
    
    public abstract int getMaxPower();
    
    public abstract void addPower(int power);
    
    public abstract void usePower(int power);
    
    public abstract void setAbilityOne(CrystalAbility ability);
    
    public abstract void setAbilityTwo(CrystalAbility ability);
    
    public abstract void setPowerAbility(CrystalAbility ability);
}
