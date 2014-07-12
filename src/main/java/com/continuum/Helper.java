package com.continuum;

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
}