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
package org.continuum.blocks;

import org.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A glass block.
 */
public class BlockGlass extends Block {

    @Override
    public Vector4f getColorOffsetFor(SIDE side, double temp, double hum) {
        return this.colorForTemperatureAndHumidity(temp, hum);
    }

    @Override
    public boolean isBlockTypeTranslucent() {
        return false;
    }


    @Override
    public Vector2f getTextureOffsetFor(Block.SIDE side) {
        if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
            return Helper.calcOffsetForTextureAt(3, 0);
        } else if (side == SIDE.BOTTOM) {
            return Helper.calcOffsetForTextureAt(2, 0);
        } else {
            return Helper.calcOffsetForTextureAt(1, 3);
        }
    }
}
