package com.continuum.utilities;

import org.lwjgl.util.vector.Vector3f;

import java.util.concurrent.ArrayBlockingQueue;

public class VectorPool {

	private static final ArrayBlockingQueue<Vector3f> _pool = new ArrayBlockingQueue<Vector3f>(512);

	/**
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static Vector3f getVector(float x, float y, float z) {
		Vector3f v = _pool.poll();

		if (v == null) {
			v = new Vector3f(x, y, z);
		} else {
			v.set(x, y, z);
		}

		return v;
	}

	/**
	 * @return
	 */
	public static Vector3f getVector() {
		return getVector(0f, 0f, 0f);
	}

	/**
	 * @param v
	 */
	public static void putVector(Vector3f v) {
		_pool.add(v);
	}
}