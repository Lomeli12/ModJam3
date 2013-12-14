package net.lomeli.cb.item;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;

public interface IShard {
    public abstract Fluid getFluid(ItemStack stack);
}
