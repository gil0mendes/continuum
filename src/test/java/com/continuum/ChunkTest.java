package com.continuum;

import com.continuum.world.chunk.Chunk;
import junit.framework.TestCase;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

public class ChunkTest extends TestCase {
	@Test
	public void testChunkToWorldMapping() throws Exception {
		Chunk chunk1 = new Chunk(null, new Vector3f(-1, 0, -1), null);

		int blockPosX = 0;
		int blockPosZ = 0;

		assertEquals(-16, chunk1.getBlockWorldPosX(blockPosX));
		assertEquals(-16, chunk1.getBlockWorldPosZ(blockPosZ));

		blockPosX = 15;
		blockPosZ = 15;

		assertEquals(-1, chunk1.getBlockWorldPosX(blockPosX));
		assertEquals(-1, chunk1.getBlockWorldPosZ(blockPosZ));

		blockPosX = 15;
		blockPosZ = 15;

		assertEquals(-1, chunk1.getBlockWorldPosX(blockPosX));
		assertEquals(-1, chunk1.getBlockWorldPosZ(blockPosZ));

		Chunk chunk2 = new Chunk(null, new Vector3f(-2, 0, -2), null);

		blockPosX = 15;
		blockPosZ = 15;

		assertEquals(-17, chunk2.getBlockWorldPosX(blockPosX));
		assertEquals(-17, chunk2.getBlockWorldPosZ(blockPosZ));
	}
}
