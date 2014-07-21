package com.continuum.generators;

import com.continuum.world.World;

public class ObjectGeneratorCactus extends ObjectGenerator {

    /**
     * @param w
     * @param seed
     */
    public ObjectGeneratorCactus(World w, String seed) {
        super(w, seed);
    }

    /**
     * Generates the cactus.
     *
     * @param posX Origin on the x-axis
     * @param posY Origin on the y-axis
     * @param posZ Origin on the z-axis
     */
    @Override
    public void generate(int posX, int posY, int posZ, boolean update) {
        for (int y = posY; y < posY + 3; y++) {
            _world.setBlock(posX, y, posZ, (byte) 0x18, update, false);
        }
    }
}