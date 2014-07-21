package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A leaf block.
 */
public class BlockDarkLeaf extends Block {

	@Override
	public boolean isCastingShadows() {
		return false;
	}

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		return new Vector4f(130f / 255f, 180f / 255f, 60f / 255f, 1.0f);
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