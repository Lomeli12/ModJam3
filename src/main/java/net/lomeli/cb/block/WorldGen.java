package net.lomeli.cb.block;

import java.util.LinkedList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {

    public class OreData {
        public int maxHeight, minHeight, maxCluster, minCluster, clusterPerChunk, oreType, meta;

        public OreData(int maxHeight, int minHeight, int maxCluster, int minCluster, int clusterPerChunk, int oreType, int meta) {
            this.maxHeight = maxHeight;
            this.minHeight = minHeight;
            this.maxCluster = maxCluster;
            this.minCluster = minCluster;
            this.clusterPerChunk = clusterPerChunk;
            this.oreType = oreType;
            this.meta = meta;
        }

        public OreData(int maxHeight, int minHeight, int maxCluster, int minCluster, int clusterPerChunk, int oreType) {
            this.maxHeight = maxHeight;
            this.minHeight = minHeight;
            this.maxCluster = maxCluster;
            this.minCluster = minCluster;
            this.clusterPerChunk = clusterPerChunk;
            this.oreType = oreType;
            this.meta = 0;
        }
    }

    private LinkedList<OreData> oreList;

    public WorldGen() {
        oreList = new LinkedList<OreData>();
        OreData data;

        data = new OreData(255, 1, 1, 1, 2, ModBlocks.crystal.blockID);
        oreList.add(data);
        data = new OreData(255, 1, 1, 1, 5, ModBlocks.oreShard.blockID, 0);
        oreList.add(data);
        data = new OreData(255, 1, 1, 1, 5, ModBlocks.oreShard.blockID, 1);
        oreList.add(data);
        data = new OreData(255, 1, 1, 1, 5, ModBlocks.oreShard.blockID, 2);
        oreList.add(data);
        data = new OreData(255, 1, 1, 1, 5, ModBlocks.oreShard.blockID, 3);
        oreList.add(data);
        data = new OreData(255, 1, 1, 1, 5, ModBlocks.oreShard.blockID, 4);
        oreList.add(data);
    }

    @SuppressWarnings("unused")
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider) {
        int x, y, z;
        int numOre;
        int numCluster;
        int tcount = 0;
        if(world.provider.dimensionId == 0) {
            for(OreData data : oreList) {
                tcount++;

                numCluster = random.nextInt(data.clusterPerChunk);
                if(numCluster == 0 && data.clusterPerChunk != 0)
                    numCluster = 1;

                for(int count = 0; count < numCluster; count++) {
                    x = random.nextInt(16);
                    z = random.nextInt(16);
                    y = random.nextInt(data.maxHeight - data.minHeight);
                    x = x + (16 * chunkX);
                    z = z + (16 * chunkZ);
                    y = y + data.minHeight;
                    numOre = MathHelper.clamp_int(random.nextInt(data.maxCluster), data.minCluster, data.maxCluster);

                    generateOre(world, random, x, y, z, data.oreType, data.meta, numOre);
                }
            }
        }
    }

    private void generateOre(World world, Random rand, int x, int y, int z, int blockID, int meta, int ntg) {
        int lx, ly, lz;
        lx = x;
        ly = y;
        lz = z;
        int id;
        id = world.getBlockId(lx, ly, lz);
        if(id != Block.stone.blockID && id != Block.dirt.blockID)
            return;

        for(int i = 0; i < ntg; i++) {

            id = world.getBlockId(lx, ly, lz);

            world.setBlock(lx, ly, lz, blockID, meta, 2);
            switch(rand.nextInt(3)) {
            case 0:
                lx = lx + (rand.nextInt(4) - 2);
                break;
            case 1:
                ly = ly + (rand.nextInt(4) - 2);
                break;
            case 2:
                lz = lz + (rand.nextInt(4) - 2);
                break;
            }
        }
    }
}
