/*
 * Copyright 2014-2017 Gil Mendes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.continuum.world;

import org.continuum.world.chunk.Chunk;
import javolution.util.FastList;
import javolution.util.FastSet;

import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public final class WorldUpdateManager {
    private static final int MAX_THREADS = Math.max(Runtime.getRuntime().availableProcessors() / 2, 1);
    private static final Executor threadPoll = Executors.newFixedThreadPool(MAX_THREADS);

    private final PriorityBlockingQueue<Chunk> _vboUpdates = new PriorityBlockingQueue<Chunk>();
    private final FastSet<Chunk> _currentlyProcessedChunks = new FastSet<Chunk>();

    private double averageUpdateDuration = 0.0;

    private final World _parent;

    private int _chunkUpdateAmount;

    /**
     * @param _parent
     */
    public WorldUpdateManager(World _parent) {
        this._parent = _parent;
    }

    public void queueChunkUpdates(FastList<Chunk> visibleChunks) {
        for (FastList.Node<Chunk> n = visibleChunks.head(), end = visibleChunks.tail(); (n = n.getNext()) != end; ) {
            if (n.getValue().isDirty() || n.getValue().isFresh() || n.getValue().isLightDirty()) {
                queueChunkUpdate(n.getValue());
            }
        }
    }

    public void queueChunkUpdate(Chunk c) {
        final Chunk chunkToProcess = c;

        if (!_currentlyProcessedChunks.contains(chunkToProcess) && _currentlyProcessedChunks.size() < MAX_THREADS) {
            _currentlyProcessedChunks.add(chunkToProcess);

            Runnable r = new Runnable() {
                public void run() {
                    long timeStart = System.currentTimeMillis();

                    processChunkUpdate(chunkToProcess);
                    _currentlyProcessedChunks.remove(chunkToProcess);

                    averageUpdateDuration += System.currentTimeMillis() - timeStart;
                    averageUpdateDuration /= 2;
                }
            };

            threadPoll.execute(r);
        }
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

    public int getVboUpdatesSize() {
        return _vboUpdates.size();
    }

    public double getMeanUpdateDuration() {
        return averageUpdateDuration;
    }
}
