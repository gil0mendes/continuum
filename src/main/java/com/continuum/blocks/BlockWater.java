package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A water block.
 */
public class BlockWater extends Block {

	private static final Vector4f colorOffset = new Vector4f(0.9f, 0.9f, 1.0f, 0.8f);

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public Vector4f getColorOffsetFor(SIDE side) {
		return colorOffset;
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return Helper.calcOffsetForTextureAt(0, 0);
	}

	@Override
	public Vector2f getTerrainTextureOffsetFor(SIDE side) {
		return Helper.calcOffsetForTextureAt(13, 12);
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
	public boolean shouldRenderBoundingBox() {
		return false;
	}

	@Override
	public boolean isRemovable() {
		return false;
	}

	@Override
	public boolean letSelectionRayThrough() {
		return true;
	}

	@Override
	public BLOCK_FORM getBlockForm() {
		return BLOCK_FORM.LOWERED_BOCK;
	}
}