package com.continuum.world;

import com.continuum.main.Configuration;
import com.continuum.main.Continuum;
import com.continuum.rendering.Primitives;
import com.continuum.rendering.RenderableObject;
import com.continuum.rendering.ShaderManager;
import com.continuum.blocks.Block;
import com.continuum.generators.*;
import com.continuum.rendering.TextureManager;
import com.continuum.rendering.particles.BlockParticleEmitter;
import com.continuum.world.characters.Player;
import com.continuum.utilities.FastRandom;
import com.continuum.world.characters.Slime;
import com.continuum.world.chunk.Chunk;
import com.continuum.world.chunk.ChunkCache;
import com.continuum.world.chunk.ChunkMesh;
import com.continuum.world.chunk.ChunkUpdateManager;
import com.continuum.world.entity.Entity;
import com.continuum.world.horizon.Clouds;
import com.continuum.world.horizon.SunMoon;
import javolution.util.FastList;
import javolution.util.FastMap;
import javolution.util.FastSet;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.xml.sax.InputSource;

import java.io.*;
import java.util.Collections;
import java.util.logging.Level;

import static org.lwjgl.opengl.GL11.*;

/**
 * The world of Continuum. At its most basic the world contains chunks (consisting of a fixed amount of blocks)
 * and the player.
 * <p>
 * The world is randomly generated by using a bunch of Perlin noise generators initialized
 * with a favored seed value.
 */
public final class World implements RenderableObject {

	/* PLAYER */
	private Player _player;
	/* WORLD GENERATION */
	private final FastMap<String, ChunkGenerator> _chunkGenerators = new FastMap<String, ChunkGenerator>(32);
	private final FastMap<String, ObjectGenerator> _objectGenerators = new FastMap<String, ObjectGenerator>(32);
	/* ------ */
	private final FastRandom _random;
	/* PROPERTIES */
	private String _title, _seed;
	private Vector3f _spawningPoint;
	private double _time = Configuration.INITIAL_TIME;
	private long _lastDaytimeMeasurement = Continuum.getInstance().getTime();
	private double _daylight = 1.0f;
	/* RENDERING */
	private FastList<Chunk> _visibleChunks;
	/* UPDATING & CACHING */
	private final ChunkUpdateManager _chunkUpdateManager = new ChunkUpdateManager(this);
	private final ChunkCache _chunkCache = new ChunkCache(this);
	private boolean _updatingEnabled = false;
	private boolean _updateThreadAlive = true;
	private final Thread _updateThread;
	/* ENTITIES */
	private final FastList<Entity> _entities = new FastList<Entity>();
	/* PARTICLE EMITTERS */
	private final BlockParticleEmitter _blockParticleEmitter = new BlockParticleEmitter(this);
	/* HORIZON */
	private final Clouds _clouds;
	private final SunMoon _sunMoon;
	/* WATER AND LAVA ANIMATION */
	private int _textureAnimationTick = 0;
	private long _lastWaterAnimationTickUpdate;

