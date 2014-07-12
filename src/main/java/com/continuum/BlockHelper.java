package com.continuum;

import org.lwjgl.util.vector.Vector2f;

/**
 * Helper functions for blocks
 *
 * Created by gil0mendes on 12/07/14.
 */
public class BlockHelper
{
	public static final float div = 1.0f / 16.0f;

	public static enum SIDE {

		LEFT, RIGHT, TOP, BOTTOM, FRONT, BACK;
	};

	static Vector2f calcOffsetForTextureAt(int x, int y) {
		return new Vector2f(x * div, y * div);
	}

	/**
	 * Get texture offset
	 *
	 * @param type
	 * @param side
	 * @return
	 */
	public static Vector2f getTextureOffsetFor(int type, SIDE side) {
		switch (type) {
			// Grass block
			case 0x1:
				if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
					return calcOffsetForTextureAt(3, 0);
				} else if (side == SIDE.TOP) {
					calcOffsetForTextureAt(0, 0);
				} else {
					calcOffsetForTextureAt(2, 0);
				}
				break;
			// Dirt block
			case 0x2:
				calcOffsetForTextureAt(2, 0);
				break;
			// Stone block
			case 0x3:
				calcOffsetForTextureAt(1, 0);
				break;
		}

		return calcOffsetForTextureAt(2, 0);
	}
}
