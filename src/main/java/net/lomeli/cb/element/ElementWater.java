package net.lomeli.cb.element;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.water.AbilityDrenchedEarth;
import net.lomeli.cb.abilities.water.AbilityMoistSoil;
import net.lomeli.cb.abilities.water.AbilityMorphaReach;
import net.lomeli.cb.abilities.water.AbilityPowerWater;
import net.lomeli.cb.abilities.water.AbilityReplenishWater;
import net.lomeli.cb.abilities.water.AbilityShardPowerWater;

import net.minecraft.util.StatCollector;

public class ElementWater implements IElement {
    private int id;

    public ElementWater setID(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("element.crystalbearers:waterElement");
    }

    @Override
    public CrystalAbility dominantPositive() {
        return new AbilityReplenishWater();
    }

    @Override
    public CrystalAbility recessivePositive() {
        return new AbilityMoistSoil();
    }

    @Override
    public CrystalAbility dominantNeutral() {
        return new AbilityShardPowerWater();
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return new AbilityPowerWater();
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilityDrenchedEarth();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityMorphaReach();
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
