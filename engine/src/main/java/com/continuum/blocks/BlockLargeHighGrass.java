package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A large high grass billboard block.
 */
public class BlockLargeHighGrass extends Block {

	private static final Vector4f colorOffset = new Vector4f(0.8f, 0.8f, 0.8f, 1.0f);

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		return colorOffset;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(15, 11);
	}

	@Override
	public boolean isPenetrable() {
		return true;
	}

	@Override
	public boolean isCastingShadows() {
		return true;
	}

	@Override
	public boolean shouldRenderBoundingBox() {
		return false;
	}

	@Override
	public BLOCK_FORM getBlockForm() {
		return BLOCK_FORM.BILLBOARD;
	}
}