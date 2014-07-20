package com.continuum.generators;

import com.continuum.world.Chunk;
import com.continuum.main.Configuration;
import com.continuum.utilities.FastRandom;
import com.continuum.utilities.PerlinNoise;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Generators are used to generate the basic terrain, to generate caves
 * and to populate the surface.
 */
public abstract class ChunkGenerator {

	/**
	 * First Perlin noise generator.
	 */
	final PerlinNoise _pGen1;
	/**
	 * Second Perlin noise generator.
	 */
	final PerlinNoise _pGen2;
	/**
	 * Third Perlin noise generator.
	 */
	final PerlinNoise _pGen3;
	/**
	 * Fast random number generator.
	 */
	final FastRandom _rand;

	/**
	 * Init. the generator with a given seed value.
	 *
	 * @param seed
	 */
	ChunkGenerator(String seed) {
		_rand = new FastRandom(seed.hashCode());
		_pGen1 = new PerlinNoise(seed.hashCode());
		_pGen2 = new PerlinNoise(seed.hashCode() + 1);
		_pGen3 = new PerlinNoise(seed.hashCode() + 2);
	}

	/**
	 * Apply the generation process to the given chunk.
	 *
	 * @param c
	 */
	public void generate(Chunk c) {
		throw new NotImplementedException();
	}

	/**
	 * @param c
	 * @return
	 */
	int getOffsetX(Chunk c) {
		return (int) c.getPosition().x * (int) Configuration.CHUNK_DIMENSIONS.x;
	}

	/**
	 * @param c
	 * @return
	 */
	int getOffsetY(Chunk c) {
		return (int) c.getPosition().y * (int) Configuration.CHUNK_DIMENSIONS.y;
	}

	/**
	 * @param c
	 * @return
	 */
	int getOffsetZ(Chunk c) {
		return (int) c.getPosition().z * (int) Configuration.CHUNK_DIMENSIONS.z;
	}
}