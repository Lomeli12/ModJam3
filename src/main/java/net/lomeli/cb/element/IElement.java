package net.lomeli.cb.element;

import net.lomeli.cb.abilities.CrystalAbility;

public interface IElement {
    public abstract String getLocalizedName();

    public abstract CrystalAbility dominantPositive();

    public abstract CrystalAbility recessivePositive();

    public abstract CrystalAbility dominantNeutral();

    public abstract CrystalAbility recessiveNeutral();

    public abstract CrystalAbility dominantNegative();

    public abstract CrystalAbility recessiveNegative();
    
    public abstract int getElementID();
    
    public abstract CrystalAbility[] abilities();
}
