package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityReplenishWater extends CrystalAbility {

    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    TileEntity tile = worldObj.getBlockTileEntity(x1, y1, z1);
                    if(tile instanceof IFluidHandler) {
                        if(((IFluidHandler) tile).canFill(null, FluidRegistry.WATER))
                            ((IFluidHandler) tile).fill(null, new FluidStack(FluidRegistry.WATER, 500), true);
                    }
                }

    }
}
