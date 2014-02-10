package net.lomeli.cb.abilities.water;

import java.util.Random;

import net.lomeli.cb.abilities.CrystalAbility;
import net.lomeli.cb.core.ModUtil;
import net.lomeli.cb.lib.Strings;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

public class AbilityDrenchedEarth extends CrystalAbility {

    @Override
    public EnumAbilityType abilityType() {
        return EnumAbilityType.NEGATIVE;
    }

    @Override
    public void enviromentalEffect(World worldObj, int x, int y, int z, Random rand) {
        int radius = 10;

        for (int x1 = x - radius; x1 <= x + 5; x1++)
            for (int y1 = y - radius; y1 <= y + 5; y1++)
                for (int z1 = z - radius; z1 <= z + 5; z1++) {
                    Block id = worldObj.getBlock(x1, y1, z1);
                    TileEntity tile = worldObj.getTileEntity(x1, y1, z1);
                    if (tile instanceof TileEntityFurnace) {
                        try {
                            int burnTime = TileEntityFurnace.class.getDeclaredField("furnaceBurnTime").getInt(tile);
                            if (burnTime > 0 && ModUtil.areBlocksTheSame(id, Blocks.furnace)) {
                                int meta = worldObj.getBlockMetadata(x1, y1, z1);
                                TileEntityFurnace.class.getDeclaredField("furnaceBurnTime").set(tile, 0);
                                worldObj.setBlock(x1, y1, z1, Blocks.furnace, meta, 2);
                            }
                        } catch (Exception e) {

                        }
                    } else if (!ModUtil.areBlocksTheSame(id, Blocks.glowstone)) {
                        if (id.getLightValue() > 0.5F) {
                            if (!ModUtil.areBlocksTheSame(id, Blocks.flowing_lava) || !ModUtil.areBlocksTheSame(id, Blocks.lava)) {
                                EntityItem drop = new EntityItem(worldObj, x1, y1, z1, new ItemStack(id, 1, worldObj.getBlockMetadata(x1, y1, z1)));
                                if (drop != null)
                                    worldObj.spawnEntityInWorld(drop);
                                worldObj.setBlockToAir(x1, y1, z1);
                            }
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
