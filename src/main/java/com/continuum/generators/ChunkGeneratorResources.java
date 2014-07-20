package com.continuum.generators;

import com.continuum.world.Chunk;
import com.continuum.Configuration;
import com.continuum.blocks.Block;
import com.continuum.blocks.BlockStone;

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
					}
				}
			}
		}
	}
}