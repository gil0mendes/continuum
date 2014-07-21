package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A glass block.
 */
public class BlockGlass extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}


	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(1, 3);
	}
}