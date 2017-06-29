package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A bookshelf block.
 */
public class BlockBookShelf extends Block {

    @Override
    public Vector2f getTextureOffsetFor(SIDE side) {
        if (side == SIDE.TOP || side == SIDE.BOTTOM) {
            return Helper.calcOffsetForTextureAt(6, 5);
        }

        return Helper.calcOffsetForTextureAt(3, 2);
    }
}