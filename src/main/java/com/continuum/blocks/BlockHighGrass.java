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
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(12, 11);
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