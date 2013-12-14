package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.earth.AbilityAnimalLove;
import net.lomeli.cb.abilities.earth.AbilityNatureDevourer;
import net.lomeli.cb.abilities.earth.AbilityNatureTouch;
import net.lomeli.cb.abilities.earth.AbilityPowerEarth;
import net.lomeli.cb.abilities.earth.AbilityTerrainDesecrator;
import net.lomeli.cb.abilities.earth.AbilityShardPowerEarth;

public class ElementEarth implements IElement {
    private int id;

    public ElementEarth setID(int id) {
        this.id = id;
        return this;
    }

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
        return new AbilityShardPowerEarth();
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return new AbilityPowerEarth();
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

    @Override
    public int getElementID() {
        return id;
    }

    @Override
    public CrystalAbility[] abilities() {
        return new CrystalAbility[] { dominantPositive(), recessivePositive(), dominantNegative(), recessiveNegative() };
    }
}
