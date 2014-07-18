package com.continuum;

import org.lwjgl.util.vector.Vector3f;

/**
 * Stores the settings of the game.
 *
 * TODO: Replace with a single hashmap
 */
public class Configuration {

	// World
	public static Vector3f VIEWING_DISTANCE_IN_CHUNKS = new Vector3f(16.0f, 1.0f, 16.0f);
	// Debug
	public static boolean SHOW_PLACING_BOX = true;
	public static boolean SHOW_CHUNK_OUTLINES = false;
	// Display
	public static final int DISPLAY_HEIGHT = 720;
	public static final int DISPLAY_WIDTH = 1280;
	public static final boolean FULLSCREEN = false;
	// Illumination
	public static final float MAX_LIGHT = 1.0f;
	public static final float MIN_LIGHT = 0.2f;
	public static final float DIMMING_INTENS = 0.075f;
	public static final float BLOCK_SIDE_DIMMING = 0.025f;
	// Player
	public static int JUMP_INTENSITY = 10;
	public static int MAX_GRAVITY = 32;
	public static int WALKING_SPEED = 4;
	public static int RUNNING_SPEED = 32;
	public static int PLAYER_HEIGHT = 1;
}