package net.lomeli.cb.abilities.fire;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

public class AbilityCombustable extends CrystalAbility {
    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1);
                    if(Block.lightValue[id] > 0.5F && rand.nextInt(1000) < 405)
                        worldObj.createExplosion(null, x1, y1, z1, 5F, true);
                }
    }
    
    @Override
    public int cost() {
        return 200;
    }
    
    @Override
    public String getAbilityName() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":"
                + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }
}
