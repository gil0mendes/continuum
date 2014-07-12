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
	protected Vector3f position = new Vector3f(0.0f, 0.0f, 0.0f);

	/**
	 * Empty constructor
	 */
	public RenderObject() {}

	/**
	 * Render method
	 */
	public void render() {}

	/**
	 * Get object position.
	 *
	 * @return Current position
	 */
	public Vector3f getPosition() {
		return position;
	}

	/**
	 * Set a new position for object.
	 *
	 * @param newPostion
	 */
	public void setPosition(Vector3f newPostion) {
		this.position = newPostion;
	}

}
