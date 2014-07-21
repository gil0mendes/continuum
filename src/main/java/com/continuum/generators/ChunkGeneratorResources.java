package com.continuum.generators;

import com.continuum.blocks.Block;
import com.continuum.blocks.BlockStone;
import com.continuum.main.Configuration;
import com.continuum.world.chunk.Chunk;

public class ChunkGeneratorResources extends ChunkGeneratorTerrain {

    /**
     * @param seed
     */
    public ChunkGeneratorResources(String seed) {
        super(seed);
    }

    /**
     * @param c
     */
    @Override
    public void generate(Chunk c) {
        for (int x = 0; x < Configuration.CHUNK_DIMENSIONS.x; x++) {
            for (int z = 0; z < Configuration.CHUNK_DIMENSIONS.z; z++) {
                for (int y = 0; y < Configuration.CHUNK_DIMENSIONS.y; y++) {
                    if (Block.getBlockForType(c.getBlock(x, y, z)).getClass() == BlockStone.class) {
                        if (_rand.standNormalDistrDouble() < Configuration.PROB_COAL) {
                            c.setBlock(x, y, z, (byte) 0x14);
                        }

                        if (_rand.standNormalDistrDouble() < Configuration.PROB_GOLD) {
                            c.setBlock(x, y, z, (byte) 0x15);
                        }

                        if (_rand.standNormalDistrDouble() < Configuration.PROB_DIAMOND) {
                            c.setBlock(x, y, z, (byte) 35);
                        }
                        if (_rand.standNormalDistrDouble() < Configuration.PROB_REDSTONE) {
                            c.setBlock(x, y, z, (byte) 33);
                        }

                        if (_rand.standNormalDistrDouble() < Configuration.PROB_SILVER) {
                            c.setBlock(x, y, z, (byte) 34);
                        }
                    }
                }
            }
        }
    }
}