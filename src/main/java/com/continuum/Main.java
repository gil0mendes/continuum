package com.continuum;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created by gil0mendes on 11/07/14.
 */
public class Main
{
	// Constant values
	public static final String GAME_TITLE = "Continuum (Pre) Alpha";
	private static long timerTicksPerSecond = Sys.getTimerResolution();
	public static final float DISPLAY_WIDTH = 1024.0f;
	public static final float DISPLAY_HEIGHT = 768.0f;

	// Logger
	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	// Time at the start of the last render loop
	private long lastLoopTime = getTime();

	// Time at last fps measurement.
	private long lastFpsTime;

	// Measured frames per second.
	private int fps;

	// World
	private World world;

	// Player
	private Player player;

	// Start logger
	static {
		try {
			LOGGER.addHandler(new FileHandler("errors.log", true));
		} catch (IOException ex) {
			LOGGER.log(Level.WARNING, ex.toString(), ex);
		}
	}

	public static void main(String[] args) {
		Main main = null;

		// Try create the main window
		try {
			main = new Main();

			main.create();
			main.start();
		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, ex.toString(), ex);
		} finally {
			// After crash or end render loop should destroy the main window
			if (main != null) {
				main.destroy();
			}
		}

		System.exit(0);
	}

	/**
	 * Empty constructor
	 */
	public Main() {}

	/**
	 * Get the current time in milliseconds
	 *
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / timerTicksPerSecond;
	}

	/**
	 * Create and initialize Display, Keyboard, Mouse and main loop.
	 *
	 * @throws LWJGLException
	 */
	public void create() throws LWJGLException {
		// Display
		Display.setDisplayMode(new DisplayMode((int) DISPLAY_WIDTH, (int) DISPLAY_HEIGHT));
		Display.setFullscreen(false);
		Display.setTitle("Continuum");
		Display.create();

		// Keyboard
		Keyboard.create();

		// Mouse
		Mouse.setGrabbed(true);
		Mouse.create();

		// OpenGL
		this.initGL();
		this.resizeGL();
	}

	/**
	 * Destroy window
	 */
	public void destroy() {
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

	/**
	 * Start main loop
	 */
	public void start() {
		while (!Display.isCloseRequested()) {
			// Sync. at 60 FPS
			Display.sync(60);

			long delta = getTime() - lastLoopTime;
			lastLoopTime = getTime();
			lastFpsTime += delta;
			fps++;

			// Update the FPS display in the title bar (only) at every second passed
			if (lastFpsTime >= 1000) {
				Display.setTitle(GAME_TITLE + " (FPS: " + fps + ")");
				lastFpsTime = 0;
				fps = 0;
			}

			// Update the scene
			update(delta);

			// Render the scene
			render();

			// Update Display
			Display.update();

			// Process the keyboard and mouse input
			processKeyboard();
			processMouse();
		}

		Display.destroy();
	}

	/**
	 * Initialize OpenGL
	 */
	private void initGL() {
		// Enable OpenGL features
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_FOG);

		// --- Configure FOG
		float[] fogColor = {0.75f, 0.75f, 0.75f, 1f};;
		FloatBuffer fogColorBuffer = BufferUtils.createFloatBuffer(4);
		fogColorBuffer.put(fogColor);
		fogColorBuffer.rewind();

		glFog(GL_FOG_COLOR, fogColorBuffer);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_DENSITY, 1.0f);
		glFogf(GL_FOG_START, 256.0f);
		glFogf(GL_FOG_END, 512.0f);

		// Initialize world
		this.world = new World("WORLD1", "-7492801512473941435");

		// Initialize player
		this.player = new Player(this.world);

		// Initialize Chunks
		Chunk.init();

		// Initialize world
		world.init();
	}

	/**
	 * Setup OpenGL for defined resolution
	 */
	private void resizeGL() {
		glViewport(0, 0, (int) DISPLAY_WIDTH, (int) DISPLAY_HEIGHT);

		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(64.0f, DISPLAY_WIDTH / DISPLAY_HEIGHT, 0.1f, 1000f);
		glPushMatrix();

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
	}

	/**
	 * Render the scene
	 */
	private void render() {
		glClearColor(world.getDaylight()-0.25f, world.getDaylight(), world.getDaylight()+0.25f, 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		// Render player
		this.player.render();

		// Render all chunks
		Chunk.renderAllChunks();
	}

	/**
	 * Updates the player and the world
	 */
	public void update(long delta) {
		world.update(delta);
		player.update(delta);
	}

	// --- PROCESS

	/**
	 * Process Mouse data
	 */
	private void processMouse() {}

	/**
	 * Process Keyboard data
	 */
	private void processKeyboard() {}


}