	/**
	 * Initializes a new world for the single player mode.
	 *
	 * @param title The title/description of the world
	 * @param seed  The seed string used to generate the terrain
	 */
	public World(String title, String seed) {
		if (title == null) {
			throw new IllegalArgumentException("No title provided.");
		}

		if (title.isEmpty()) {
			throw new IllegalArgumentException("No title provided.");
		}

		if (seed == null) {
			throw new IllegalArgumentException("No seed provided.");
		}

		if (seed.isEmpty()) {
			throw new IllegalArgumentException("No seed provided.");
		}

		this._title = title;
		this._seed = seed;

		// If loading failed accept the given seed
		loadMetaData();

		// Init. horizon
		_clouds = new Clouds(this);
		_sunMoon = new SunMoon(this);

		// Init. etc.
		_lastWaterAnimationTickUpdate = Continuum.getInstance().getTime();

		// Init. generators
		_chunkGenerators.put("terrain", new ChunkGeneratorTerrain(_seed));
		_chunkGenerators.put("forest", new ChunkGeneratorForest(_seed));
		_chunkGenerators.put("resources", new ChunkGeneratorResources(_seed));
		_objectGenerators.put("tree", new ObjectGeneratorTree(this, _seed));
		_objectGenerators.put("pineTree", new ObjectGeneratorPineTree(this, _seed));
		_objectGenerators.put("firTree", new ObjectGeneratorFirTree(this, _seed));
		_objectGenerators.put("cactus", new ObjectGeneratorCactus(this, _seed));

		// Init. random generator
		_random = new FastRandom(seed.hashCode());
		_visibleChunks = new FastList<Chunk>();

		_updateThread = new Thread(new Runnable() {

			public void run() {
				while (true) {
					/*
                     * Checks if the thread should be killed.
                     */
					if (!_updateThreadAlive) {
						return;
					}

                    /*
                     * Puts the thread to sleep
                     * if updating is disabled.
                     */
					if (!_updatingEnabled) {
						synchronized (_updateThread) {
							try {
								_updateThread.wait();
							} catch (InterruptedException ex) {
								Continuum.getInstance().getLogger().log(Level.SEVERE, ex.toString());
							}
						}
					}

					_chunkUpdateManager.processChunkUpdates();
					_chunkCache.freeCacheSpace();
				}
			}
		});
	}

	/**
	 * Stops the updating thread and writes all chunks to disk.
	 */
	public void dispose() {
		Continuum.getInstance().getLogger().log(Level.INFO, "Disposing world {0} and saving all chunks.", _title);

		synchronized (_updateThread) {
			_updateThreadAlive = false;
			_updateThread.notify();
		}

		try {
			_updateThread.join();
		} catch (InterruptedException e) {

		}

		saveMetaData();
		_chunkCache.saveAndDisposeAllChunks();
	}

	/**
	 * Updates the time of the world. A day in Blockmania takes 12 minutes and the
	 * time is updated every 15 seconds.
	 */
	private void updateDaytime() {
		if (Continuum.getInstance().getTime() - _lastDaytimeMeasurement >= 100) {
			setTime(_time + 1f / ((5f * 60f * 10f)));
			_lastDaytimeMeasurement = Continuum.getInstance().getTime();
		}
	}

	/**
	 *
	 */
	private void updateDaylight() {
		// Sunrise
		if (_time < 0.1f && _time > 0.0f) {
			_daylight = _time / 0.1f;
		} else if (_time >= 0.1 && _time <= 0.5f) {
			_daylight = 1.0f;
		}

		// Sunset
		if (_time > 0.5f && _time < 0.6f) {
			_daylight = 1.0f - (_time - 0.5f) / 0.1f;
		} else if (_time >= 0.6f && _time <= 1.0f) {
			_daylight = 0.0f;
		}
	}

	/**
	 * Renders the world.
	 */
	public void render() {
		if (_player == null)
			return;

		if (!_player.isHeadUnderWater()) {
			/**
			 * Sky box.
			 */
			_player.applyNormalizedModelViewMatrix();

			glDisable(GL_CULL_FACE);
			glDisable(GL_DEPTH_TEST);

			glBegin(GL_QUADS);
			Primitives.drawSkyBox(getDaylight());
			glEnd();

			glEnable(GL_CULL_FACE);
			glEnable(GL_DEPTH_TEST);
		}

		_player.applyPlayerModelViewMatrix();

		_sunMoon.render();

		if (!_player.isHeadUnderWater())
			_clouds.render();

        /*
        * Render the world from the player's view.
        */
		_player.render();

		renderEntities();
		renderChunks();

		renderParticleEmitters();
	}

	private void renderParticleEmitters() {
		_blockParticleEmitter.render();
	}

	private void updateParticleEmitters() {
		_blockParticleEmitter.update();
	}

	private void renderEntities() {
		for (int i = 0; i < _entities.size(); i++) {
			_entities.get(i).render();
		}
	}

	private void updateEntities() {
		for (int i = 0; i < _entities.size(); i++) {
			_entities.get(i).update();
		}
	}

