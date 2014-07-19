package com.continuum.blocks;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

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
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		return null;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		return null;
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