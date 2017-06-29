package com.continuum.rendering;

/**
 * The base interface of all renderable objects.
 */
public interface RenderableObject {
    /**
     * Rendering operations have to be placed here.
     */
    public abstract void render();

    /**
     * Updating operations have to be placed here.
     */
    public abstract void update();
}