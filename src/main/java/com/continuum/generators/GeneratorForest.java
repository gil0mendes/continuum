package com.continuum.generators;

import com.continuum.Chunk;
import com.continuum.Configuration;
import com.continuum.World;

/**
 * Generator to create a forest.
 */
public class GeneratorForest extends GeneratorTerrain {

	public GeneratorForest(String seed) {
		super(seed);
	}

	@Override
	public void generate(Chunk c, World parent) {
		for (int y = 0; y < Configuration.CHUNK_DIMENSIONS.y; y++) {
			for (int x = 0; x < Configuration.CHUNK_DIMENSIONS.x; x++) {
				for (int z = 0; z < Configuration.CHUNK_DIMENSIONS.z; z++) {

					float dens = calcForestDensity(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y), c.getBlockWorldPosZ(z));

					// Generate grass
					if (c.getBlock(x, y, z) == 0x1 && dens > 0.15) {
						if (_rand.randomBoolean()) {
							c.setBlock(x, y + 1, z, 0xB);
						} else {
							c.setBlock(x, y + 1, z, 0xC);
						}
					}

					// Generate some flowers and wheat
					if (c.getBlock(x, y, z) == 0x1 && dens > 0.5) {
						if (_rand.randomDouble() > 0.25f) {
							c.setBlock(x, y + 1, z, 0x9);
						} else {
							c.setBlock(x, y + 1, z, 0xA);
						}
					}

					// Check the distance to the last placed trees
					if (dens > 0.7 && c.getBlock(x, y, z) == 0x1 && y > 32) {
						c.getParent().generatePineTree(c.getBlockWorldPosX(x), c.getBlockWorldPosY((int) y) + 1, c.getBlockWorldPosZ(z), false);
					} else if (dens > 0.6f && c.getBlock(x, y, z) == 0x1 && y > 32) {
						c.getParent().generateTree(c.getBlockWorldPosX(x), c.getBlockWorldPosY((int) y) + 1, c.getBlockWorldPosZ(z), false);
					}
				}
			}
		}
	}

	/**
	 * Returns the cave density for the base terrain.
	 */
	protected float calcForestDensity(float x, float y, float z) {
		float result = 0.0f;
		result += _pGen1.noise(0.8f * x, 0.8f * y, 0.8f * z);
		return result;
	}
}