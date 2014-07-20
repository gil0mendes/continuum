package com.continuum.world;

import com.continuum.main.Configuration;
import com.continuum.utilities.BlockMath;
import com.continuum.utilities.Helper;
import javolution.util.FastList;

import java.util.Collections;
import java.util.TreeMap;
import java.util.logging.Level;

public final class ChunkCache {

	private final TreeMap<Integer, Chunk> _chunkCache = new TreeMap<Integer, Chunk>();
	private final World _parent;

	/**
	 * @param _parent
	 */
	public ChunkCache(World _parent) {
		this._parent = _parent;
	}

	/**
	 * Loads a specified chunk from cache or queues a new chunk for generation.
	 * <p/>
	 * NOTE: This method ALWAYS returns a valid chunk (if positive x and z values are provided)
	 * since a new chunk is generated if none of the present chunks fit the request.
	 *
	 * @param x X-coordinate of the chunk
	 * @param z Z-coordinate of the chunk
	 * @return The chunk
	 */
	public Chunk loadOrCreateChunk(int x, int z) {
		// Catch negative values
		if (x < 0 || z < 0) {
			return null;
		}

		// Try to load the chunk from the cache
		Chunk c = _chunkCache.get(BlockMath.cantorize(x, z));

		// We got a chunk! Already! Great!
		if (c != null) {
			return c;
		}

		// Delete some elements if the cache size is exceeded
		if (_chunkCache.size() > capacity()) {
			// Fetch all chunks within the cache
			FastList<Chunk> sortedChunks;
			sortedChunks = new FastList<Chunk>(_chunkCache.values());
			// Sort them according to their distance to the player
			Collections.sort(sortedChunks);

			Helper.LOGGER.log(Level.FINE, "Cache full. Removing some chunks from the chunk cache...");

			// Free some space
			for (int i = 0; i < 32; i++) {
				int indexToDelete = sortedChunks.size() - i;

				if (indexToDelete >= 0 && indexToDelete < sortedChunks.size()) {
					Chunk cc = sortedChunks.get(indexToDelete);
					// Save the chunk before removing it from the cache
					_chunkCache.remove(BlockMath.cantorize((int) cc.getPosition().x, (int) cc.getPosition().z));
					cc.dispose();
				}
			}

			Helper.LOGGER.log(Level.FINE, "Finished removing chunks from the chunk cache.");
		}

		// Init a new chunk
		c = _parent.prepareNewChunk(x, z);
		_chunkCache.put(BlockMath.cantorize(x, z), c);

		return c;
	}

	/**
	 * Returns true if the given chunk is present in the cache.
	 *
	 * @param c The chunk
	 * @return True if the chunk is present in the chunk cache
	 */
	public boolean isChunkCached(Chunk c) {
		return loadChunk((int) c.getPosition().x, (int) c.getPosition().z) != null;
	}

	/**
	 * Tries to load a chunk from the cache. Returns null if no
	 * chunk is found.
	 *
	 * @param x X-coordinate
	 * @param z Z-coordinate
	 * @return The loaded chunk
	 */
	public Chunk loadChunk(int x, int z) {
		return _chunkCache.get(BlockMath.cantorize(x, z));
	}

	/**
	 * @param key
	 * @return
	 */
	public Chunk getChunkByKey(int key) {
		return _chunkCache.get(key);
	}

	/**
	 * Writes all chunks to disk.
	 */
	public void writeAllChunksToDisk() {
		_parent.suspendUpdateThread();
        /*
         * Wait until the update thread is suspended.
         */
		while (_parent.isUpdateThreadRunning()) {
			// Do nothing
		}
		for (Chunk c : _chunkCache.values()) {
			c.writeChunkToDisk();
		}
		_parent.resumeUpdateThread();
	}

	/**
	 * @return
	 */
	public int size() {
		return _chunkCache.size();
	}

	/**
	 * @return
	 */
	int capacity() {
		return (Configuration.getSettingNumeric("V_DIST_X").intValue() * Configuration.getSettingNumeric("V_DIST_Z").intValue()) * 2;
	}
}