package com.continuum.generators;

import com.continuum.utilities.FastRandom;
import com.continuum.world.World;

/**
 * Object generators are used to generate objects within the world.
 */
public abstract class ObjectGenerator {

    /**
     *
     */
    final FastRandom _rand;
    /**
     *
     */
    final World _world;

    /**
     * @param w
     * @param seed
     */
    ObjectGenerator(World w, String seed) {
        _rand = new FastRandom(seed.hashCode());
        _world = w;
    }

    /**
     * Generates an object at the given position.
     *
     * @param posX Position on the x-axis
     * @param posY Position on the y-axis
     * @param posZ Position on the z-axis
     * @param update If true, the chunk will be queued for updating
     */
    public abstract void generate(int posX, int posY, int posZ, boolean update);
}