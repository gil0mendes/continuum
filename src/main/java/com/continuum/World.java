package com.continuum;

import org.lwjgl.util.vector.Vector3f;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * World class
 * <p/>
 * Created by gil0mendes on 12/07/14.
 */
public class World extends RenderObject {
	// Daylight intensity
	private float daylight = 0.9f;

	private Random rand;

	// Used for updating/generating the world
	private Thread updateThread = null;

	// The chunks to display
	private Chunk[][][] chunks;
	private Set<Chunk> chunkUpdateQueue = new HashSet<Chunk>();

	// Logger
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	// Player
	private Player player = null;

	/**
	 * Init. world
	 *
	 * @param title
	 * @param seed
	 */
	public World(String title, String seed, Player p) {
		this.player = p;
		rand = new Random(seed.hashCode());
		final World currentWorld = this;

		chunks = new Chunk[(int) Configuration.viewingDistanceInChunks.x][(int) Configuration.viewingDistanceInChunks.y][(int) Configuration.viewingDistanceInChunks.z];

		updateThread = new Thread(new Runnable() {

			@Override
			public void run() {

				long timeStart = System.currentTimeMillis();

				LOGGER.log(Level.INFO, "Generating chunks.");

				for (int x = 0; x < Configuration.viewingDistanceInChunks.x; x++) {
					for (int y = 0; y < Configuration.viewingDistanceInChunks.y; y++) {
						for (int z = 0; z < Configuration.viewingDistanceInChunks.z; z++) {
							Chunk c = new Chunk(currentWorld, new Vector3f(x, y, z));
							chunks[x][y][z] = c;
							c.calcSunlight();
						}
					}
				}

				LOGGER.log(Level.INFO, "World updated ({0}s).", (System.currentTimeMillis() - timeStart) / 1000d);

				for (int x = 0; x < Configuration.viewingDistanceInChunks.x; x++) {
					for (int y = 0; y < Configuration.viewingDistanceInChunks.y; y++) {
						for (int z = 0; z < Configuration.viewingDistanceInChunks.z; z++) {
							Chunk c = chunks[x][y][z];
							c.generateVertexArray();
							addChunkToUpdateQueue(c);
						}
					}
				}

				while (true) {
					for (int x = 0; x < Configuration.viewingDistanceInChunks.x; x++) {
						for (int y = 0; y < Configuration.viewingDistanceInChunks.y; y++) {
							for (int z = 0; z < Configuration.viewingDistanceInChunks.z; z++) {
								Chunk c = chunks[x][y][z];
								if (c.dirty) {
									c.calcSunlight();
									c.generateVertexArray();
								}
							}
						}
					}
				}
			}
		});

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				daylight -= 0.25f;

				if (daylight < 0.25f) {
					daylight = 0.9f;
				}

				for (int x = 0; x < Configuration.viewingDistanceInChunks.x; x++) {
					for (int y = 0; y < Configuration.viewingDistanceInChunks.y; y++) {
						for (int z = 0; z < Configuration.viewingDistanceInChunks.z; z++) {
							Chunk c = chunks[x][y][z];
							c.dirty = true;
							addChunkToUpdateQueue(c);
						}
					}
				}
			}
		}, 60000, 20000);
	}

	public synchronized void addChunkToUpdateQueue(Chunk c) {
		chunkUpdateQueue.add(c);
	}

	private synchronized void removeChunkFromQueue(Chunk c) {
		chunkUpdateQueue.remove(c);
	}

	public void init() {
		updateThread.start();
	}

	@Override
	public void render() {
		for (int x = 0; x < Configuration.viewingDistanceInChunks.x; x++) {
			for (int y = 0; y < Configuration.viewingDistanceInChunks.y; y++) {
				for (int z = 0; z < Configuration.viewingDistanceInChunks.z; z++) {
					Chunk c = chunks[x][y][z];
					if (c != null) {
						c.render();
					}
				}
			}
		}
	}

	/*
	 * Update the world.
	 */
	@Override
	public synchronized void update(long delta) {

		int chunkUpdates = 0;
		//LOGGER.log(Level.INFO, "Updating {0} chunks.", chunkUpdateQueue.size());

		List<Chunk> deletedElements = new ArrayList<Chunk>();

		for (Chunk c : chunkUpdateQueue) {
			if (chunkUpdates < 16) {
				if (!c.dirty) {
					c.generateDisplayList();

					deletedElements.add(c);
					chunkUpdates++;
				}
			} else {
				break;
			}
		}

		for (Chunk c : deletedElements) {
			removeChunkFromQueue(c);
		}
	}

	public void generateTrees() {
		for (int x = 0; x < Configuration.viewingDistanceInChunks.x * Chunk.chunkDimensions.x; x++) {
			for (int y = 0; y < Configuration.viewingDistanceInChunks.y * Chunk.chunkDimensions.y; y++) {
				for (int z = 0; z < Configuration.viewingDistanceInChunks.z * Chunk.chunkDimensions.z; z++) {
					if (getBlock(new Vector3f(x, y, z)) == 0x1) {
						if (rand.nextFloat() > 0.998f) {
							generateTree(new Vector3f(x, y + 1, z));
						}
					}
				}
			}
		}
	}

	private void generateTree(Vector3f pos) {
		int height = rand.nextInt() % 4 + 6;

		// Generate tree trunk
		for (int i = 0; i < height; i++) {
			setBlock(new Vector3f(pos.x, pos.y + i, pos.z), 0x5);
		}

		// Generate the treetop
		for (int y = height - 2; y < height + 2; y++) {
			for (int x = -2; x <= 2; x++) {
				for (int z = -2; z <= 2; z++) {
					if (rand.nextFloat() < 0.95 && !(x == 0 && z == 0)) {
						setBlock(new Vector3f(pos.x + x, pos.y + y, pos.z + z), 0x6);
					}
				}
			}
		}
	}

	/**
	 * Sets the type of a block at a given position.
	 *
	 * @param pos
	 * @param type
	 */
	public final void setBlock(Vector3f pos, int type) {
		Vector3f chunkPos = calcChunkPos(pos);
		Vector3f blockCoord = calcBlockPos(pos, chunkPos);

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];

			// Create a new chunk if needed
			if (c == null) {
				c = new Chunk(this, new Vector3f(chunkPos.x, chunkPos.y, chunkPos.z));
				//LOGGER.log(Level.INFO, "Generating chunk at X: {0}, Y: {1}, Z: {2}", new Object[]{chunkPos.x, chunkPos.y, chunkPos.z});
				chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z] = c;
			}

			// Generate or update the corresponding chunk
			c.setBlock(blockCoord, type, true);
			chunkUpdateQueue.add(c);
		} catch (Exception e) {
			return;
		}
	}

	/**
	 * Returns a block at a position by looking up the containing chunk.
	 */
	public final int getBlock(Vector3f pos) {
		Vector3f chunkPos = calcChunkPos(pos);
		Vector3f blockCoord = calcBlockPos(pos, chunkPos);

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];
			return c.getBlock((int) blockCoord.x, (int) blockCoord.y, (int) blockCoord.z);
		} catch (Exception e) {
			return -1;
		}
	}

	public final float getLight(Vector3f pos) {
		Vector3f chunkPos = calcChunkPos(pos);
		Vector3f blockCoord = calcBlockPos(pos, chunkPos);

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];
			return c.getLight((int) blockCoord.x, (int) blockCoord.y, (int) blockCoord.z);
		} catch (Exception ex) {
			return 0.0f;
		}
	}

	/**
	 * Returns true if the given position is hitting a block below.
	 */
	public boolean isHitting(Vector3f pos) {
		Vector3f chunkPos = calcChunkPos(pos);
		Vector3f blockCoord = calcBlockPos(pos, chunkPos);

		try {
			Chunk c = chunks[(int) chunkPos.x][(int) chunkPos.y][(int) chunkPos.z];
			return (c.getBlock((int) blockCoord.x, (int) blockCoord.y, (int) blockCoord.z) > 0);
		} catch (Exception e) {
			return false;
		}

	}

	/**
	 * Calculate the corresponding chunk for a given position within the world.
	 */
	private Vector3f calcChunkPos(Vector3f pos) {
		return new Vector3f((int) (pos.x / Chunk.chunkDimensions.x), (int) (pos.y / Chunk.chunkDimensions.y), (int) (pos.z / Chunk.chunkDimensions.z));
	}

	/**
	 * Calculate the position of a world-block within a specific chunk.
	 */
	private Vector3f calcBlockPos(Vector3f pos, Vector3f chunkPos) {
		return new Vector3f(pos.x - (chunkPos.x * Chunk.chunkDimensions.x), pos.y - (chunkPos.y * Chunk.chunkDimensions.y), pos.z - (chunkPos.z * Chunk.chunkDimensions.z));
	}

	public float getDaylight() {
		return daylight;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
}