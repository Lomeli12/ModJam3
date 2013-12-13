package net.lomeli.cb.abilities.fire;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

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
}
