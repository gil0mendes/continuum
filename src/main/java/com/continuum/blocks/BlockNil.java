package com.continuum.blocks;

/**
 * A nil block. Usable as placeholder for 'impossible' block types.
 */
public class BlockNil extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return false;
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
	public boolean renderBoundingBox() {
		return false;
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}