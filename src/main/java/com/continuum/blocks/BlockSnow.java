package com.continuum.blocks;

import com.continuum.utilities.Helper;
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