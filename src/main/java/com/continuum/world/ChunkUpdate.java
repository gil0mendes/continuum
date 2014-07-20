package com.continuum.world;

public final class ChunkUpdate implements Comparable<ChunkUpdate> {

	private final boolean _updateNeighbors;
	private final Chunk _chunk;

	/**
	 * @param _updateNeighbors
	 * @param _chunk
	 */
	public ChunkUpdate(boolean _updateNeighbors, Chunk _chunk) {
		this._updateNeighbors = _updateNeighbors;
		this._chunk = _chunk;
	}

	/**
	 * @return
	 */
	public boolean isUpdateNeighbors() {
		return _updateNeighbors;
	}

	/**
	 * @return
	 */
	public Chunk getChunk() {
		return _chunk;
	}

	/**
	 * @return
	 */
	double getWeight() {
		return _chunk.distanceToPlayer();
	}

	/**
	 * @param o
	 * @return
	 */
	public int compareTo(ChunkUpdate o) {
		return new Double(getWeight()).compareTo(o.getWeight());
	}

	/**
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o.getClass() == ChunkUpdate.class) {
			ChunkUpdate cu = (ChunkUpdate) o;
			return cu.getChunk().equals(_chunk);
		}
		return false;
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 37 * hash + (this._chunk != null ? this._chunk.hashCode() : 0);
		return hash;
	}
}