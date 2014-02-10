package net.lomeli.cb.block;

import java.util.List;
import java.util.Random;

import net.lomeli.cb.CrystalBearers;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockDarkStone extends BlockCB {

    public BlockDarkStone(String texture) {
        super(Material.rock, texture);
        this.setTickRandomly(true);
        this.setCreativeTab(CrystalBearers.modTab);
        this.setHardness(2.0F);
        this.setResistance(10.0F);
        this.setStepSound(soundTypeStone);
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        if (!world.isRemote && world.difficultySetting.getDifficultyId() > 0) {
            List<?> list = world.getChunkProvider().getPossibleCreatures(EnumCreatureType.monster, x, y, z);
            if (list.size() > 0) {
                try {
                    EntityLivingBase entity = (EntityLivingBase) list.get(list.size());
                    if (entity != null) {
                        if (world.isAirBlock(x, y + 1, z) && world.isAirBlock(x, y + 2, z) && rand.nextInt(1000) < 45) {
                            entity.setLocationAndAngles(x + 0.5, y + 1, z + 0.5, rand.nextFloat() * 360.0F, 0.0F);
                            world.spawnEntityInWorld(entity);
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

}
