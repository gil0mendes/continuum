package com.continuum.world.entity;

import com.continuum.rendering.RenderableObject;
import com.continuum.datastructures.AABB;
import org.lwjgl.util.vector.Vector3f;

public abstract class Entity implements RenderableObject {

	protected Vector3f _position = new Vector3f();

    /**
     * Returns the position of the entity.
     *
     * @return The position
     */
    public Vector3f getPosition() {
        return _position;
    }

    /**
     * Sets the position of the entity.
     *
     * @param position The position
     */
    public void setPosition(Vector3f position) {
        _position.set(position);
    }

    /**
     * @return The AABB of the entity
     */
    public abstract AABB getAABB();
}