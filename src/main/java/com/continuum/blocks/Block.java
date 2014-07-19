package com.continuum.blocks;

import com.continuum.Helper;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

/**
 * Block class
 */
public abstract class Block {

	public static enum SIDE {

		LEFT, RIGHT, TOP, BOTTOM, FRONT, BACK;
	};
	private static Block[] _blocks = {new BlockAir(), new BlockGrass(), new BlockDirt(), new BlockStone(), new BlockWater(), new BlockWood(), new BlockLeaf(), new BlockSand(), new BlockHardStone(), new BlockRedFlower(), new BlockYellowFlower(), new BlockWheat()};

	public static Block getBlock(int type) {
		Block b = null;
		try {
			b = _blocks[type];
		} catch (Exception e) {
			Logger.getLogger(Block.class.toString()).log(Level.SEVERE, e.toString());
		}
		return b;
	}

	/**
	 * Returns true if a given block type is translucent.
	 *
	 * @param type The block type
	 * @return True if the block type is translucent
	 */
	public boolean isBlockTypeTranslucent() {
		return false;
	}

	/**
	 * Calculates the color offset for a given block type and a speific
	 * side of the block.
	 *
	 * @param type The block type
	 * @param side The block side
	 * @return The color offset
	 */
	public Vector4f getColorOffsetFor(SIDE side) {
		return new Vector4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

	/**
	 * Calculates the texture offset for a given block type and a specific
	 * side of the block.
	 *
	 * @param type The type of the block
	 * @param side The side of the block
	 * @return The texture offset
	 */
	public Vector2f getTextureOffsetFor(SIDE side) {
		return Helper.getInstance().calcOffsetForTextureAt(2, 0);
	}

	/**
	 *  TODO
	 *
	 * @return
	 */
	public boolean isBlockBillboard() {
		return false;
	}

	/**
	 *  TODO
	 *
	 * @return
	 */
	public boolean isBlockInvisible() {
		return false;
	}

	/**
	 *  TODO
	 *
	 * @return
	 */
	public boolean isPenetrable() {
		return false;
	}

	/**
	 *  TODO
	 *
	 * @return
	 */
	public boolean isCastingShadows() {
		return true;
	}
}