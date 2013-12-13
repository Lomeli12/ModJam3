package net.lomeli.cb.abilities.fire;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;

import net.lomeli.cb.abilities.CrystalAbility;

public class AbilityBlockSmelter extends CrystalAbility {
    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 5;

        for(int x1 = x - radius; x1 <= x + 5; x1++)
            for(int y1 = y - radius; y1 <= y + 5; y1++)
                for(int z1 = z - radius; z1 <= z + 5; z1++) {
                    int id = worldObj.getBlockId(x1, y1, z1), meta = worldObj.getBlockMetadata(x1, y1, z1);
                    if(FurnaceRecipes.smelting().getMetaSmeltingList().containsKey(Arrays.asList(id, meta)) && rand.nextBoolean()) {
                        ItemStack result = FurnaceRecipes.smelting().getSmeltingResult(new ItemStack(id, 1, meta));
                        if(result != null)
                            worldObj.setBlock(x1, y1, z1, result.itemID, result.getItemDamage(), 2);
                    }
                }
    }
}
