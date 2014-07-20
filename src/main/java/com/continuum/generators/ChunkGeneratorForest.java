package com.continuum.generators;

import com.continuum.world.Chunk;
import com.continuum.Configuration;

/**
 * Generates some trees, flowers and high grass.
 */
public class ChunkGeneratorForest extends ChunkGeneratorTerrain {

	/**
	 * Init. the forest generator.
	 *
	 * @param seed
	 */
	public ChunkGeneratorForest(String seed) {
		super(seed);
	}

	/**
	 * Apply the generation process to the given chunk.
	 *
	 * @param c
	 */
	@Override
	public void generate(Chunk c) {
		for (int y = 0; y < Configuration.CHUNK_DIMENSIONS.y; y++) {
			for (int x = 0; x < Configuration.CHUNK_DIMENSIONS.x; x++) {
				for (int z = 0; z < Configuration.CHUNK_DIMENSIONS.z; z++) {
					generateGrassAndFlowers(c, x, y, z);
					generateTree(c, x, y, z);
				}
			}
		}
	}

	/**
	 * @param c
	 * @param x
	 * @param y
	 * @param z
	 */
	void generateGrassAndFlowers(Chunk c, int x, int y, int z) {

		if (c.getBlock(x, y, z) == 0x1) {
			float grassDens = calcGrassDensity(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y), c.getBlockWorldPosZ(z));

			if (grassDens > 0.0) {
                /*
                 * Generate high grass.
                 */
				double rand = _rand.standNormalDistrDouble();
				if (rand >= 0) {
					if (c.canBlockSeeTheSky(x, y + 1, z)) {
						c.setBlock(x, y + 1, z, (byte) 0xB);
					}
				} else if (rand <= -1) {
					if (c.canBlockSeeTheSky(x, y + 1, z)) {
						c.setBlock(x, y + 1, z, (byte) 0xC);
					}
				}

                /*
                 * Generate flowers.
                 */
				if (_rand.standNormalDistrDouble() < -2) {
					if (_rand.randomBoolean()) {
						if (c.canBlockSeeTheSky(x, y + 1, z)) {
							c.setBlock(x, y + 1, z, (byte) 0x9);
						}
					} else {
						if (c.canBlockSeeTheSky(x, y + 1, z)) {
							c.setBlock(x, y + 1, z, (byte) 0xA);
						}
					}

				}
			}
		}
	}

	/**
	 * @param c
	 * @param x
	 * @param y
	 * @param z
	 */
	void generateTree(Chunk c, int x, int y, int z) {
		// Do not create trees too close to surrounding blocks.
		if (c.getParent().isBlockSurrounded(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y + 1), c.getBlockWorldPosZ(z)) || !c.canBlockSeeTheSky(x, y + 1, z)) {
			return;
		}

		if (c.getBlock(x, y, z) == 0x1 && y > 32) {
			float forestDens = calcForestDensity(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y), c.getBlockWorldPosZ(z));
			double r = _rand.standNormalDistrDouble();

			// Create some trees outside of forests
			if (forestDens < 0.01) {
				r += 3f;
			}

			if (r > -0.1 && r < 0.1) {
				double r2 = _rand.standNormalDistrDouble();
				if (r2 > -2 && r2 < -1) {
					c.setBlock(x, y + 1, z, (byte) 0x0);
					c.getParent().getGeneratorPineTree().generate(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y) + 1, c.getBlockWorldPosZ(z), false);
				} else if (r2 > -3 && r2 < -2) {
					c.setBlock(x, y + 1, z, (byte) 0x0);
					c.getParent().getGeneratorFirTree().generate(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y) + 1, c.getBlockWorldPosZ(z), false);
				} else {
					c.setBlock(x, y + 1, z, (byte) 0x0);
					c.getParent().getGeneratorTree().generate(c.getBlockWorldPosX(x), c.getBlockWorldPosY(y) + 1, c.getBlockWorldPosZ(z), false);
				}
			}
		}
	}

	/**
	 * Returns the cave density for the base terrain.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	float calcForestDensity(float x, float y, float z) {
		float result = 0.0f;
		result += _pGen3.multiFractalNoise(0.009f * x, 0.009f * y, 0.009f * z, 4, 2.3614521f);
		return result;
	}

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	float calcGrassDensity(float x, float y, float z) {
		float result = 0.0f;
		result += _pGen3.multiFractalNoise(0.02f * x, 0.02f * y, 0.02f * z, 8, 2.37152f);
		return result;
	}
}