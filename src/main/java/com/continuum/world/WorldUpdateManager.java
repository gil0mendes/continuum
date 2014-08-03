package com.continuum.world;

import com.continuum.world.World;
import com.continuum.world.chunk.Chunk;
import javolution.util.FastList;

import java.util.Collections;

public final class WorldUpdateManager {

	private final FastList<Chunk> _vboUpdates = new FastList<Chunk>(128);

	private double _meanUpdateDuration = 0.0;
	private final World _parent;

	private int _chunkUpdateAmount;

	/**
	 * @param _parent
	 */
	public WorldUpdateManager(World _parent) {
		this._parent = _parent;
	}

	public void processChunkUpdates() {
		long timeStart = System.currentTimeMillis();

		FastList<Chunk> dirtyChunks = new FastList<Chunk>(_parent.getVisibleChunks());

		for (int i = dirtyChunks.size() - 1; i >= 0; i--) {
			Chunk c = dirtyChunks.get(i);

			if (c == null) {
				dirtyChunks.remove(i);
				continue;
			}

			if (!(c.isDirty() || c.isFresh() || c.isLightDirty())) {
				dirtyChunks.remove(i);
			}
		}

		if (!dirtyChunks.isEmpty()) {
			Collections.sort(dirtyChunks);
			Chunk closestChunk = dirtyChunks.getFirst();
			processChunkUpdate(closestChunk);
		}

		_chunkUpdateAmount = dirtyChunks.size();

		_meanUpdateDuration += System.currentTimeMillis() - timeStart;
		_meanUpdateDuration /= 2;
	}

	private void processChunkUpdate(Chunk c) {
		if (c != null) {
			if (c.processChunk())
				_vboUpdates.add(c);
		}
	}

	public void updateVBOs() {
		while (!_vboUpdates.isEmpty()) {
			Chunk c = _vboUpdates.removeFirst();
			c.generateVBOs();
		}
	}

	public int getUpdatesSize() {
		return _chunkUpdateAmount;
	}

	public int getVboUpdatesSize() {
		return _vboUpdates.size();
	}

	public double getMeanUpdateDuration() {
		return _meanUpdateDuration;
	}
}