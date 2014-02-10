package net.lomeli.cb.abilities.dark;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public class AbilityEnrichedEarth extends CrystalAbility {
    private List<Block> blockList;

    public AbilityEnrichedEarth() {
        this.blockList = new ArrayList<Block>();
        this.blockList.add(Blocks.coal_ore);
        this.blockList.add(Blocks.iron_ore);
        this.blockList.add(Blocks.lapis_ore);
        this.blockList.add(Blocks.redstone_ore);

        this.blockList.add(Blocks.diamond_ore);
        this.blockList.add(Blocks.emerald_ore);
        this.blockList.add(Blocks.gold_ore);
    }

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.POSITIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 30;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    if (rand.nextInt(10000000) < 2) {
                        if (id.getUnlocalizedName().equals(Blocks.stone)) {
                            if (rand.nextInt(2000) < 10)
                                worldObj.setBlock(x1, y1, z1, this.blockList.get(rand.nextInt(this.blockList.size())));
                            else
                                worldObj.setBlock(x1, y1, z1, this.blockList.get(rand.nextInt(4)));
                        }
                    }
                }
    }

    @Override
    public int cost() {
        return 400;
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
