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
package org.continuum;

import com.continuum.world.chunk.Chunk;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

public class ContinuumChunkTest extends junit.framework.TestCase {

	@Test
	public void testChunkToWorldMapping() throws Exception {
		Chunk chunk1 = new Chunk(null, new Vector3f(-1, 0, -1));

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

		Chunk chunk2 = new Chunk(null, new Vector3f(-2, 0, -2));

		blockPosX = 15;
		blockPosZ = 15;

		assertEquals(-17, chunk2.getBlockWorldPosX(blockPosX));
		assertEquals(-17, chunk2.getBlockWorldPosZ(blockPosZ));
	}

}
