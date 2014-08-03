package com.continuum.world.chunk;

import com.continuum.world.World;
import javolution.util.FastList;

public final class ChunkUpdateManager {

    private final FastList<Chunk> _vboUpdates = new FastList<Chunk>(128);

    private double _meanUpdateDuration = 0.0;
    private final World _parent;

    private int _chunkUpdateAmount;

    /**
     * @param _parent
     */
    public ChunkUpdateManager(World _parent) {
        this._parent = _parent;
    }

    /**
     * TODO
     */
    public void processChunkUpdates() {
        long timeStart = System.currentTimeMillis();

        FastList<Chunk> dirtyChunks = new FastList<Chunk>(_parent.getVisibleChunks());

        for (int i = dirtyChunks.size() - 1; i >= 0; i--) {
            Chunk c = dirtyChunks.get(i);

            if (!(c.isDirty() || c.isFresh() || c.isLightDirty())) {
                dirtyChunks.remove(i);
            }
        }

        if (!dirtyChunks.isEmpty()) {
            Chunk closestChunk = dirtyChunks.getFirst();
            processChunkUpdate(closestChunk);
        }

        _chunkUpdateAmount = dirtyChunks.size();

        _meanUpdateDuration += System.currentTimeMillis() - timeStart;
        _meanUpdateDuration /= 2;
    }

    /**
     * TODO
     */
    private void processChunkUpdate(Chunk c) {
        if (c != null) {
            // Generate the chunk...
            c.generate();

            // ... and fetch its neighbors...
            Chunk[] neighbors = c.loadOrCreateNeighbors();

            // Before starting the illumination process, make sure that the neighbor chunks
			// are present and generated.
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] != null) {
                    neighbors[i].generate();
                }
            }

            // If the light of this chunk is marked as dirty...
            if (c.isLightDirty()) {
                // ... propagate light into adjacent chunks...
                c.updateLight();
            }

            // Check if this chunk was changed...
            if (c.isDirty() && !c.isLightDirty() && !c.isFresh()) {
                // ... if yes, regenerate the vertex arrays
                c.generateMesh();
                _vboUpdates.add(c);
            }
        }
    }

    /**
     * TODO
     */
    public void updateVBOs() {
        if (!_vboUpdates.isEmpty()) {
            Chunk c = _vboUpdates.removeFirst();
            c.generateVBOs();
        }
    }

    /**
     * @return
     */
    public int getUpdatesSize() {
        return _chunkUpdateAmount;
    }

    /**
     * @return
     */
    public int getVboUpdatesSize() {
        return _vboUpdates.size();
    }

    /**
     * @return
     */
    public double getMeanUpdateDuration() {
        return _meanUpdateDuration;
    }
}