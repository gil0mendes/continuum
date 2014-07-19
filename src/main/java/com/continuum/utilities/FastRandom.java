package com.continuum.utilities;

/**
 * Random number generator based on the Xorshift generator by George Marsaglia.
 */
public class FastRandom {

	long seed = System.currentTimeMillis();

	/**
	 * Initializes a new instance of the random number genrator using
	 * a specified seed
	 *
	 * @param seed The seed to use
	 */
	public FastRandom(long seed) {
		this.seed = seed;
	}

	/**
	 * Initializes a new instance of the random number genrator using
	 * System.currentTimeMillis() as seed.
	 */
	public FastRandom() {
	}

	/**
	 * Returns a random value as long.
	 *
	 * @return Random value
	 */
	public long randomLong() {
		seed ^= (seed << 21);
		seed ^= (seed >>> 35);
		seed ^= (seed << 4);
		return seed;
	}

	/**
	 * Returns a random value as int.
	 *
	 * @return Random value
	 */
	public int randomInt() {
		return (int) randomLong();
	}

	/**
	 * Returns a random value as double.
	 *
	 * @return Random value
	 */
	public double randomDouble() {
		return randomLong() / ((double) Long.MAX_VALUE - 1d);
	}

	/**
	 * Returns a random value as boolean.
	 *
	 * @return Random value
	 */
	public boolean randomBoolean() {
		return randomLong() > 0;
	}

	/**
	 * Returns a random character string with a specified length.
	 *
	 * @param length The length of the generated string
	 * @return Random character string
	 */
	public String randomCharacterString(int length) {
		StringBuilder s = new StringBuilder();

		for (int i = 0; i < length / 2; i++) {
			s.append((char) ('a' + Math.abs(randomDouble()) * 26d));
			s.append((char) ('A' + Math.abs(randomDouble()) * 26d));
		}

		return s.toString();
	}
}