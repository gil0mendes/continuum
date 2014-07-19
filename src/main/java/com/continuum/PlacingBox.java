package com.continuum;

import static org.lwjgl.opengl.GL11.*;

/**
 * A wireframe box which can be used to highlight blocks.
 */
public class PlacingBox extends RenderableObject {

	@Override
	public void render() {
		glColor3f(1.0f, 1.0f, 1.0f);
		glBegin(GL_LINES);
		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(+0.5f, -0.5f, -0.5f);

		glVertex3f(-0.5f, -0.5f, +0.5f);
		glVertex3f(+0.5f, -0.5f, +0.5f);

		glVertex3f(-0.5f, +0.5f, +0.5f);
		glVertex3f(+0.5f, +0.5f, +0.5f);

		glVertex3f(-0.5f, +0.5f, -0.5f);
		glVertex3f(+0.5f, +0.5f, -0.5f);

		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(-0.5f, -0.5f, +0.5f);

		glVertex3f(+0.5f, -0.5f, -0.5f);
		glVertex3f(+0.5f, -0.5f, +0.5f);

		glVertex3f(-0.5f, +0.5f, -0.5f);
		glVertex3f(-0.5f, +0.5f, +0.5f);

		glVertex3f(+0.5f, +0.5f, -0.5f);
		glVertex3f(+0.5f, +0.5f, +0.5f);

		glVertex3f(-0.5f, -0.5f, -0.5f);
		glVertex3f(-0.5f, +0.5f, -0.5f);

		glVertex3f(+0.5f, -0.5f, -0.5f);
		glVertex3f(+0.5f, +0.5f, -0.5f);

		glVertex3f(-0.5f, -0.5f, +0.5f);
		glVertex3f(-0.5f, +0.5f, +0.5f);

		glVertex3f(+0.5f, -0.5f, +0.5f);
		glVertex3f(+0.5f, +0.5f, +0.5f);
		glEnd();
	}
}