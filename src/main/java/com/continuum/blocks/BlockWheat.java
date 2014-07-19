package com.continuum.blocks;

import com.continuum.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A flower billboard block.
 */
public class BlockWheat extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		return new Vector4f(40f / 255f, 190f / 255f, 40f / 255f, 1.0f);
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(12, 6);
	}

	@Override
	public boolean isBlockBillboard() {
		return true;
	}

	@Override
	public boolean isPenetrable() {
		return true;
	}

	@Override
	public boolean isCastingShadows() {
		return false;
	}
}