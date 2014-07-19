package com.continuum.blocks;

import com.continuum.Helper;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * A grass block.
 *
 * @author Benjamin Glatzel <benjamin.glatzel@me.com>
 */
public class BlockGrass extends Block {

	@Override
	public boolean isBlockTypeTranslucent() {
		return false;
	}

	@Override
	public Vector4f getColorOffsetFor(Block.SIDE side) {
		if (side == Block.SIDE.TOP) {
			return new Vector4f(180f / 255f, 255f / 255f, 25f / 255f, 1.0f);
		}

		return new Vector4f(1f, 1f, 1f, 1.0f);
	}

	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		if (side == Block.SIDE.LEFT || side == Block.SIDE.RIGHT || side == Block.SIDE.FRONT || side == Block.SIDE.BACK) {
			return Helper.getInstance().calcOffsetForTextureAt(3, 0);

		} else if (side == Block.SIDE.BOTTOM) {
			return Helper.getInstance().calcOffsetForTextureAt(2, 0);
		} else {
			return Helper.getInstance().calcOffsetForTextureAt(0, 0);
		}
	}
}