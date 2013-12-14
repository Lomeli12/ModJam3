package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.air.AbilityAirBubble;
import net.lomeli.cb.abilities.air.AbilityFalless;
import net.lomeli.cb.abilities.air.AbilityRagingWinds;
import net.lomeli.cb.abilities.air.AbilityToxicAir;

public class ElementAir implements IElement {

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("element.crystalbearers:airElement");
    }

    @Override
    public CrystalAbility dominantPositive() {
        return new AbilityAirBubble();
    }

    @Override
    public CrystalAbility recessivePositive() {
        return new AbilityFalless();
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
        return new AbilityRagingWinds();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityToxicAir();
    }

}
