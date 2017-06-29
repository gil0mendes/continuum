package com.continuum.blocks;

/**
 * A air block.
 */
public class BlockAir extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return true;
	}

	@Override
	public boolean isBlockInvisible() {
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
	public boolean shouldRenderBoundingBox() {
		return false;
	}
}