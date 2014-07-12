package com.continuum;

import org.lwjgl.util.vector.Vector3f;
import static org.lwjgl.opengl.GL11.*;
import java.util.Random;

/**
 * World class
 *
 * Created by gil0mendes on 12/07/14.
 */
public class World extends RenderObject
{
	// The title of the world
	private String title = "World 1";

	// The seed used for terrain generation
	private String seed = "CONTINUUM";

	// The chunks to display
	Chunk[][][] chunks;

	// Random number generator
	private Random rand = new Random();

	// Noise generator
	private PerlinNoiseGenerator pGen = new PerlinNoiseGenerator();

	// World dimensions
	private static final Vector3f worldDimensions = new Vector3f(1024, 256, 1024);

	// Time since last chunk update
	long timeSinceLastChunkUpdate = 0;

	// Player
	private Player player = null;

	public World() {
		chunks = new Chunk[(int) Configuration.viewingDistanceInChunks.x][(int) Configuration.viewingDistanceInChunks.y][(int) Configuration.viewingDistanceInChunks.z];

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				updateWorld();
			}
		});

		t.start();
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslatef(-512f, 0.0f, -512f);

		for (int x = 0; x < (int) Configuration.viewingDistanceInChunks.x; x++) {
			for (int y = 0; y < (int) Configuration.viewingDistanceInChunks.y; y++) {
				for (int z = 0; z < (int) Configuration.viewingDistanceInChunks.z; z++) {
					if (chunks[x][y][z] != null) {

						if (System.currentTimeMillis() - timeSinceLastChunkUpdate > 0) {
							if (chunks[x][y][z].updateDisplayList()) {
								timeSinceLastChunkUpdate = System.currentTimeMillis();
							}
						}

						// Render active chunks only
						chunks[x][y][z].render();
					}
				}
			}
		}

		glPopMatrix();
	}

	/**
	 * Update world
	 */
	public final void updateWorld() {
		long timeStart = System.currentTimeMillis();

		for (int x = 0; x < worldDimensions.x; x++) {
			for (int z = 0; z < worldDimensions.z; z++) {
				for (int y = 0; y < 32; y++) {
					if (pGen.getCaveDensityAt(x, y, z) < 0.25) {
						setBlock(new Vector3f(x, y, z), 0x3);
					}
				}
			}
		}

		for (int x = 0; x < worldDimensions.x; x++) {
			for (int z = 0; z < worldDimensions.z; z++) {

				float height = Math.abs(pGen.getTerrainHeightAt(x / 2.0f, z / 2.0f) * 256.0f + 64.0f);

				float y = height;

				while (y > 0) {
					if (pGen.getCaveDensityAt(x, y, z) < 0.5) {
						if (height == y) {
							setBlock(new Vector3f(x, y, z), 0x1);
						} else {
							setBlock(new Vector3f(x, y, z), 0x2);
						}
					}
					y--;
				}
			}
		}

		System.out.println("World updated (" + (System.currentTimeMillis() - timeStart) / 1000d + "s).");
	}

	public final void setBlock(Vector3f pos, int type) {
		Vector3f chunkPos = new Vector3f((float) Math.floor(pos.x / Chunk.chunkDimensions.x), (float) Math.floor(pos.y / Chunk.chunkDimensions.y), (float) Math.floor(pos.z / Chunk.chunkDimensions.z));
		Vector3f blockCoord = new Vector3f(pos.x - (chunkPos.x * Chunk.chunkDimensions.x), pos.y - (chunkPos.y * Chunk.chunkDimensions.y), pos.z - (chunkPos.z * Chunk.chunkDimensions.z));

		Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];

		// Create a new chunk if needed
		if (c == null) {
			//System.out.println("Generating chunk at X: " + chunkPos.x + ", Y: " + chunkPos.y + ", Z: " + chunkPos.z);
			c = new Chunk(this, new Vector3f(chunkPos.x, chunkPos.y, chunkPos.z));
			chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z] = c;
		}

		// Generate or update the corresponding chunk
		c.setBlock(blockCoord, type);
	}

	public final int getBlock(Vector3f pos) {
		Vector3f chunkPos = new Vector3f((float) Math.floor(pos.x / Chunk.chunkDimensions.x), (float) Math.floor(pos.y / Chunk.chunkDimensions.y), (float) Math.floor(pos.z / Chunk.chunkDimensions.z));
		Vector3f blockCoord = new Vector3f(pos.x - (chunkPos.x * Chunk.chunkDimensions.x), pos.y - (chunkPos.y * Chunk.chunkDimensions.y), pos.z - (chunkPos.z * Chunk.chunkDimensions.z));

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];
			return c.getBlock(blockCoord);
		} catch (Exception e) {
			return 0;
		}
	}

	public boolean isHitting(Vector3f pos) {
		pos.x += 512;
		pos.z += 512;

		Vector3f chunkPos = new Vector3f((float) Math.floor(pos.x / Chunk.chunkDimensions.x), (float) Math.floor(pos.y / Chunk.chunkDimensions.y), (float) Math.floor(pos.z / Chunk.chunkDimensions.z));
		Vector3f blockCoord = new Vector3f(pos.x - (chunkPos.x * Chunk.chunkDimensions.x), pos.y - (chunkPos.y * Chunk.chunkDimensions.y), pos.z - (chunkPos.z * Chunk.chunkDimensions.z));

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];
			return (c.getBlock(blockCoord) > 0);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Set player
	 *
	 * @param player The player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
}
