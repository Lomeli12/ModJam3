package net.lomeli.cb.abilities;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.lomeli.cb.tile.ICrystal;

public class DebugAbility extends CrystalAbility {

    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEUTRAL;
    }

    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        TileEntity tile = worldObj.getBlockTileEntity(x, y, z);
        if(tile != null) {
            if(tile instanceof ICrystal) {
                ((ICrystal) tile).addPower(10000);
            }
        }
    }
    
    public String getAbilityName(){
        return "Power";
    }
    
    public String getAbilityDesc(){
        return "An unlimted power source (Naturally spawning crystals only)";
    }
}
