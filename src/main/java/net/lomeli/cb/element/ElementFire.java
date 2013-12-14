package net.lomeli.cb.element;

import net.minecraft.util.StatCollector;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.abilities.fire.AbilityBainOfIce;
import net.lomeli.cb.abilities.fire.AbilityBlockSmelter;
import net.lomeli.cb.abilities.fire.AbilityCombustable;
import net.lomeli.cb.abilities.fire.AbilityPowerFire;
import net.lomeli.cb.abilities.fire.AbilitySpontanousCombustion;
import net.lomeli.cb.abilities.fire.AbilityShardPowerFire;

public class ElementFire implements IElement {
    private int id;

    public ElementFire setID(int id) {
        this.id = id;
        return this;
    }

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
        return new AbilityShardPowerFire();
    }

    @Override
    public CrystalAbility recessiveNeutral() {
        return new AbilityPowerFire();
    }

    @Override
    public CrystalAbility dominantNegative() {
        return new AbilitySpontanousCombustion();
    }

    @Override
    public CrystalAbility recessiveNegative() {
        return new AbilityCombustable();
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
