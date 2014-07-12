package com.continuum;

import org.lwjgl.util.vector.Vector3f;

/**
 * Created by gil0mendes on 12/07/14.
 */
public class Helper {
	// Helper instance
	private static Helper instance = null;

	/**
	 * Get a helper instance.
	 *
	 * @return Helper instance
	 */
	public static Helper getInstance() {
		if (instance == null) {
			instance = new Helper();
		}

		return instance;
	}

	/**
	 * Constructor
	 */
	public Helper() {}

	public Vector3f calcPlayerOrigin() {
		return new Vector3f(Chunk.chunkDimensions.x * Configuration.viewingDistanceInChunks.x/2, 128 ,(Chunk.chunkDimensions.z * Configuration.viewingDistanceInChunks.z) / 2);
	}
}