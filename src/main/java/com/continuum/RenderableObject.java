package com.continuum;

import com.continuum.utilities.VectorPool;
import org.lwjgl.util.vector.Vector3f;

/**
 * The base class of all renderable objects.
 */
public abstract class RenderableObject {

	/**
	 * The position of this renderable object.
	 */
	protected Vector3f _position = VectorPool.getVector();

	/**
	 * Rendering operations have to be placed here.
	 */
	public void render() {
	}

	/**
	 * Updating operations have to be placed here.
	 */
	public void update() {
	}

	/**
	 * Returns the position of the object.
	 *
	 * @return The position
	 */
	public Vector3f getPosition() {
		return _position;
	}

	/**
	 * Sets the position of the object.
	 *
	 * @param position The position
	 */
	public void setPosition(Vector3f position) {
		_position.set(position);
	}
}