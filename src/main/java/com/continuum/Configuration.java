package com.continuum;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 * CLass with some configurations
 *
 * Created by gil0mendes on 12/07/14.
 */
public class Configuration {
	// View distance
	public static Vector3f viewingDistanceInChunks = new Vector3f(32.0f,1.0f,32.0f);

	public static Vector2f calcTextureMapCoords(int x, int y) {
		return new Vector2f(1.0f/(float) x,1.0f/(float) y);
	}
}