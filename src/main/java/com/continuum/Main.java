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
	// Time at last frame
	private long lastFrame;

	// Frames per second
	private int fps;

	// Last fps time
	protected long lastFPS;

	// Window settings
	public static final float DISPLAY_WIDTH = 1152.0f;
	public static final float DISPLAY_HEIGHT = 648.0f;

	// Logger
	public static final Logger LOGGER = Logger.getLogger(Main.class.getName());

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
		// Start debug window
		if (Configuration.displayDebug) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					new DebugWindow().setVisible(true);
				}
			});
		}

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
	 * Get the time in milliseconds
	 *
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
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
		//initGL();
		getDelta();
		lastFPS = getTime();

		while (!Display.isCloseRequested()) {
			int delta = getDelta();

			updateFPS();

			processKeyboard();
			processMouse();

			update(delta);
			render();

			Display.update();
			Display.sync(60);
		}

		Display.destroy();
	}

	/**
	 * Initialize OpenGL
	 */
	private void initGL() {
		glClearColor(0.5f, 0.75f, 1.0f, 1.0f);
		glLineWidth(2.0f);

		// Set shade model
		glShadeModel(GL_FLAT);

		// Enable OpenGL features
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_FOG);

		// Disable OpenGL features
		glDisable(GL_NORMALIZE);
		glDisable(GL_LIGHTING);

		// --- Configure FOG
		float[] fogColor = {0.75f, 0.75f, 0.75f, 1f};;
		FloatBuffer fogColorBuffer = BufferUtils.createFloatBuffer(4);
		fogColorBuffer.put(fogColor);
		fogColorBuffer.rewind();

		glFog(GL_FOG_COLOR, fogColorBuffer);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_DENSITY, 1.0f);
		glHint(GL_FOG_HINT, GL_DONT_CARE);
		glFogf(GL_FOG_START, 256.0f);
		glFogf(GL_FOG_END, 512.0f);

		// Initialize world
		this.world = new World();

		// Initialize player
		this.player = new Player(this.world);

		// Initialize Chunks
		Chunk.init();
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();

		glPushMatrix();

		// Render player
		this.player.render();

		// Render world
		this.world.render();

		// Draw coordinate axis
		glBegin(GL_LINES);
		glColor3f(255.0f, 0.0f, 0.0f);
		glVertex3f(0.0f, 0.0f, 0.0f);
		glVertex3f(1000.0f, 0.0f, 0.0f);
		glEnd();

		glBegin(GL_LINES);
		glColor3f(0.0f, 255.0f, 0.0f);
		glVertex3f(0.0f, 0.0f, 0.0f);
		glVertex3f(0.0f, 1000.0f, 0.0f);
		glEnd();

		glBegin(GL_LINES);
		glColor3f(0.0f, 0.0f, 255.0f);
		glVertex3f(0.0f, 0.0f, 0.0f);
		glVertex3f(0.0f, 0.0f, 1000.0f);
		glEnd();

		glPopMatrix();
	}

	/**
	 * Update main char
	 */
	public void update(int delta) {
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
	private void processKeyboard() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
		{
			this.player.walkForward();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
		{
			this.player.walkBackwards();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left
		{
			this.player.strafeLeft();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right
		{
			this.player.strafeRight();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			this.player.jump();
		}
	}


}
