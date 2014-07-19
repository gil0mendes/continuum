package com.continuum.blocks;

import com.continuum.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A dirt block.
 */
public class BlockHardStone extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return false;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(1, 1);
	}
}