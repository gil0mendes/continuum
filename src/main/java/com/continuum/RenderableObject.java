package com.continuum;

import org.lwjgl.util.vector.Vector3f;

/**
 * The base class of all renderable objects.
 *
 * @author Gil Mendes <gil00mendes@gmail.com>
 */
public  abstract class RenderableObject
{
	// Position vector
	protected Vector3f _position = new Vector3f(0.0f, 0.0f, 0.0f);

	/**
	 * Rendering operations have to be place here.
	 */
	public void render() {}

	/**
	 * Updating operation have to placed here. The delta value can be
	 * used to determinate how much time has passed since the last update.
	 *
	 * @param delta Time since the last update
	 */
	public void update(long delta) {}

	/**
	 * Returns the position of the object
	 *
	 * @return The position
	 */
	public Vector3f getPosition() {
		return _position;
	}

	/**
	 * Set the position of the object.
	 *
	 * @param newPostion The position
	 */
	public void setPosition(Vector3f newPostion) {
		this._position = newPostion;
	}

}
