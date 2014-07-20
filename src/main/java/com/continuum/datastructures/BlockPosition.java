package com.continuum.datastructures;

import com.continuum.utilities.VectorPool;
import org.lwjgl.util.vector.Vector3f;

/**
 * Represents the position of a block. This class is used within the
 * collision detection process.
 */
public final class BlockPosition implements Comparable<BlockPosition> {

	/**
	 * Position on the x-axis.
	 */
	/**
	 * Position on the y-axis.
	 */
	/**
	 * Position on the z-axis.
	 */
	public final int x;
	public final int y;
	public final int z;
	private final Vector3f _origin;

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @param origin
	 */
	public BlockPosition(int x, int y, int z, Vector3f origin) {
		this.x = x;
		this.y = y;
		this.z = z;
		this._origin = origin;
	}

	/**
	 * @return
	 */
	float getDistance() {
		return VectorPool.getVector((float) x - _origin.x, (float) y - _origin.y, (float) z - _origin.z).lengthSquared();
	}

	/**
	 * @param o
	 * @return
	 */
	public int compareTo(BlockPosition o) {
		return new Float(getDistance()).compareTo(o.getDistance());
	}
}