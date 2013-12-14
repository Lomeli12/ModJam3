package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.fire.AbilityBainOfIce;
import net.lomeli.cb.abilities.fire.AbilityBlockSmelter;
import net.lomeli.cb.abilities.fire.AbilityCombustable;
import net.lomeli.cb.abilities.fire.AbilitySpontanousCombustion;

public class ElementFire implements IElement {

    @Override
    public String getLocalizedName() {
        return StatCollector.translateToLocal("element.crystalbearers:fireElement");
    }

    @Override
    public CrystalAbility dominantPositive() {
        return new AbilityBainOfIce();
    }

    @Override
    public CrystalAbility recessivePositive() {
        return new AbilityBlockSmelter();
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
        return new AbilitySpontanousCombustion();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityCombustable();
    }

}
