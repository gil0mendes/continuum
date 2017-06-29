package com.continuum.world.chunk;

import com.continuum.main.Configuration;
import com.continuum.main.Continuum;
import com.continuum.utilities.MathHelper;
import com.continuum.world.WorldProvider;
import javolution.util.FastList;
import javolution.util.FastMap;
import org.lwjgl.util.vector.Vector3f;

import java.io.*;
import java.util.Collections;
import java.util.logging.Level;

public final class ChunkCache {

	private final FastMap<Integer, Chunk> _chunkCache = new FastMap<Integer, Chunk>().shared();
	private final WorldProvider _parent;

	/**
	 * @param parent
	 */
	public ChunkCache(WorldProvider parent) {
		_parent = parent;
	}

	/**
	 * Loads a specified chunk from cache or from the disk.
	 * <p/>
	 * NOTE: This method ALWAYS returns a valid chunk (if positive x and z values are provided)
	 * since a new chunk is generated if none of the present chunks fit the request.
	 *
	 * @param x X-coordinate of the chunk
	 * @param z Z-coordinate of the chunk
	 * @return The chunk
	 */
	public Chunk loadOrCreateChunk(int x, int z) {
		int chunkId = MathHelper.cantorize(MathHelper.mapToPositive(x), MathHelper.mapToPositive(z));
		// Try to load the chunk from the cache
		Chunk c = _chunkCache.get(chunkId);

		// We got a chunk! Already! Great!
		if (c != null) {
			return c;
		}

		Vector3f chunkPos = new Vector3f(x, 0, z);
		// Init a new chunk
		c = loadChunkFromDisk(chunkPos);

		if (c == null) {
			c = new Chunk(_parent, chunkPos);
		}

		_chunkCache.put(chunkId, c);
		c.setCached(true);

		return c;
	}

	public void freeCacheSpace() {
		if (_chunkCache.size() <= capacity()) {
			return;
		}

		FastList<Chunk> cachedChunks = new FastList<Chunk>(_chunkCache.values());
		Collections.sort(cachedChunks);

		if (_chunkCache.size() > capacity()) {
			Chunk chunkToDelete = cachedChunks.getLast();
			// Prevent further updates to this chunk
			chunkToDelete.setCached(false);
			// Write the chunk to disk (but do not remove it from the cache just now)
			writeChunkToDisk(chunkToDelete);
			// When the chunk is written, finally remove it from the cache
			_chunkCache.values().remove(chunkToDelete);
			chunkToDelete.freeBuffers();
		}
	}

	/**
	 * Writes all chunks to disk and disposes them.
	 */
	public void saveAndDisposeAllChunks() {
		for (Chunk c : _chunkCache.values()) {
			c.setCached(false);
			writeChunkToDisk(c);
		}

		_chunkCache.clear();
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
	public static int capacity() {
		return (Configuration.getSettingNumeric("V_DIST_X").intValue() * Configuration.getSettingNumeric("V_DIST_Z").intValue() + 1024);
	}

	private void writeChunkToDisk(Chunk c) {
		if (Continuum.getInstance().isSandboxed())
			return;

		if (c.isFresh()) {
			return;
		}

		File dirPath = new File(_parent.getWorldSavePath() + "/" + c.getChunkSavePath());
		if (!dirPath.exists()) {
			if (!dirPath.mkdirs()) {
				Continuum.getInstance().getLogger().log(Level.SEVERE, "Could not create save directory.");
				return;
			}
		}

		File f = new File(_parent.getWorldSavePath() + "/" + c.getChunkSavePath() + "/" + c.getChunkFileName());

		try {
			FileOutputStream fileOut = new FileOutputStream(f);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(c);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Chunk loadChunkFromDisk(Vector3f chunkPos) {
		File f = new File(_parent.getWorldSavePath() + "/" + Chunk.getChunkSavePathForPosition(chunkPos) + "/" + Chunk.getChunkFileNameForPosition(chunkPos));

		if (!f.exists())
			return null;

		try {
			FileInputStream fileIn = new FileInputStream(f);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			Chunk result = (Chunk) in.readObject();
			result.setParent(_parent);

			in.close();
			fileIn.close();

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}