package com.continuum.generators;

import com.continuum.world.Chunk;
import com.continuum.Configuration;
import com.continuum.blocks.Block;
import com.continuum.blocks.BlockDirt;
import com.continuum.blocks.BlockWater;

/**
 * Generates grass (substitutes dirt blocks), flowers and high grass.
 */
public class ChunkGeneratorFlora extends ChunkGeneratorForest {

	/**
	 * Init. the flora generator.
	 *
	 * @param seed
	 */
	public ChunkGeneratorFlora(String seed) {
		super(seed);
	}

	/**
	 * Apply the generation process to the given chunk.
	 *
	 * @param c
	 */
	@Override
	public void generate(Chunk c) {
		for (int x = 0; x < Configuration.CHUNK_DIMENSIONS.x; x++) {
			for (int z = 0; z < Configuration.CHUNK_DIMENSIONS.z; z++) {
				for (int y = (int) Configuration.CHUNK_DIMENSIONS.y - 1; y >= 0; y--) {
					byte type = c.getBlock(x, y, z);

					// Ignore this column if a block was found, which is opaque and no dirt
					if (Block.getBlockForType(type).getClass() != BlockDirt.class && !Block.getBlockForType(type).isBlockTypeTranslucent()) {
						break;
					}

					// Do not generate flora under water
					if (Block.getBlockForType(type).getClass() == BlockWater.class) {
						break;
					}

					if (Block.getBlockForType(type).getClass() == BlockDirt.class) {
						// Not every block should be updated each turn
						if (_rand.randomDouble() > 0.025f) {
							c.setBlock(x, y, z, (byte) 0x1);
							generateGrassAndFlowers(c, x, y, z);
						}
						// Only update the topmost dirt block
						break;
					}
				}
			}
		}
	}
}