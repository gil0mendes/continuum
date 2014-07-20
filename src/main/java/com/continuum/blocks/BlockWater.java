package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A water block.
 */
public class BlockWater extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(14, 13);
	}

	@Override
	public boolean isPenetrable() {
		return false;
	}

	@Override
	public boolean isCastingShadows() {
		return false;
	}

	@Override
	public boolean renderBoundingBox() {
		return false;
	}

	@Override
	public boolean isRemovable() {
		return true;
	}
}