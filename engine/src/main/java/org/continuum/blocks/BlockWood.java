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
 * A wood block.
 */
public class BlockWood extends Block {

    @Override
    public boolean isBlockTypeTranslucent() {
        return false;
    }

    public Vector4f getColorOffsetFor(Block.SIDE side, double temp, double hum) {
        Vector4f leadColor = this.foliageColorForTemperatureAndHumidity(temp, hum);
        return new Vector4f(leadColor.x * 1f, leadColor.y * 1f, leadColor.z * 1f, 1.0f);
    }

    @Override
    public Vector2f getTextureOffsetFor(Block.SIDE side) {
        if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
            return Helper.calcOffsetForTextureAt(4, 1);
        } else {
            return Helper.calcOffsetForTextureAt(5, 1);
        }
    }
}
