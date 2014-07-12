package com.continuum;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;

/**
 * The Player class encapsulates all functionality regarding the player.
 *
 * E.g. moving, gravity, placing blocks and so on.
 */
public class Player extends RenderObject {
	// How high the player can jump
	private static int JUMP_INTENSITY = 20;

	// Max. gravity
	private static int MAX_GRAVITY = 80;

	// Max. speed of the playering while walking
	private static int WALKING_SPEED = 8;

	// Height of the player in "blocks"
	private int PLAYER_HEIGHT = 1;

	// Viewing direction of the player
	private double yaw = 135d;
	private double pitch;

	private double accX;
	private double accZ;

	// Gravity (aka acceleration y)
	private float gravity;

	// The parent world
	private World parent = null;

	/**
	 * Init. the player
	 */
	public Player() {}

	/**
	 * Positions the player within the world
	 * and adjusts the player's view accordingly.
	 */
	@Override
	public void render() {
		// Rotate
		glRotatef((float) pitch, 1f, 0f, 0f);
		glRotatef((float) yaw, 0f, 1f, 0f);
		glTranslatef(-position.x, -position.y, -position.z);
	}

	/**
	 * Update the player position
	 *
	 * @param delta
	 */
	@Override
	public void update(long delta) {
		yaw(Mouse.getDX() * 0.1f);
		pitch(Mouse.getDY() * 0.1f);

		processMovement(delta);
		processPlayerInteraction();
	}

	/**
	 * Yaws the player's point of view.
	 *
	 * @param diff Amount of yawing to be applied.
	 */
	public void yaw(float diff) {
		yaw += diff;
	}

	/**
	 * Pitches the player's point of view.
	 *
	 * @param diff Amount of pitching to be applied.
	 */
	public void pitch(float diff) {
		pitch -= diff;
	}

	/**
	 * Moves the player forward.
	 */
	public void walkForward() {
		accX += (double) WALKING_SPEED * Math.sin(Math.toRadians(yaw));
		accZ -= WALKING_SPEED * Math.cos(Math.toRadians(yaw));
	}

	/*
	 * Moves the player backward.
	 */
	public void walkBackwards() {
		accX -= (double) WALKING_SPEED * Math.sin(Math.toRadians(yaw));
		accZ += (double) WALKING_SPEED * Math.cos(Math.toRadians(yaw));
	}

	/*
	 * Lets the player strafe left.
	 */
	public void strafeLeft() {
		accX += (double) WALKING_SPEED * Math.sin(Math.toRadians(yaw - 90));
		accZ -= (double) WALKING_SPEED * Math.cos(Math.toRadians(yaw - 90));
	}

	/*
	 * Lets the player strafe right.
	 */
	public void strafeRight() {
		accX += (double) WALKING_SPEED * Math.sin(Math.toRadians(yaw + 90));
		accZ -= (double) WALKING_SPEED * Math.cos(Math.toRadians(yaw + 90));
	}

	private boolean isPlayerStandingOnGround() {
		if (getParent() != null) {
			return getParent().isHitting(new Vector3f(getPosition().x, getPosition().y - PLAYER_HEIGHT, getPosition().z));
		} else {
			return false;
		}
	}

	private boolean isObjectInFrontOfPlayer() {

		if (getParent() != null) {
			Vector2f direction = new Vector2f((float) accX, (float) accZ);

			try {
				direction.normalise();
			} catch (Exception e) {
			}

			return getParent().isHitting(new Vector3f(getPosition().x + direction.x, getPosition().y - PLAYER_HEIGHT + 1, getPosition().z + direction.y));
		} else {
			return false;
		}
	}

	/**
	 * Lets the player jump. Yey.
	 */
	public void jump() {
		// Jumps are only possible, if the player is hitting the ground.
		if (isPlayerStandingOnGround()) {
			gravity = JUMP_INTENSITY;
		}
	}

	public Vector3f calcViewBlockPosition() {
		Vector3f blockPosition = new Vector3f(position);
		Vector2f viewingDirectionHorizontal = new Vector2f((float) Math.sin(Math.toRadians(yaw)), -1f * (float) Math.cos(Math.toRadians(yaw)));
		Vector2f viewingDirectionVertical = new Vector2f(-1f * (float) Math.sin(Math.toRadians(pitch)), (float) Math.cos(Math.toRadians(pitch)));

		blockPosition.x += 4f * viewingDirectionHorizontal.x;
		blockPosition.y += 4f * viewingDirectionVertical.x;
		blockPosition.z += 4f * viewingDirectionHorizontal.y;

		return blockPosition;
	}

	/**
	 * Places a block.
	 * TODO: Yeah... Should do more than that. :-)
	 */
	public void placeBlock() {
		if (getParent() != null) {
			Vector3f blockPosition = calcViewBlockPosition();

			getParent().setBlock(blockPosition, 0x2);
		}
	}

	/**
	 * Removes a block.
	 * TODO: Yeah... Should do more than that. :-)
	 */
	public void removeBlock() {
		if (getParent() != null) {
			Vector3f blockPosition = calcViewBlockPosition();

			getParent().setBlock(blockPosition, 0x0);
		}
	}

	private void processPlayerInteraction() {
		if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
			placeBlock();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			removeBlock();
		}
	}

	private void processMovement(long delta) {

		if (getParent() != null) {
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) {//reset position
				resetPlayer();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_T)) {//generate trees
				parent.generateTrees();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
			{
				walkForward();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
			{
				walkBackwards();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left
			{
				strafeLeft();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right
			{
				strafeRight();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				jump();
			}

            /*
             * Apply gravity.
             */
			if (!parent.isHitting(new Vector3f(getPosition().x, getPosition().y - PLAYER_HEIGHT, getPosition().z))) {
				if (gravity > -MAX_GRAVITY) {
					gravity -= 1;
				}
				getPosition().y += (gravity / 1000.0f) * delta;
			} else if (gravity > 0.0f) {
				getPosition().y += (gravity / 1000.0f) * delta;
			}

			/**
			 * Collision detection with objects along the x/z-plane.
			 */
			if (isObjectInFrontOfPlayer()) {
				accX = 0;
				accZ = 0;
			}

			getPosition().x += (accX / 1000.0f) * delta;
			getPosition().z += (accZ / 1000.0f) * delta;

			accX = 0;
			accZ = 0;

		}
	}

	public void resetPlayer() {
		position = Helper.getInstance().calcPlayerOrigin();
	}

	public World getParent() {
		return parent;
	}

	public void setParent(World parent) {
		this.parent = parent;
		this.resetPlayer();
	}
}