	private void updateVisibleChunks() {
		_visibleChunks.clear();

		for (int x = -(Configuration.getSettingNumeric("V_DIST_X").intValue() / 2); x < (Configuration.getSettingNumeric("V_DIST_X").intValue() / 2); x++) {
			for (int z = -(Configuration.getSettingNumeric("V_DIST_Z").intValue() / 2); z < (Configuration.getSettingNumeric("V_DIST_Z").intValue() / 2); z++) {

				Chunk c = _chunkCache.loadOrCreateChunk(calcPlayerChunkOffsetX() + x, calcPlayerChunkOffsetZ() + z);

				if (c != null) {
					if (c.isChunkInFrustum()) {
						_visibleChunks.add(c);
					}
				}
			}
		}
	}

	private void renderChunks() {

		ShaderManager.getInstance().enableShader("chunk");
		int daylight = GL20.glGetUniformLocation(ShaderManager.getInstance().getShader("chunk"), "daylight");
		int swimmimg = GL20.glGetUniformLocation(ShaderManager.getInstance().getShader("chunk"), "swimming");
		int animationOffset = GL20.glGetUniformLocation(ShaderManager.getInstance().getShader("chunk"), "animationOffset");
		int animationType = GL20.glGetUniformLocation(ShaderManager.getInstance().getShader("chunk"), "animationType");
		GL20.glUniform1f(daylight, (float) getDaylight());
		GL20.glUniform1i(animationType, 0);
		GL20.glUniform1i(swimmimg, _player.isHeadUnderWater() ? 1 : 0);

		glEnable(GL_TEXTURE_2D);

		updateVisibleChunks();

		// OPAQUE ELEMENTS
		for (FastSet.Record n = _visibleChunks.head(), end = _visibleChunks.tail(); (n = n.getNext()) != end; ) {
			Chunk c = _visibleChunks.valueOf(n);

			GL20.glUniform1i(animationType, 0);
			TextureManager.getInstance().bindTexture("terrain");
			c.render(ChunkMesh.RENDER_TYPE.OPAQUE);

			// Animated lava
			GL20.glUniform1i(animationType, 1);
			GL20.glUniform1f(animationOffset, ((float) (_textureAnimationTick % 16)) * (1.0f / 16f));
			TextureManager.getInstance().bindTexture("custom_lava_still");
			_visibleChunks.valueOf(n).render(ChunkMesh.RENDER_TYPE.LAVA);

			if (Configuration.getSettingBoolean("CHUNK_OUTLINES")) {
				c.getAABB().render();
			}
		}

		GL20.glUniform1i(animationType, 0);
		TextureManager.getInstance().bindTexture("terrain");

		// BILLBOARDS AND TRANSLUCENT ELEMENTS
		for (FastSet.Record n = _visibleChunks.head(), end = _visibleChunks.tail(); (n = n.getNext()) != end; ) {
			_visibleChunks.valueOf(n).render(ChunkMesh.RENDER_TYPE.BILLBOARD_AND_TRANSLUCENT);
		}

		GL20.glUniform1i(animationType, 1);

		for (int i = 0; i < 2; i++) {
			// ANIMATED WATER
			for (FastSet.Record n = _visibleChunks.head(), end = _visibleChunks.tail(); (n = n.getNext()) != end; ) {

				if (i == 0) {
					glColorMask(false, false, false, false);
				} else {
					glColorMask(true, true, true, true);
				}

				GL20.glUniform1f(animationOffset, ((float) (_textureAnimationTick / 2 % 12)) * (1.0f / 16f));
				TextureManager.getInstance().bindTexture("custom_water_still");
				_visibleChunks.valueOf(n).render(ChunkMesh.RENDER_TYPE.WATER);
			}
		}

		ShaderManager.getInstance().enableShader(null);
		glDisable(GL_TEXTURE_2D);
	}

	/**
	 * Update all dirty display lists.
	 */
	public void update() {
		updateDaytime();
		updateWaterLavaAnimationTick();

		_player.update();
		_chunkUpdateManager.updateVBOs();

		_clouds.update();

		for (FastSet.Record n = _visibleChunks.head(), end = _visibleChunks.tail(); (n = n.getNext()) != end; )
			_visibleChunks.valueOf(n).update();

		updateEntities();
		_chunkCache.disposeUnusedChunks();

		updateParticleEmitters();
	}

