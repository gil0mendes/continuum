package com.continuum.generators;

import com.continuum.Chunk;
import com.continuum.World;

/**
 * Generator interface,
 */
public interface Generator {
	public void generate(Chunk c, World parent);
}