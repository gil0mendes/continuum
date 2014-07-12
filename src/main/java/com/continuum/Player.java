package com.continuum;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * Player class
 *
 * Created by gil0mendes on 12/07/14.
 */
public class Player extends RenderObject {
	// Viewing direction of the player
	private float yaw = 0.0f;
	private float pitch = 0.0f;

	// Walking speed
	private float walkingSpeed = 1.0f;

	// Accelaration
	private Vector3f acc = new Vector3f(0.0f, 0.0f, 0.0f);

	// Gravity
	private float gravity = 0.0f;

	// The parent world
	private World parent = null;

	/**
	 * Player constructor
	 *
	 * @param parent Parent world
	 */
	public Player(World parent) {
		this.parent = parent;
		this.position = new Vector3f(0.0f, -100.0f, 0.0f);
	}

	/**
	 * Positions the player within the world
	 * and adjusts the players view accordingly.
	 */
	@Override
	public void render() {
		// Rotate
		glRotatef(pitch, 1.0f, 0.0f, 0.0f);
		glRotatef(yaw, 0.0f, 1.0f, 0.0f);
		glTranslatef(-position.x, -position.y, -position.z);
	}

	@Override
	public void update() {
		// Update view direction
		this.yaw(Mouse.getDX());
		this.pitch(-1 * Mouse.getDY());

		this.getPosition().y += this.acc.y + (this.gravity * Helper.getInstance().calcInterpolation());

		// Check if player is hitting something
		if (!this.parent.isHitting(new Vector3f(this.getPosition().x, this.getPosition().y - 4.0f, this.getPosition().z))) {
			if (this.gravity > -1.0f) {
				this.gravity -= 0.1f;
			}
		} else {
			this.gravity = 0.0f;
		}
	}

	public void yaw(float diff) {
		yaw += diff * Helper.getInstance().calcInterpolation();
	}

	public void pitch(float diff) {
		pitch += diff * Helper.getInstance().calcInterpolation();
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

	/**
	 * Jump
	 */
	public void jump() {
		// Check if its hitting something
		if (parent.isHitting(new Vector3f(getPosition().x, getPosition().y - 4.0f, getPosition().z))) {
			if (this.gravity >= 0) {
				this.gravity += 1.0f;
			}
		}
	}
}