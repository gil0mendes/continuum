package com.continuum;

import com.continuum.world.World;
import junit.framework.TestCase;
import org.junit.Test;

public class WorldTest extends TestCase {
	private final World _world = new World("Test", "abcde");

	@Test
	public void testChunkToWorldMapping() throws Exception {
		int blockPosX = 0;
		int blockPosZ = 0;
		assertEquals(0, _world.calcChunkPosX(blockPosX));
		assertEquals(0, _world.calcChunkPosZ(blockPosZ));

		blockPosX = 15;
		blockPosZ = 15;
		assertEquals(0, _world.calcChunkPosX(blockPosX));
		assertEquals(0, _world.calcChunkPosZ(blockPosZ));

		blockPosX = -15;
		blockPosZ = 0;
		assertEquals(-1, _world.calcChunkPosX(blockPosX));
		assertEquals(0, _world.calcChunkPosZ(blockPosZ));

		blockPosX = 0;
		blockPosZ = -15;
		assertEquals(0, _world.calcChunkPosX(blockPosX));
		assertEquals(-1, _world.calcChunkPosZ(blockPosZ));

		blockPosX = -15;
		blockPosZ = -15;
		assertEquals(-1, _world.calcChunkPosX(blockPosX));
		assertEquals(-1, _world.calcChunkPosZ(blockPosZ));

		blockPosX = -2;
		blockPosZ = -2;
		assertEquals(-1, _world.calcChunkPosX(blockPosX));
		assertEquals(-1, _world.calcChunkPosZ(blockPosZ));

		blockPosX = -1;
		blockPosZ = -1;
		assertEquals(-1, _world.calcChunkPosX(blockPosX));
		assertEquals(-1, _world.calcChunkPosZ(blockPosZ));

		blockPosX = 13;
		blockPosZ = 13;
		assertEquals(13, _world.calcBlockPosX(blockPosX, 0));
		assertEquals(13, _world.calcBlockPosZ(blockPosZ, 0));

		blockPosX = 32;
		blockPosZ = 32;
		assertEquals(0, _world.calcBlockPosX(blockPosX, 2));
		assertEquals(0, _world.calcBlockPosZ(blockPosZ, 2));

		blockPosX = 13;
		blockPosZ = 13;
		assertEquals(13, _world.calcBlockPosX(blockPosX, 0));
		assertEquals(13, _world.calcBlockPosZ(blockPosZ, 0));

		blockPosX = -1;
		blockPosZ = -1;
		assertEquals(15, _world.calcBlockPosX(blockPosX, -1));
		assertEquals(15, _world.calcBlockPosZ(blockPosZ, -1));

		blockPosX = -2;
		blockPosZ = -2;
		assertEquals(14, _world.calcBlockPosX(blockPosX, -1));
		assertEquals(14, _world.calcBlockPosZ(blockPosZ, -1));
	}
}
