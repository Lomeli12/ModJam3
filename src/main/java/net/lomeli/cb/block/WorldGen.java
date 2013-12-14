package net.lomeli.cb.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider) {
        if(world.provider.dimensionId == 0)
            generateOverworld(world, random, chunkX, chunkZ);
    }

    private void generateOverworld(World world, Random rand, int chunkX, int chunkZ) {
        int xCoord = chunkX + rand.nextInt(16);
        int zCoord = chunkZ + rand.nextInt(16);
        new WorldGenMinable(ModBlocks.crystal.blockID, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(256),
                zCoord);
        new WorldGenMinable(ModBlocks.oreShard.blockID, 0, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(40),
                zCoord);
        new WorldGenMinable(ModBlocks.oreShard.blockID, 1, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(40),
                zCoord);
        new WorldGenMinable(ModBlocks.oreShard.blockID, 2, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(40),
                zCoord);
        new WorldGenMinable(ModBlocks.oreShard.blockID, 3, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(40),
                zCoord);
        new WorldGenMinable(ModBlocks.oreShard.blockID, 4, 1, Block.stone.blockID).generate(world, rand, xCoord, rand.nextInt(40),
                zCoord);
    }

}
