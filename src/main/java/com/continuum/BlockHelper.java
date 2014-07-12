package com.continuum;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Helper functions for blocks
 * <p/>
 * Created by gil0mendes on 12/07/14.
 */
public class BlockHelper {
	public static final float div = 1.0f / 16.0f;

	public static enum SIDE {

		LEFT, RIGHT, TOP, BOTTOM, FRONT, BACK;
	}

	;

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
					return calcOffsetForTextureAt(0, 0);
				}
				break;
			// Dirt block
			case 0x2:
				return calcOffsetForTextureAt(2, 0);
			// Stone block
			case 0x3:
				return calcOffsetForTextureAt(1, 0);
			// Water block
			case 0x4:
				return calcOffsetForTextureAt(15, 13);
			// Tree block
			case 0x5:
				if (side == SIDE.LEFT || side == SIDE.RIGHT || side == SIDE.FRONT || side == SIDE.BACK) {
					return calcOffsetForTextureAt(4, 1);
				} else if (side == SIDE.TOP) {
					return calcOffsetForTextureAt(5, 1);
				}
				break;
			// Leaf block
			case 0x6:
				return calcOffsetForTextureAt(4, 3);
			default:
				return calcOffsetForTextureAt(2, 0);
		}

		return calcOffsetForTextureAt(2, 0);
	}

	/**
	 * Get color offset
	 *
	 * @param type
	 * @param side
	 * @return
	 */
	public static Vector3f getColorOffsetFor(int type, SIDE side) {
		switch (type) {
			// Grass block
			case 0x1:
				if (side == SIDE.TOP) {
					return new Vector3f(0.5f, 1f, 0.55f);
				}
				break;
			// Leaf block
			case 0x6:
				return new Vector3f(0.25f, 0.8f, 0.25f);
		}

		return new Vector3f(1f, 1f, 1f);
	}

	/**
	 * Is a translucent block?
	 *
	 * @param type
	 * @return
	 */
	public static boolean isBlockTypeTranslucent(int type) {
		switch (type) {
			// Leaf block
			case 0x6:
				return true;
			default:
				return false;
		}
	}
}
