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

public class BlockSnow extends Block {

    @Override
    public boolean isBlockTypeTranslucent() {
        return false;
    }

    @Override
    public Vector2f getTextureOffsetFor(Block.SIDE side) {
        if (side == Block.SIDE.LEFT || side == Block.SIDE.RIGHT || side == Block.SIDE.FRONT || side == Block.SIDE.BACK) {
            return Helper.calcOffsetForTextureAt(4, 4);

        } else if (side == Block.SIDE.BOTTOM) {
            return Helper.calcOffsetForTextureAt(2, 0);
        } else {
            return Helper.calcOffsetForTextureAt(2, 4);
        }
    }
}
