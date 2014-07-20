package com.continuum.generators;

import com.continuum.main.Configuration;
import com.continuum.world.World;

/**
 * Generates a simple, bushy tree.
 */
public class ObjectGeneratorPineTree extends ObjectGenerator {

	/**
	 * @param w
	 * @param seed
	 */
	public ObjectGeneratorPineTree(World w, String seed) {
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
		int height = _rand.randomInt() % 2 + 8;

		if (posY + height >= Configuration.CHUNK_DIMENSIONS.y) {
			return;
		}

		// Generate tree trunk
		for (int i = 0; i < height; i++) {
			_world.setBlock(posX, posY + i, posZ, (byte) 0x5, update, false);
		}

		int stage = 2;
		// Generate the treetop
		for (int y = height - 1; y >= (height * (1f / 3f)); y--) {
			// int div = (int) Math.ceil((double) y / 4d);
			for (int x = -(stage / 2); x <= (stage / 2); x++) {
				for (int z = -(stage / 2); z <= (stage / 2); z++) {
					if (!(x == 0 && z == 0)) {
						_world.setBlock(posX + x, posY + y, posZ + z, (byte) 0x16, update, false);
						_world.refreshSunlightAt(posX + x, posZ + z, false, true);
					}
				}
			}

			stage++;
		}

		_world.setBlock(posX, posY + height, posZ, (byte) 0x6, update, false);
	}
}