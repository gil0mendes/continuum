package com.continuum;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * CLass with some configurations
 */
public class Configuration {
	// View distance in chunks
	public static Vector3f _viewingDistanceInChunks = new Vector3f(32.0f, 1.0f, 32.0f);

	// Show placing box
	public static boolean _showPlacingBox = false;

	public static Vector2f calcTextureMapCoords(int x, int y) {
		return new Vector2f(1.0f / (float) x, 1.0f / (float) y);
	}
}