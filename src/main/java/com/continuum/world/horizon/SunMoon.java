package com.continuum.world.horizon;

import com.continuum.main.Configuration;
import com.continuum.rendering.RenderableObject;
import com.continuum.rendering.TextureManager;
import com.continuum.world.World;

import static org.lwjgl.opengl.GL11.*;

public class SunMoon implements RenderableObject {

	private World _parent;

	private int _dlSunMoon = -1;

	public SunMoon(World parent) {
		_parent = parent;
		_dlSunMoon = glGenLists(1);

		generateSunMoonDisplayList();
	}

	public void render() {
		glPushMatrix();

		// Position the sun relatively to the player
		glTranslated(_parent.getPlayer().getPosition().x, Configuration.CHUNK_DIMENSIONS.y * 2.0, Configuration.getSettingNumeric("V_DIST_Z") * Configuration.CHUNK_DIMENSIONS.z + _parent.getPlayer().getPosition().z);
		glRotatef(-35, 1, 0, 0);

		glColor4f(1f, 1f, 1f, 1.0f);

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);

		glEnable(GL_TEXTURE_2D);
		if (_parent.isDaytime()) {
			TextureManager.getInstance().bindTexture("sun");
		} else {
			TextureManager.getInstance().bindTexture("moon");
		}

		glCallList(_dlSunMoon);

		glDisable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glPopMatrix();

		glDisable(GL_TEXTURE_2D);
	}

	private void generateSunMoonDisplayList() {
		glNewList(_dlSunMoon, GL_COMPILE);
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex3d(-Configuration.SUN_SIZE, Configuration.SUN_SIZE, -Configuration.SUN_SIZE);
		glTexCoord2f(1.f, 0.0f);
		glVertex3d(Configuration.SUN_SIZE, Configuration.SUN_SIZE, -Configuration.SUN_SIZE);
		glTexCoord2f(1.f, 1.0f);
		glVertex3d(Configuration.SUN_SIZE, -Configuration.SUN_SIZE, -Configuration.SUN_SIZE);
		glTexCoord2f(0.f, 1.0f);
		glVertex3d(-Configuration.SUN_SIZE, -Configuration.SUN_SIZE, -Configuration.SUN_SIZE);
		glEnd();
		glEndList();
	}

	public void update() {
		// Nothing to do at the moment.
	}
}