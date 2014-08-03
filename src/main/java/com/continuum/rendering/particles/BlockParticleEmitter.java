package com.continuum.rendering.particles;

import com.continuum.rendering.TextureManager;
import com.continuum.world.World;

public class BlockParticleEmitter extends ParticleEmitter {

	private World _parent;
	private byte _currentBlockType = 0x1;

	public BlockParticleEmitter(World parent) {
		_parent = parent;
	}

	public void emitParticles(int amount, byte blockType) {
		_currentBlockType = blockType;
		super.emitParticles(amount);
	}

	public void render() {
		TextureManager.getInstance().bindTexture("terrain");
		super.render();
	}

	public World getParent() {
		return _parent;
	}

	@Override
	protected Particle createParticle() {
		return new BlockParticle(100, _origin, _currentBlockType, this);
	}
}