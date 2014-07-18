package com.continuum;

import java.awt.Font;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

/**
 * Entry point class
 * <p/>
 * Created by gil0mendes on 11/07/14.
 */
public class Main {
	private static TrueTypeFont font1;

	// Constant values
	public static final String GAME_TITLE = "Continuum v0.01a";
	public static final float DISPLAY_WIDTH = 1280.0f;
	public static final float DISPLAY_HEIGHT = 800.0f;

	// Logger
	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	// Time at the start of the last render loop
	private long lastLoopTime = Helper.getInstance().getTime();

	// Time at last fps measurement.
	private long lastFpsTime;

	// Measured frames per second.
	private int fps;
	private int meanFps;

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
	public Main() {
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
		Display.setTitle(GAME_TITLE);
		Display.create(new PixelFormat().withDepthBits(24));

		// Keyboard
		Keyboard.create();

		// Mouse
		Mouse.setGrabbed(true);
		Mouse.create();

		// OpenGL
		this.initGL();
		this.resizeGL();

		font1 = new TrueTypeFont(new Font("Arial", Font.PLAIN, 12), true);
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
			// Process the keyboard and mouse input
			processKeyboard();
			processMouse();

			// Sync. at 60 FPS
			Display.sync(60);

			long delta = Helper.getInstance().getTime() - lastLoopTime;
			lastLoopTime = Helper.getInstance().getTime();
			lastFpsTime += delta;
			fps++;

			// Update the FPS display in the title bar each second passed
			if (lastFpsTime >= 1000) {
				lastFpsTime = 0;

				meanFps += fps;
				meanFps /= 2;

				fps = 0;
			}

			// Update the scene
			update(delta);

			// Render the scene
			render();

			// Update Display
			Display.update();

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
		glShadeModel(GL_SMOOTH);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_FOG);
		glDepthFunc(GL_LEQUAL);
		glLineWidth(4.0f);
		//glEnable(GL_BLEND);
		//glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Configure FOG/Perspective
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glHint(GL_FOG_HINT, GL_NICEST);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_DENSITY, 1.0f);
		float viewingDistance = (Configuration._viewingDistanceInChunks.x * Chunk.CHUNK_DIMENSIONS.x) / 2f;
		glFogf(GL_FOG_START, viewingDistance - 64f);
		glFogf(GL_FOG_END, viewingDistance - 32f);

		// Initialize player
		this.player = new Player();

		// Initialize world
		this.world = new World("WORLD1", "ABCDEF", this.player);

		// Set parent for player
		this.player.setParent(this.world);

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
		gluPerspective(64.0f, DISPLAY_WIDTH / DISPLAY_HEIGHT, 0.01f, 2000f);
		glPushMatrix();

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glPushMatrix();
	}

	/**
	 * Render the scene
	 */
	public void render() {
		if (world.isWorldGenerated()) {
			glClearColor(world.getDaylightColor().x, world.getDaylightColor().y, world.getDaylightColor().z, 1.0f);

			// Color the fog like the sky
			float[] fogColor = {world.getDaylightColor().x, world.getDaylightColor().y, world.getDaylightColor().z, 1.0f};
			FloatBuffer fogColorBuffer = BufferUtils.createFloatBuffer(4);
			fogColorBuffer.put(fogColor);
			fogColorBuffer.rewind();
			glFog(GL_FOG_COLOR, fogColorBuffer);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glLoadIdentity();

			player.render();
			world.render();

			renderHUD();
		} else {
			glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		}
	}

	/**
	 * Updates the player and the world
	 */
	public void update(long delta) {
		if (world.isWorldGenerated()) {
			world.update(delta);
			player.update(delta);
		}
	}

	// --- PROCESS

	/**
	 * Process Mouse data
	 */
	private void processMouse() {
	}

	/**
	 * Process Keyboard data
	 */
	private void processKeyboard() {
	}

	private void renderHUD() {
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		glOrtho(0, Display.getDisplayMode().getWidth(),
				Display.getDisplayMode().getHeight(), 0, -1, 1);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();

		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_TEXTURE_2D);

		// Draw debugging information
		font1.drawString(4, 4, String.format("%s (fps: %d, free heap space: %d MB)", GAME_TITLE, meanFps, Runtime.getRuntime().freeMemory() / 1048576), Color.white);
		font1.drawString(4, 22, String.format("%s", player, Color.white));
		font1.drawString(4, 38, String.format("%s", world, Color.white));

		glDisable(GL_TEXTURE_2D);
		glDisable(GL_BLEND);

		glColor3f(1f, 1f, 1f);
		glLineWidth(2f);
		// Draw the crosshair
		glBegin(GL_LINES);
		glVertex2d(Display.getDisplayMode().getWidth() / 2f - 8f, Display.getDisplayMode().getHeight() / 2f);
		glVertex2d(Display.getDisplayMode().getWidth() / 2f + 8f, Display.getDisplayMode().getHeight() / 2f);

		glVertex2d(Display.getDisplayMode().getWidth() / 2f, Display.getDisplayMode().getHeight() / 2f - 8f);
		glVertex2d(Display.getDisplayMode().getWidth() / 2f, Display.getDisplayMode().getHeight() / 2f + 8f);
		glEnd();


		glEnable(GL_DEPTH_TEST);

		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
		glLoadIdentity();
	}

}
