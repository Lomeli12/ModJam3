package net.lomeli.cb.element;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.dark.AbilityEnrichedEarth;
import net.lomeli.cb.abilities.dark.AbilityMagnetism;
import net.lomeli.cb.abilities.dark.AbilityOreater;
import net.lomeli.cb.abilities.dark.AbilityPowerDark;
import net.lomeli.cb.abilities.dark.AbilityShardPowerDark;
import net.lomeli.cb.abilities.dark.AbilitySpreadDark;

import net.minecraft.util.StatCollector;

public class ElementDark implements IElement {

    private int id;

    public ElementDark setID(int id) {
        this.id = id;
        return this;
    }

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
        return new AbilityShardPowerDark();
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return new AbilityPowerDark();
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilityOreater();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilitySpreadDark();
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
