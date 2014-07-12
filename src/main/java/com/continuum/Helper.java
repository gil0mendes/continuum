package com.continuum;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

/**
 * Created by gil0mendes on 12/07/14.
 */
public class Helper {
	// Time of last frame and update
	private double timeLastFrame;
	private double timeLastUpdate;

	// Frame counter
	private int frameCounter;

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
	public Helper() {
		updateFPSDisplay();
	}

	/**
	 * Get system time.
	 *
	 * @return
	 */
	public double getTime() {
		return (Sys.getTime() * 1000d) / Sys.getTimerResolution();
	}

	/**
	 * Update frame info
	 */
	public void frameRendered() {
		frameCounter++;
		timeLastFrame = getTime();

		if (getTime() - timeLastUpdate > 1000) {
			calculateFPS();
			updateFPSDisplay();
		}
	}

	public double calcInterpolation() {
		double fps = calculateFPS();

		if (fps > 0d) {
			return 60d / fps;
		}

		return 0d;
	}

	public double timeSinceLastFrame() {
		return getTime() - timeLastFrame;
	}

	private double calculateFPS() {
		double secondsSinceLastUpdate = ((getTime() - timeLastUpdate) / 1000d);
		if (secondsSinceLastUpdate > 0d) {
			return frameCounter / secondsSinceLastUpdate;
		}
		return 0d;
	}

	/**
	 * Update display title info.
	 */
	private void updateFPSDisplay() {
		Display.setTitle("Continuum (FPS: " + Math.round(calculateFPS()) + ", Quads: " + Chunk.quadCounter + ", Interpolation: " + calcInterpolation() + ")");

		timeLastUpdate = getTime();
		frameCounter = 0;
	}
}