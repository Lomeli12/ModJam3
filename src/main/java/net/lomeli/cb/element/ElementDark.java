package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.dark.*;

public class ElementDark implements IElement{

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("element.crystalbearers:darkElement");
    }

    @Override
    public CrystalAbility dominantPositive() {
        return new AbilityMagnetism();
    }

    @Override
    public CrystalAbility recessivePositive() {
        return new AbilityEnrichedEarth();
    }

    @Override
    public CrystalAbility dominantNeutral() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilityOreater();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilitySpeardDark();
    }

}