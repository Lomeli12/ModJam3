package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.core.ModUtil;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import net.minecraftforge.common.IPlantable;

public class AbilityMoistSoil extends CrystalAbility {
    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 8;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    if (rand.nextBoolean()) {
                        if (ModUtil.areBlocksTheSame(id, Blocks.farmland))
                            worldObj.setBlockMetadataWithNotify(x1, y1, z1, 7, 2);
                        else if (id instanceof IPlantable)
                            id.updateTick(worldObj, x1, y1, z1, rand);
                    }
                }
    }

    @Override
    public int cost() {
        return 40;
    }

    @Override
    public String getAbilityName() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length()) + "Name";
    }

    @Override
    public String getAbilityDesc() {
        return "ability." + Strings.MOD_ID.toLowerCase() + ":" + this.getClass().getSimpleName().substring(7, this.getClass().getSimpleName().length());
    }
}
