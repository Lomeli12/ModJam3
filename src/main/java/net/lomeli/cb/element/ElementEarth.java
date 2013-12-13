package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.earth.AbilityAnimalLove;
import net.lomeli.cb.abilities.earth.AbilityNatureDevourer;
import net.lomeli.cb.abilities.earth.AbilityNatureTouch;
import net.lomeli.cb.abilities.earth.AbilityTerrainDesecrator;

public class ElementEarth implements IElement{

    @Override
    public CrystalAbility dominantPositive() {
        return new AbilityNatureTouch();
    }

    @Override
    public CrystalAbility recessivePositive() {
        return new AbilityAnimalLove();
    }

    @Override
    public CrystalAbility dominantNeutral() {
        return null;
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return null;
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilityNatureDevourer();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityTerrainDesecrator();
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("element.crystalbearers:earthElement");
    }

}
