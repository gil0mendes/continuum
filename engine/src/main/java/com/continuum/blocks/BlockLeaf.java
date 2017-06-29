package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A leaf block.
 */
public class BlockLeaf extends Block {

	@Override
	public boolean isCastingShadows() {
		return true;
	}

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(4, 3);
	}

	/**
	 * @return
	 */
	@Override
	public boolean doNotTessellate() {
		return true;
	}
}