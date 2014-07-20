package com.continuum.world;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Primitives {

	/**
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 * @param x
	 * @param y
	 * @param z
	 */
	public static void drawCloud(float scaleX, float scaleY, float scaleZ, float x, float y, float z) {
		// Front face
		GL11.glColor3f(0.99f, 0.99f, 0.99f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);

		// Back Face
		GL11.glColor3f(0.99f, 0.99f, 0.99f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);

		// Top Face
		GL11.glColor3f(1f, 1f, 1f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);

		// Bottom Face
		GL11.glColor3f(0.91f, 0.91f, 0.91f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);

		// Right face
		GL11.glColor3f(0.99f, 0.99f, 0.99f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);

		// Left Face
		GL11.glColor3f(0.99f, 0.99f, 0.99f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, -0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(-0.5f * scaleX + x, -0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, 0.5f * scaleZ + z);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(-0.5f * scaleX + x, 0.5f * scaleY + y, -0.5f * scaleZ + z);
	}

	/**
	 * @param brightness
	 */
	public static void drawSkyBox(float brightness) {
		Vector3f skyColor = new Vector3f(0.80f * brightness, 0.90f * brightness, 0.98f * brightness);

		// Front face
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(0.5f, -0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(-0.5f, 0.5f, 0.5f);

		// Back Face
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);

		// Top Face
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);

		// Bottom Face
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glVertex3f(0.5f, -0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glVertex3f(-0.5f, -0.5f, 0.5f);

		// Right face

		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(0.5f, -0.5f, 0.5f);

		// Left Face
		GL11.glTexCoord2f(0.0f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glTexCoord2f(0.5f, 0.0f);
		GL11.glColor3f(brightness, brightness, brightness);
		GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
		GL11.glTexCoord2f(0.5f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
		GL11.glTexCoord2f(0.0f, 0.5f);
		GL11.glColor3f(skyColor.x * brightness, skyColor.y * brightness, skyColor.z * brightness);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
	}
}