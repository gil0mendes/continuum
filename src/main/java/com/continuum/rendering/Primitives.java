package com.continuum.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Primitives {

    private static final Vector4f CLOUD_COLOR_1 = new Vector4f(0.92f, 0.92f, 0.92f, 0.9f);
    private static final Vector4f CLOUD_COLOR_2 = new Vector4f(1.0f, 1.0f, 1.0f, 0.9f);
    private static final Vector3f SKY_COLOR_1 = new Vector3f(0.52f, 0.58f, 1.0f);
    private static final Vector3f SKY_COLOR_2 = new Vector3f(0.84f, 0.88f, 1f);

    /**
     * @param scaleX Scale along the x-axis
     * @param scaleY Scale along the y-axis
     * @param scaleZ Scale along the z-axis
     * @param x Position on the x-axis
     * @param y Position on the y-axis
     * @param z Position on the z-axis
     */
    public static void drawCloud(double scaleX, double scaleY, double scaleZ, double x, double y, double z, boolean drawLeft, boolean drawRight, boolean drawFront, boolean drawBack) {

        // Top Face
        GL11.glColor4f(CLOUD_COLOR_2.x, CLOUD_COLOR_2.y, CLOUD_COLOR_2.z, CLOUD_COLOR_2.w);
        GL11.glTexCoord2f(0.0f, 0.5f);
        GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.5f, 0.0f);
        GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.5f, 0.5f);
        GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);

        // Back Face
        if (drawBack) {
            GL11.glColor4f(CLOUD_COLOR_2.x, CLOUD_COLOR_2.y, CLOUD_COLOR_2.z, CLOUD_COLOR_2.w);
            GL11.glTexCoord2f(0.5f, 0.0f);
            GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.5f);
            GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
        }


        // Left Face
        if (drawLeft) {
            GL11.glColor4f(CLOUD_COLOR_2.x, CLOUD_COLOR_2.y, CLOUD_COLOR_2.z, CLOUD_COLOR_2.w);
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.0f);
            GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.5f);
            GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);
        }
        // Right face
        if (drawRight) {
            GL11.glColor4f(CLOUD_COLOR_2.x, CLOUD_COLOR_2.y, CLOUD_COLOR_2.z, CLOUD_COLOR_2.w);
            GL11.glTexCoord2f(0.5f, 0.0f);
            GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, -0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.5f);
            GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.0f);
            GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
        }


        // Front face
        if (drawFront) {
            GL11.glColor4f(CLOUD_COLOR_2.x, CLOUD_COLOR_2.y, CLOUD_COLOR_2.z, CLOUD_COLOR_2.w);
            GL11.glTexCoord2f(0.5f, 0.0f);
            GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.0f);
            GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.5f, 0.5f);
            GL11.glVertex3d(0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
            GL11.glTexCoord2f(0.0f, 0.5f);
            GL11.glVertex3d(-0.5 * scaleX + x, 0.5 * scaleY + y, 0.5 * scaleZ + z);
        }

        // Bottom Face
        GL11.glColor4f(CLOUD_COLOR_1.x, CLOUD_COLOR_1.y, CLOUD_COLOR_1.z, CLOUD_COLOR_1.w);
        GL11.glTexCoord2f(0.5f, 0.5f);
        GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.0f, 0.5f);
        GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, -0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3d(0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
        GL11.glTexCoord2f(0.5f, 0.0f);
        GL11.glVertex3d(-0.5 * scaleX + x, -0.5 * scaleY + y, 0.5 * scaleZ + z);
    }

    /**
     * Draws a simple gradient skybox.
     *
     * @param brightness The brightness of the skybox.
     */
    public static void drawSkyBox(double brightness) {
        // Front face
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, 0.0f, 0.5f);
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(0.5f, 0.0f, 0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);

        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, 0.5f);

        // Back Face
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, 0.0f, -0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(0.5f, 0f, -0.5f);

        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);

        // Right face
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(0.5f, 0f, -0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(0.5f, 0f, 0.5f);

        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(0.5f, 0.0f, -0.5f);
        GL11.glVertex3f(0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);


        // Left Face
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, 0f, -0.5f);
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, 0f, 0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);

        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, 0.5f);
        GL11.glVertex3f(-0.5f, 0.0f, -0.5f);

        // Top Face
        GL11.glColor3d(SKY_COLOR_1.x * brightness, SKY_COLOR_1.y * brightness, SKY_COLOR_1.z * brightness);
        GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
        GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
        GL11.glVertex3f(0.5f, 0.5f, 0.5f);
        GL11.glVertex3f(0.5f, 0.5f, -0.5f);

        // Bottom Face
        GL11.glColor3d(SKY_COLOR_2.x * brightness, SKY_COLOR_2.y * brightness, SKY_COLOR_2.z * brightness);
        GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(0.5f, -0.5f, -0.5f);
        GL11.glVertex3f(0.5f, -0.5f, 0.5f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
    }
}