	private void updateWaterLavaAnimationTick() {
		if (Continuum.getInstance().getTime() - _lastWaterAnimationTickUpdate >= 200) {
			_textureAnimationTick++;
			_lastWaterAnimationTickUpdate = Continuum.getInstance().getTime();
		}
	}

	/**
	 * Returns the chunk position of a given coordinate.
	 *
	 * @param x The X-coordinate of the block
	 * @return The X-coordinate of the chunk
	 */
	public int calcChunkPosX(int x) {
		// Offset for negative chunks
		if (x < 0)
			x -= 15;

		return (x / (int) Configuration.CHUNK_DIMENSIONS.x);
	}

	/**
	 * Returns the chunk position of a given coordinate.
	 *
	 * @param z The Z-coordinate of the block
	 * @return The Z-coordinate of the chunk
	 */
	public int calcChunkPosZ(int z) {
		// Offset for negative chunks
		if (z < 0)
			z -= 15;

		return (z / (int) Configuration.CHUNK_DIMENSIONS.z);
	}

	/**
	 * Returns the internal position of a block within a chunk.
	 *
	 * @param x1 The X-coordinate of the block within the world
	 * @param x2 The X-coordinate of the chunk within the world
	 * @return The X-coordinate of the block within the chunk
	 */
	public int calcBlockPosX(int x1, int x2) {
		return Math.abs(x1 - (x2 * (int) Configuration.CHUNK_DIMENSIONS.x));
	}

	/**
	 * Returns the internal position of a block within a chunk.
	 *
	 * @param z1 The Z-coordinate of the block within the world
	 * @param z2 The Z-coordinate of the chunk within the world
	 * @return The Z-coordinate of the block within the chunk
	 */
	public int calcBlockPosZ(int z1, int z2) {
		return Math.abs(z1 - (z2 * (int) Configuration.CHUNK_DIMENSIONS.z));
	}

