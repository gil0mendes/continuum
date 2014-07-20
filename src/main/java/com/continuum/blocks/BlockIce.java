package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * An ice block.
 */
public class BlockIce extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}


	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(3, 4);
	}
}