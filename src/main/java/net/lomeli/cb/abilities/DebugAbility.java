package net.lomeli.cb.abilities;

import java.util.Random;

import net.lomeli.cb.tile.ICrystal;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class DebugAbility extends CrystalAbility {

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEUTRAL;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getTileEntity(x, y, z);
        if (tile != null) {
            if (tile instanceof ICrystal) {
                ((ICrystal) tile).addPower(10000);
            }
        }
    }

    @Override
    public String getAbilityName() {
        return "Power";
    }

    @Override
    public String getAbilityDesc() {
        return "An unlimted power source (Naturally spawning crystals only)";
    }
}
