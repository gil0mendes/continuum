package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A high grass billboard block.
 */
public class BlockHighGrass extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		return new Vector4f(160f / 255f, 223f / 255f, 84f / 255f, 1.0f);
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(7, 2);
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

	@Override
	public boolean renderBoundingBox() {
		return false;
	}
}