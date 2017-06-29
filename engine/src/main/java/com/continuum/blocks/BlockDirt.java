package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A dirt block.
 */
public class BlockDirt extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return false;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(2, 0);
	}
}