package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

/**
 * A tree trunk.
 */
public class BlockTreeTrunk extends Block {
	@Override
	public Vector2f getTextureOffsetFor(Block.SIDE side) {
		if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
			return Helper.calcOffsetForTextureAt(4, 1);
		} else {
			return Helper.calcOffsetForTextureAt(5, 1);
		}
	}
}