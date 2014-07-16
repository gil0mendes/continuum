package com.continuum;

import org.lwjgl.util.vector.Vector3f;

/**
 * Class for a render object
 *
 * Created by gil0mendes on 12/07/14.
 */
public  abstract class RenderObject
{
	// Position vector
	protected Vector3f _position = new Vector3f(0.0f, 0.0f, 0.0f);

	/**
	 * Empty constructor
	 */
	public RenderObject() {}

	/**
	 * Render method
	 */
	public void render() {}

	/**
	 * Update method
	 *
	 * @param delta
	 */
	public void update(long delta) {}

	/**
	 * Get object position.
	 *
	 * @return Current position
	 */
	public Vector3f getPosition() {
		return _position;
	}

	/**
	 * Set a new position for object.
	 *
	 * @param newPostion
	 */
	public void setPosition(Vector3f newPostion) {
		this._position = newPostion;
	}

}
