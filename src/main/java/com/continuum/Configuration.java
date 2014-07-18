package com.continuum;

import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;

/**
 * Stores the settings of the game.
 *
 * TODO: Replace with a single hashmap
 */
public class Configuration {

	// World
	public static Vector3f VIEWING_DISTANCE_IN_CHUNKS = new Vector3f(16.0f, 1.0f, 16.0f);
	public static final float SUN_SIZE = 60f;
	// Chunk
	public static final Vector3f CHUNK_DIMENSIONS = new Vector3f(16, 128, 16);
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
	// Graphics
	public static final PixelFormat PIXEL_FORMAT = new PixelFormat().withDepthBits(24);
}