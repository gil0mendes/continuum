package com.continuum;

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by gil0mendes on 12/07/14.
 */
public class Player extends RenderObject {

	boolean playerModelHidden = false;
	float yaw = 0.0f;
	float pitch = 0.0f;
	float walkingSpeed = 0.075f;

	public Player() {
		position = new Vector3f(0.0f, -100.0f, 0.0f);
	}

	/*
	 * Positions the player within the world
	 * and adjusts the players view accordingly.
	 *
	 * TODO: Create and render a player mesh.
	 */
	@Override
	public void render() {
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		glTranslatef(position.x, position.y, position.z);
	}

	public void yaw(float diff) {
		yaw += diff;
	}

	public void pitch(float diff) {
		pitch += diff;
	}

	public void walkForward() {
		position.x -= walkingSpeed * (float) Math.sin(Math.toRadians(yaw));
		position.z += walkingSpeed * (float) Math.cos(Math.toRadians(yaw));
	}

	public void walkBackwards() {
		position.x += walkingSpeed * (float) Math.sin(Math.toRadians(yaw));
		position.z -= walkingSpeed * (float) Math.cos(Math.toRadians(yaw));
	}

	public void strafeLeft() {
		position.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw-90));
		position.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw-90));
	}

	public void strafeRight() {
		position.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw+90));
		position.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw+90));
	}
}