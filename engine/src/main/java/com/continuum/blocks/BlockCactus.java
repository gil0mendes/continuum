package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

public class BlockCactus extends Block {

    @Override
    public boolean doNotTessellate() {
        return true;
    }

    @Override
    public boolean isBlockTypeTranslucent() {
        return true;
    }

    @Override
    public Vector2f getTextureOffsetFor(SIDE side) {
        if (side == Block.SIDE.LEFT || side == Block.SIDE.RIGHT || side == Block.SIDE.FRONT || side == Block.SIDE.BACK) {
            return Helper.calcOffsetForTextureAt(6, 4);
        }

        return Helper.calcOffsetForTextureAt(5, 4);
    }

    public BLOCK_FORM getBlockForm() {
        return BLOCK_FORM.CACTUS;
    }
}