package net.lomeli.cb.element;

import java.util.HashMap;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import net.lomeli.cb.item.IShard;
import net.lomeli.cb.item.ModItems;

public class FluidElements {
    public static Fluid fireFluid, waterFluid, earthFluid, airFluid, darkFluid;
    private static HashMap<Fluid, IElement> fluidElementList;
    private static HashMap<ItemStack, Fluid> shardList;

    public static void loadFluids() {
        fluidElementList = new HashMap<Fluid, IElement>();
        shardList = new HashMap<ItemStack, Fluid>();

        fireFluid = new Fluid("liquidFireCrystal");// .setIcons(ModBlocks.shardBlock.getIcon(0,
                                                   // 0));
        waterFluid = new Fluid("liquidWaterCrystal");// .setIcons(ModBlocks.shardBlock.getIcon(0,
                                                     // 1));
        earthFluid = new Fluid("liquidEarthCrystal");// .setIcons(ModBlocks.shardBlock.getIcon(0,
                                                     // 2));
        airFluid = new Fluid("liquidAirCrystal");// .setIcons(ModBlocks.shardBlock.getIcon(0,
                                                 // 3));
        darkFluid = new Fluid("liquidDarkCrystal");// .setIcons(ModBlocks.shardBlock.getIcon(0,
                                                   // 4));

        FluidRegistry.registerFluid(fireFluid);
        FluidRegistry.registerFluid(waterFluid);
        FluidRegistry.registerFluid(earthFluid);
        FluidRegistry.registerFluid(airFluid);
        FluidRegistry.registerFluid(darkFluid);

        fluidElementList.put(fireFluid, ElementRegistry.fire);
        fluidElementList.put(waterFluid, ElementRegistry.water);
        fluidElementList.put(earthFluid, ElementRegistry.earth);
        fluidElementList.put(airFluid, ElementRegistry.air);
        fluidElementList.put(darkFluid, ElementRegistry.dark);

        shardList.put(ModItems.shards[0], fireFluid);
        shardList.put(ModItems.shards[1], waterFluid);
        shardList.put(ModItems.shards[2], earthFluid);
        shardList.put(ModItems.shards[3], airFluid);
        shardList.put(ModItems.shards[4], darkFluid);
    }

    public static IElement getFluidElement(Fluid fluid) {
        return fluid != null ? fluidElementList.get(fluid) : null;
    }

    public static Fluid getFluidBaseOnStack(ItemStack stack) {
        if(stack != null && stack.getItem() instanceof IShard)
            return ((IShard) stack.getItem()).getFluid(stack);
        return null;
    }
}
