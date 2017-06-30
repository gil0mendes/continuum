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

import com.continuum.utilities.MathHelper;
import org.junit.Test;

public class TestMathHelper extends junit.framework.TestCase {

	@Test
	public void testCantor() throws Exception {
		int test1 = MathHelper.mapToPositive(22);
		assertEquals(22, MathHelper.redoMapToPositive(test1));
		int test2 = MathHelper.mapToPositive(-22);
		assertEquals(-22, MathHelper.redoMapToPositive(test2));

		int cant = MathHelper.cantorize(MathHelper.mapToPositive(-22), MathHelper.mapToPositive(11));
		assertEquals(11, MathHelper.redoMapToPositive(MathHelper.cantorY(cant)));
		assertEquals(-22,  MathHelper.redoMapToPositive(MathHelper.cantorX(cant)));
	}

}
