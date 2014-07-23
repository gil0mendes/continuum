package com.continuum.rendering.particles;

import com.continuum.blocks.Block;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

/**
 * Block particle class
 */
public class BlockParticle extends Particle {

	private float _size = 0.04f;
	private byte _blockType = 0x1;

	public BlockParticle(int lifeTime, Vector3f position, byte blockType) {
		super(lifeTime, position);
		_blockType = blockType;
	}

	@Override
	protected void renderParticle() {
		glDisable(GL11.GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		glBegin(GL_QUADS);
		GL11.glColor3f(0.75f, 0.75f, 0.75f);
		GL11.glTexCoord2f(Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).x, Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).y);
		GL11.glVertex3f(-_size, _size, -_size);
		GL11.glTexCoord2f(Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).x + 0.0624f, Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).y);
		GL11.glVertex3f(_size, _size, -_size);
		GL11.glTexCoord2f(Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).x + 0.0624f, Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).y + 0.0624f);
		GL11.glVertex3f(_size, -_size, -_size);
		GL11.glTexCoord2f(Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).x, Block.getBlockForType(_blockType).getTextureOffsetFor(Block.SIDE.FRONT).y + 0.0624f);
		GL11.glVertex3f(-_size, -_size, -_size);
		glEnd();

		glDisable(GL_BLEND);
		glDisable(GL11.GL_TEXTURE_2D);
		glEnable(GL11.GL_CULL_FACE);
	}
}
