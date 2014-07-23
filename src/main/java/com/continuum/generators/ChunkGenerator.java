package com.continuum.generators;

import com.continuum.noise.PerlinNoise;
import com.continuum.utilities.FastRandom;
import com.continuum.world.chunk.Chunk;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Generators are used to generate the basic terrain, to generate caves
 * and to populate the surface.
 */
public abstract class ChunkGenerator {

    final PerlinNoise _pGen1, _pGen2, _pGen3, _pGen4, _pGen5, _pGen6;
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
        _pGen4 = new PerlinNoise(seed.hashCode() + 3);
        _pGen5 = new PerlinNoise(seed.hashCode() + 4);
        _pGen6 = new PerlinNoise(seed.hashCode() + 5);
    }

    /**
     * Apply the generation process to the given chunk.
     *
     * @param c
     */
    public void generate(Chunk c) {
        throw new NotImplementedException();
    }
}