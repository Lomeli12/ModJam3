package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.water.AbilityDrenchedEarth;
import net.lomeli.cb.abilities.water.AbilityMoistSoil;
import net.lomeli.cb.abilities.water.AbilityMorphaReach;
import net.lomeli.cb.abilities.water.AbilityReplenishWater;

public class ElementWater implements IElement {

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
        return null;
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return null;
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilityDrenchedEarth();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityMorphaReach();
    }

}
