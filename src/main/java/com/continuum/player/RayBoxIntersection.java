package com.continuum.player;

import com.continuum.utilities.VectorPool;
import org.lwjgl.util.vector.Vector3f;

/**
 * Represents an intersection of a ray with the face of a block.
 */
public final class RayBoxIntersection implements Comparable<RayBoxIntersection> {

	/**
	 *
	 */
	public enum SIDE {

		/**
		 *
		 */
		FRONT,
		/**
		 *
		 */
		BACK,
		/**
		 *
		 */
		LEFT,
		/**
		 *
		 */
		RIGHT,
		/**
		 *
		 */
		TOP,
		/**
		 *
		 */
		BOTTOM,
		/**
		 *
		 */
		NONE
	}

	private final Vector3f v0;
	private final Vector3f v1;
	private final Vector3f v2;
	private final float d;
	private final float t;
	private final Vector3f origin;
	private final Vector3f ray;
	private final Vector3f intersectPoint;
	private final Vector3f blockPos;

	/**
	 * @param blockPos
	 * @param v0
	 * @param v1
	 * @param v2
	 * @param d
	 * @param t
	 * @param origin
	 * @param ray
	 * @param intersectPoint
	 */
	public RayBoxIntersection(Vector3f blockPos, Vector3f v0, Vector3f v1, Vector3f v2, float d, float t, Vector3f origin, Vector3f ray, Vector3f intersectPoint) {
		this.d = d;
		this.t = t;
		this.origin = origin;
		this.ray = ray;
		this.intersectPoint = intersectPoint;
		this.v0 = v0;
		this.v1 = v1;
		this.v2 = v2;
		this.blockPos = blockPos;
	}

	/**
	 * @param o
	 * @return
	 */
	public int compareTo(RayBoxIntersection o) {
		return new Float(Math.abs(getT())).compareTo(Math.abs(o.getT()));
	}

	/**
	 * @return the d
	 */
	public float getD() {
		return d;
	}

	/**
	 * @return the t
	 */
	float getT() {
		return t;
	}

	/**
	 * @return the origin
	 */
	public Vector3f getOrigin() {
		return origin;
	}

	/**
	 * @return the ray
	 */
	public Vector3f getRay() {
		return ray;
	}

	/**
	 * @return the intersectPoint
	 */
	public Vector3f getIntersectPoint() {
		return intersectPoint;
	}

	/**
	 * @return
	 */
	Vector3f calcSurfaceNormal() {
		Vector3f a = Vector3f.sub(v1, v0, null);
		Vector3f b = Vector3f.sub(v2, v0, null);
		Vector3f norm = Vector3f.cross(a, b, null);

		VectorPool.putVector(a);
		VectorPool.putVector(b);

		return norm;
	}

	/**
	 * @return
	 */
	public Vector3f calcAdjacentBlockPos() {
		return Vector3f.add(getBlockPos(), calcSurfaceNormal(), null);
	}

	/**
	 * @return the blockPos
	 */
	public Vector3f getBlockPos() {
		return blockPos;
	}

	/**
	 * @return
	 */
	@Override
	public String toString() {
		return String.format("x: %.2f y: %.2f z: %.2f", blockPos.x, blockPos.y, blockPos.z);
	}
}