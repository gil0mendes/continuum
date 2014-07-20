package com.continuum.generators;

import com.continuum.main.Configuration;
import com.continuum.world.World;

/**
 * Generates a simple, bushy tree.
 */
public class ObjectGeneratorTree extends ObjectGenerator {

	/**
	 * @param w
	 * @param seed
	 */
	public ObjectGeneratorTree(World w, String seed) {
		super(w, seed);
	}

	/**
	 * Generates the tree.
	 *
	 * @param posX Origin on the x-axis
	 * @param posY Origin on the y-axis
	 * @param posZ Origin on the z-axis
	 */
	@Override
	public void generate(int posX, int posY, int posZ, boolean update) {
		int height = _rand.randomInt() % 2 + 6;

		if (posY + height >= Configuration.CHUNK_DIMENSIONS.y) {
			return;
		}

		// Generate tree trunk
		for (int i = 0; i < height; i++) {
			_world.setBlock(posX, posY + i, posZ, (byte) 0x5, update, false);
		}

		// Generate the treetop
		for (int y = height - 3; y < height + 1; y++) {
			for (int x = -2; x < 3; x++) {
				for (int z = -2; z < 3; z++) {
					if (!(x == -2 && z == -2) && !(x == 2 && z == 2) && !(x == -2 && z == 2) && !(x == 2 && z == -2)) {
						if (_rand.randomDouble() <= 0.8f) {
							_world.setBlock(posX + x, posY + y, posZ + z, (byte) 0x6, update, false);
							_world.refreshSunlightAt(posX + x, posZ + z, false, true);
						}
					}
				}
			}
		}
	}
}