package com.continuum.world;

import com.continuum.world.chunk.Chunk;
import javolution.util.FastList;
import javolution.util.FastSet;

import java.util.Collections;
import java.util.concurrent.PriorityBlockingQueue;

public final class WorldUpdateManager {

	private final PriorityBlockingQueue<Chunk> _vboUpdates = new PriorityBlockingQueue<Chunk>();
	private final FastSet<Chunk> _currentlyProcessedChunks = new FastSet<Chunk>();
	private int _threadCount = 0;

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

		final FastList<Chunk> dirtyChunks = new FastList<Chunk>(_parent.getVisibleChunks());

		for (int i = dirtyChunks.size() - 1; i >= 0; i--) {
			Chunk c = dirtyChunks.get(i);

			if (c == null) {
				dirtyChunks.remove(i);
				continue;
			}

			if (!(c.isDirty() || c.isFresh() || c.isLightDirty()) || !_parent.getPlayer().getViewFrustum().intersects(c.getAABB())) {
				dirtyChunks.remove(i);
			}
		}

		if (dirtyChunks.isEmpty()) {
			return;
		}

		Collections.sort(dirtyChunks);

		final Chunk chunkToProcess = dirtyChunks.getFirst();

		if (!_currentlyProcessedChunks.contains(chunkToProcess)) {

			_currentlyProcessedChunks.add(chunkToProcess);

			Thread t = new Thread() {
				@Override
				public void run() {
					while (_threadCount > Runtime.getRuntime().availableProcessors()) {
						synchronized (_currentlyProcessedChunks) {
							try {
								_currentlyProcessedChunks.wait();
							} catch (InterruptedException e) {
							}
						}
					}

					synchronized (_currentlyProcessedChunks) {
						_threadCount++;
					}

					processChunkUpdate(chunkToProcess);
					_currentlyProcessedChunks.remove(chunkToProcess);

					synchronized (_currentlyProcessedChunks) {
						_threadCount--;
					}

					synchronized (_currentlyProcessedChunks) {
						_currentlyProcessedChunks.notify();
					}
				}
			};

			t.start();
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
		while (_vboUpdates.size() > 0) {
			Chunk c = _vboUpdates.poll();

			if (c != null)
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

	public int getThreadCount() {
		return _threadCount;
	}
}