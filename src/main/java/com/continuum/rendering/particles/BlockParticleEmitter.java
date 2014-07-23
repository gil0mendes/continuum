package com.continuum.rendering.particles;

import com.continuum.rendering.TextureManager;

public class BlockParticleEmitter extends ParticleEmitter {

	private byte _currentBlockType = 0x1;

	public void emitParticles(int amount, byte blockType) {
		_currentBlockType = blockType;
		super.emitParticles(amount);
	}

	public void render() {
		TextureManager.getInstance().bindTexture("terrain");
		super.render();
	}

	@Override
	protected Particle createParticle() {
		return new BlockParticle(100, _origin, _currentBlockType);
	}
}