	/**
	 * Places a block of a specific type at a given position and refreshes the
	 * corresponding light values.
	 *
	 * @param x           The X-coordinate
	 * @param y           The Y-coordinate
	 * @param z           The Z-coordinate
	 * @param type        The type of the block to set
	 * @param updateLight If set the affected chunk is queued for updating
	 * @param overwrite
	 */
	public final boolean setBlock(int x, int y, int z, byte type, boolean updateLight, boolean overwrite) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));

		if (c == null) {
			return false;
		}

		if (overwrite || c.getBlock(blockPosX, y, blockPosZ) == 0x0) {

			byte oldBlock = c.getBlock(blockPosX, y, blockPosZ);
			byte newBlock = oldBlock;

			if (Block.getBlockForType(c.getBlock(blockPosX, y, blockPosZ)).isRemovable()) {
				c.setBlock(blockPosX, y, blockPosZ, type);
				newBlock = type;
			} else {
				return false;
			}

			if (updateLight) {

                /*
                * Update sunlight.
                */
				c.refreshSunlightAtLocalPos(blockPosX, blockPosZ, true, true);

				byte blockLightPrev = getLight(x, y, z, Chunk.LIGHT_TYPE.BLOCK);
				byte blockLightCurrent = blockLightPrev;

				// New block placed
				if (oldBlock == 0x0 && newBlock != 0x0) {
                    /*
                    * Spread light of block light sources.
                    */
					byte luminance = Block.getBlockForType(type).getLuminance();

					// Set the block light value to the luminance of this block
					c.setLight(blockPosX, y, blockPosZ, luminance, Chunk.LIGHT_TYPE.BLOCK);
					blockLightCurrent = luminance;
				} else { // Block removed
                    /*
                    * Update the block light intensity of the current block.
                    */
					c.setLight(blockPosX, y, blockPosZ, (byte) 0x0, Chunk.LIGHT_TYPE.BLOCK);
					c.refreshLightAtLocalPos(blockPosX, y, blockPosZ, Chunk.LIGHT_TYPE.BLOCK);
					blockLightCurrent = getLight(x, y, z, Chunk.LIGHT_TYPE.BLOCK);
				}

				// Block light is brighter than before
				if (blockLightCurrent > blockLightPrev) {
					c.spreadLight(blockPosX, y, blockPosZ, blockLightCurrent, Chunk.LIGHT_TYPE.BLOCK);
				} else if (blockLightCurrent < blockLightPrev) { // Block light is darker than before
					c.unspreadLight(blockPosX, y, blockPosZ, blockLightPrev, Chunk.LIGHT_TYPE.BLOCK);
				}
			}
		}

		return true;
	}

	/**
	 * @param pos
	 * @return
	 */
	public final byte getBlockAtPosition(Vector3f pos) {
		return getBlock((int) (pos.x + ((pos.x >= 0) ? 0.5f : -0.5f)), (int) (pos.y + ((pos.y >= 0) ? 0.5f : -0.5f)), (int) (pos.z + ((pos.z >= 0) ? 0.5f : -0.5f)));
	}

	/**
	 *
	 * @param pos
	 * @param type
	 * @return
	 */
	public final byte getLightAtPosition(Vector3f pos, Chunk.LIGHT_TYPE type) {
		return getLight((int) (pos.x + ((pos.x >= 0) ? 0.5f : -0.5f)), (int) (pos.y + ((pos.y >= 0) ? 0.5f : -0.5f)), (int) (pos.z + ((pos.z >= 0) ? 0.5f : -0.5f)), type);
	}

	/**
	 * Returns the block at the given position.
	 *
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 * @return The type of the block
	 */
	public final byte getBlock(int x, int y, int z) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));

		if (c != null) {
			return c.getBlock(blockPosX, y, blockPosZ);
		}

		return 0;
	}

	/**
	 * Returns true if the block is surrounded by blocks within the N4-neighborhood on the xz-plane.
	 *
	 * @param x The X-coordinate
	 * @param y The Y-coordinate
	 * @param z The Z-coordinate
	 * @return
	 */
	@SuppressWarnings({"UnusedDeclaration"})
	public final boolean isBlockSurrounded(int x, int y, int z) {
		return (getBlock(x + 1, y, z) > 0 || getBlock(x - 1, y, z) > 0 || getBlock(x, y, z + 1) > 0 || getBlock(x, y, z - 1) > 0);
	}

	/**
	 * @param x
	 * @param z
	 * @return
	 */
	public final int maxHeightAt(int x, int z) {
		for (int y = (int) Configuration.CHUNK_DIMENSIONS.y - 1; y >= 0; y--) {
			if (getBlock(x, y, z) != 0x0)
				return y;
		}

		return 0;
	}

	/**
	 * Returns the light value at the given position.
	 *
	 * @param x    The X-coordinate
	 * @param y    The Y-coordinate
	 * @param z    The Z-coordinate
	 * @param type
	 * @return The light value
	 */
	public final byte getLight(int x, int y, int z, Chunk.LIGHT_TYPE type) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));

		if (c != null) {
			return c.getLight(blockPosX, y, blockPosZ, type);
		}

		if (type == Chunk.LIGHT_TYPE.SUN)
			return 15;
		else
			return 0;
	}

	/**
	 * Sets the light value at the given position.
	 *
	 * @param x      The X-coordinate
	 * @param y      The Y-coordinate
	 * @param z      The Z-coordinate
	 * @param intens The light intensity value
	 * @param type
	 */
	public void setLight(int x, int y, int z, byte intens, Chunk.LIGHT_TYPE type) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));

		if (c != null) {
			c.setLight(blockPosX, y, blockPosZ, intens, type);
		}
	}

	/**
	 * Refreshes sunlight vertically at a given global position.
	 *
	 * @param x
	 * @param spreadLight
	 * @param refreshSunlight
	 * @param z
	 */
	public void refreshSunlightAt(int x, int z, boolean spreadLight, boolean refreshSunlight) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));

		if (c != null) {
			c.refreshSunlightAtLocalPos(blockPosX, blockPosZ, spreadLight, refreshSunlight);
		}
	}

	/**
	 * Recursive light calculation.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param lightValue
	 * @param depth
	 * @param type
	 */
	public void unspreadLight(int x, int y, int z, byte lightValue, int depth, Chunk.LIGHT_TYPE type, FastList<Vector3f> brightSpots) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));
		if (c != null) {
			c.unspreadLight(blockPosX, y, blockPosZ, lightValue, depth, type, brightSpots);
		}
	}

	/**
	 * Recursive light calculation.
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param lightValue
	 * @param depth
	 * @param type
	 */
	public void spreadLight(int x, int y, int z, byte lightValue, int depth, Chunk.LIGHT_TYPE type) {
		int chunkPosX = calcChunkPosX(x);
		int chunkPosZ = calcChunkPosZ(z);

		int blockPosX = calcBlockPosX(x, chunkPosX);
		int blockPosZ = calcBlockPosZ(z, chunkPosZ);

		Chunk c = _chunkCache.loadOrCreateChunk(calcChunkPosX(x), calcChunkPosZ(z));
		if (c != null) {
			c.spreadLight(blockPosX, y, blockPosZ, lightValue, depth, type);
		}
	}

	/**
	 * Returns the daylight value.
	 *
	 * @return The daylight value
	 */
	public double getDaylight() {
		return _daylight;
	}

	/**
	 * Returns the player.
	 *
	 * @return The player
	 */
	public Player getPlayer() {
		return _player;
	}

	public void setPlayer(Player p) {
		_player = p;
		// Reset the player's position
		resetPlayer();
	}

	/**
	 * Calculates the offset of the player relative to the spawning point.
	 *
	 * @return The player offset on the x-axis
	 */
	private int calcPlayerChunkOffsetX() {
		return (int) (_player.getPosition().x / Configuration.CHUNK_DIMENSIONS.x);
	}

	/**
	 * Calculates the offset of the player relative to the spawning point.
	 *
	 * @return The player offset on the z-axis
	 */
	private int calcPlayerChunkOffsetZ() {
		return (int) (_player.getPosition().z / Configuration.CHUNK_DIMENSIONS.z);
	}


	/**
	 * Displays some information about the world formatted as a string.
	 *
	 * @return String with world information
	 */
	@Override
	public String toString() {
		return String.format("world (cdl: %d, cn: %d, cache: %d, ud: %fs, seed: \"%s\", title: \"%s\")", _chunkUpdateManager.getVboUpdatesSize(), _chunkUpdateManager.getUpdatesSize(), _chunkCache.size(), _chunkUpdateManager.getMeanUpdateDuration() / 1000d, _seed, _title);
	}

	/**
	 * Starts the updating thread.
	 */
	public void startUpdateThread() {
		_updatingEnabled = true;
		_updateThread.start();
	}

	/**
	 * Resumes the updating thread.
	 */
	public void resumeUpdateThread() {
		_updatingEnabled = true;
		synchronized (_updateThread) {
			_updateThread.notify();
		}
	}

	/**
	 * Safely suspends the updating thread.
	 */
	public void suspendUpdateThread() {
		_updatingEnabled = false;
	}

	/**
	 * Sets the time of the world.
	 *
	 * @param time The time to set
	 */
	public void setTime(double time) {
		_time = time;

		if (_time < 0) {
			_time = 1.0f;
		} else if (_time > 1.0f) {
			_time = 0.0f;
		}

		updateDaylight();
	}

	public ObjectGenerator getObjectGenerator(String s) {
		return _objectGenerators.get(s);
	}

	public ChunkGenerator getChunkGenerator(String s) {
		return _chunkGenerators.get(s);
	}

	/**
	 * Returns true if it is daytime.
	 *
	 * @return
	 */
	public boolean isDaytime() {
		return _time > 0.075f && _time < 0.575;
	}

	/**
	 * Returns true if it is nighttime.
	 *
	 * @return
	 */
	public boolean isNighttime() {
		return !isDaytime();
	}

	/**
	 * @param x
	 * @param z
	 * @return
	 */
	public Chunk prepareNewChunk(int x, int z) {
		FastList<ChunkGenerator> gs = new FastList<ChunkGenerator>();
		gs.add(getChunkGenerator("terrain"));
		gs.add(getChunkGenerator("resources"));
		gs.add(getChunkGenerator("forest"));

		// Generate a new chunk and return it
		return new Chunk(this, new Vector3f(x, 0, z), gs);
	}

	/**
	 * @return
	 */
	private Vector3f findSpawningPoint() {
		for (; ; ) {
			int randX = (int) (_random.randomDouble() * 16000f);
			int randZ = (int) (_random.randomDouble() * 16000f);

			double dens = ((ChunkGeneratorTerrain) getChunkGenerator("terrain")).calcDensity(randX, 32, randZ, ChunkGeneratorTerrain.BIOME_TYPE.PLAINS);

			if (dens >= 0.008 && dens < 0.02)
				return new Vector3f(randX, 128, randZ);
		}
	}

	/**
	 * Sets the spawning point to the player's current position.
	 */
	public void setSpawningPoint() {
		_spawningPoint = new Vector3f(_player.getPosition());
	}

	/**
	 *
	 */
	public void resetPlayer() {
		if (_spawningPoint == null) {
			_spawningPoint = findSpawningPoint();
			_player.resetEntity();
			_player.setPosition(_spawningPoint);
		} else {
			_player.resetEntity();
			_player.setPosition(_spawningPoint);
		}
	}

	/**
	 * @return
	 */
	public String getWorldSavePath() {
		return String.format("SAVED_WORLDS/%s", _title);

	}

	/**
	 * @return
	 */
	private boolean saveMetaData() {
		if (Continuum.getInstance().isSandboxed()) {
			return false;
		}

		// Generate the save directory if needed
		File dir = new File(getWorldSavePath());
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				Continuum.getInstance().getLogger().log(Level.SEVERE, "Could not create save directory.");
				return false;
			}
		}

		File f = new File(String.format("%s/Metadata.xml", getWorldSavePath()));

		try {
			f.createNewFile();
		} catch (IOException ex) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
		}

		Element root = new Element("World");
		Document doc = new Document(root);

		// Save the world metadata
		root.setAttribute("seed", _seed);
		root.setAttribute("title", _title);
		root.setAttribute("time", Double.toString(_time));

		// Save the player metadata
		Element player = new Element("Player");
		player.setAttribute("x", Float.toString(_player.getPosition().x));
		player.setAttribute("y", Float.toString(_player.getPosition().y));
		player.setAttribute("z", Float.toString(_player.getPosition().z));
		root.addContent(player);


		XMLOutputter outputter = new XMLOutputter();
		FileOutputStream output;

		try {
			output = new FileOutputStream(f);

			try {
				outputter.output(doc, output);
			} catch (IOException ex) {
				Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
			}

			return true;
		} catch (FileNotFoundException ex) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
		}


		return false;
	}

	/**
	 * @return
	 */
	private boolean loadMetaData() {
		if (Continuum.getInstance().isSandboxed()) {
			return false;
		}

		File f = new File(String.format("%s/Metadata.xml", getWorldSavePath()));

		try {
			SAXBuilder sxbuild = new SAXBuilder();
			InputSource is = new InputSource(new FileInputStream(f));
			Document doc;
			try {
				doc = sxbuild.build(is);
				Element root = doc.getRootElement();
				Element player = root.getChild("Player");

				_seed = root.getAttribute("seed").getValue();
				_spawningPoint = new Vector3f(Float.parseFloat(player.getAttribute("x").getValue()), Float.parseFloat(player.getAttribute("y").getValue()), Float.parseFloat(player.getAttribute("z").getValue()));
				_title = root.getAttributeValue("title");
				setTime(Float.parseFloat(root.getAttributeValue("time")));

				return true;

			} catch (JDOMException ex) {
				Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
			}

		} catch (FileNotFoundException ex) {
			// Metadata.xml not present
		}

		return false;
	}

	/**
	 * @return
	 */
	public ChunkCache getChunkCache() {
		return _chunkCache;
	}

	/**
	 * @return
	 */
	public ChunkUpdateManager getChunkUpdateManager() {
		return _chunkUpdateManager;
	}

	public FastList<Chunk> getVisibleChunks() {
		return _visibleChunks;
	}

	public BlockParticleEmitter getBlockParticleEmitter() {
		return _blockParticleEmitter;
	}

	public FastRandom getRandom() {
		return _random;
	}
}