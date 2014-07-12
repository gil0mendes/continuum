package com.continuum;

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * Player class
 *
 * Created by gil0mendes on 12/07/14.
 */
public class Player extends RenderObject {
	boolean playerModelHidden = false;
	float yaw = 0.0f;
	float pitch = 0.0f;

	// Walking speed
	float walkingSpeed = 0.01f;

	Vector3f acc = new Vector3f(0.0f, 0.0f, 0.0f);

	// The parent world
	private World parent = null;

	/**
	 * Player contructor
	 *
	 * @param parent Parent world
	 */
	public Player(World parent) {
		this.parent = parent;
		this.position = new Vector3f(0.0f, -100.0f, 0.0f);
	}

	/*
	 * Positions the player within the world
	 * and adjusts the players view accordingly.
	 *
	 * TODO: Create and render a player mesh.
	 */
	@Override
	public void render() {
		// Move player
		move();

		// Rotate
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		glTranslatef(-position.x, -position.y, -position.z);
	}

	public void yaw(float diff) {
		yaw += diff;
	}

	public void pitch(float diff) {
		pitch += diff;
	}

	/**
	 * Move the player
	 */
	private void move() {
		// Check if the player are hitting a chunk
		if (!this.parent.isHitting(new Vector3f(this.getPosition().getX(), this.getPosition().getY() - 4.0f, this.getPosition().getZ()))) {
			if (this.acc.getY() > -1.0f) {
				this.acc.y -= 0.01f;
			}
		} else {
			acc.setY(0.0f);
		}

		this.getPosition().setX(this.acc.getX());
		this.getPosition().setY(this.acc.getY());
		this.getPosition().setZ(this.acc.getZ());

		if (this.acc.getX() > 0.0f) {
			this.acc.x -= 0.01f;
		} else if (this.acc.getX() < 0.0f) {
			this.acc.x += 0.01f;
		}

		if (this.acc.getY() > 0.0f) {
			this.acc.y -= 0.01f;
		} else if (this.acc.getY() < 0.0f) {
			this.acc.y += 0.01f;
		}

		if (this.acc.getZ() > 0.0f) {
			this.acc.z -= 0.01f;
		} else if (this.acc.getZ() < 0.0f) {
			this.acc.z += 0.01f;
		}
	}

	public void walkForward() {
		this.acc.x -= walkingSpeed * (float) Math.sin(Math.toRadians(yaw));
		this.acc.z += walkingSpeed * (float) Math.cos(Math.toRadians(yaw));
	}

	public void walkBackwards() {
		this.acc.x += walkingSpeed * (float) Math.sin(Math.toRadians(yaw));
		this.acc.z -= walkingSpeed * (float) Math.cos(Math.toRadians(yaw));
	}

	public void strafeLeft() {
		this.acc.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw - 90));
		this.acc.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw - 90));
	}

	public void strafeRight() {
		this.acc.x -= walkingSpeed * (float)Math.sin(Math.toRadians(yaw + 90));
		this.acc.z += walkingSpeed * (float)Math.cos(Math.toRadians(yaw + 90));
	}
}