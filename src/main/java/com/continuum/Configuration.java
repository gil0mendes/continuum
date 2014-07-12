package com.continuum;

import org.lwjgl.util.vector.Vector2f;

/**
 * CLass with some configurations
 *
 * Created by gil0mendes on 12/07/14.
 */
public class Configuration {
	// Display debug
	public static boolean displayDebug = true;

	// View distance
	public static Vector2f viewingDistanceInChunks = new Vector2f(64.0f,64.0f);

	public static Vector2f calcTextureMapCoords(int x, int y) {
		return new Vector2f(1.0f/(float) x,1.0f/(float) y);
	}
}