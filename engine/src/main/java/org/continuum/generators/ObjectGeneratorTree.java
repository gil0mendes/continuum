/*
 * Copyright 2014-2017 Gil Mendes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.continuum.generators;

import org.continuum.main.Configuration;
import org.continuum.world.WorldProvider;

/**
 */
public class ObjectGeneratorTree extends ObjectGenerator {

    /**
     * @param w
     * @param seed
     */
    public ObjectGeneratorTree(WorldProvider w, String seed) {
        super(w, seed);
    }

    /**
     * Generates the tree.
     *
     * @param posX Origin on the x-axis
     * @param posY Origin on the y-axis
     * @param posZ Origin on the z-axis
     */
    @Override
    public void generate(int posX, int posY, int posZ, boolean update) {
        int height = Math.abs(_rand.randomInt() % 4) + 6;

        if (posY + height >= Configuration.CHUNK_DIMENSIONS.y) {
            return;
        }

        // Generate tree trunk
        for (int i = 0; i < height; i++) {
            _worldProvider.setBlock(posX, posY + i, posZ, (byte) 0x5, update, false);
        }

        // Generate the treetop
        for (int y = height - 3; y < height + 1; y++) {
            for (int x = -2; x < 3; x++) {
                for (int z = -2; z < 3; z++) {
                    if (!(x == -2 && z == -2) && !(x == 2 && z == 2) && !(x == -2 && z == 2) && !(x == 2 && z == -2)) {
                        if (_rand.randomDouble() <= 0.8) {
                            _worldProvider.setBlock(posX + x, posY + y, posZ + z, (byte) 0x6, update, false);
                            _worldProvider.refreshSunlightAt(posX + x, posZ + z, false, true);
                        }
                    }
                }
            }
        }
    }
}
