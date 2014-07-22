package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A glass block.
 */
public class BlockGlass extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return false;
	}


	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
        if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
            return Helper.calcOffsetForTextureAt(3, 0);
        }
        else if (side == SIDE.BOTTOM) {
            return Helper.calcOffsetForTextureAt(2, 0);
        } else {
            return Helper.calcOffsetForTextureAt(1, 3);
        }
	}
}