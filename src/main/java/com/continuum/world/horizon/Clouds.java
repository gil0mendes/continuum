package com.continuum.world.horizon;

import com.continuum.main.Continuum;
import com.continuum.rendering.Primitives;
import com.continuum.rendering.RenderableObject;
import com.continuum.rendering.ShaderManager;
import com.continuum.world.World;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;

import static org.lwjgl.opengl.GL11.*;

public class Clouds implements RenderableObject {

	private boolean[][] _clouds;
	private int _dlClouds = -1;

	private final Vector2f _cloudOffset = new Vector2f();
	private final Vector2f _windDirection = new Vector2f(0.25f, 0);
	private double _lastWindUpdate = 0;
	private short _nextWindUpdateInSeconds = 32;

	private World _parent;

	public Clouds(World parent) {
		_parent = parent;
		_dlClouds = glGenLists(1);

		generateClouds();
		generateCloudDisplayList();
	}

	private void generateClouds() {
		try {
			BufferedImage cloudImage = ImageIO.read(ResourceLoader.getResource("com/continuum/data/textures/clouds.png").openStream());
			_clouds = new boolean[cloudImage.getWidth()][cloudImage.getHeight()];

			for (int x = 0; x < cloudImage.getWidth(); x++) {
				for (int y = 0; y < cloudImage.getHeight(); y++) {
					if ((cloudImage.getRGB(x, y) & 0x00FFFFFF) != 0) {
						_clouds[x][y] = true;
					}
				}
			}
		} catch (IOException ex) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Generates the cloud display list.
	 */
	private void generateCloudDisplayList() {
		glNewList(_dlClouds, GL_COMPILE);
		glBegin(GL_QUADS);

		int length = _clouds.length;

		for (int x = 0; x < length; x++) {
			for (int y = 0; y < length; y++) {
				if (_clouds[x][y]) {
					try {
						Primitives.drawCloud(16, 16, 16, x * 16f - (length / 2 * 16f), 0, y * 16f - (length / 2 * 16f), !_clouds[x - 1][y], !_clouds[x + 1][y], !_clouds[x][y + 1], !_clouds[x][y - 1]);
					} catch (Exception e) {

					}
				}
			}
		}

		glEnd();
		glEndList();
	}

	public void render() {
		glEnable(GL_BLEND);
		GL11.glBlendFunc(770, 771);

		ShaderManager.getInstance().enableShader("cloud");
		int daylight = GL20.glGetUniformLocation(ShaderManager.getInstance().getShader("cloud"), "daylight");
		GL20.glUniform1f(daylight, (float) _parent.getDaylight());

		// Render two passes: The first one only writes to the depth buffer, the second one to the frame buffer
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				glColorMask(false, false, false, false);
			} else {
				glColorMask(true, true, true, true);
			}

            /*
            * Draw clouds.
            */
			if (_dlClouds > 0 && _parent.isDaytime()) {
				glPushMatrix();
				glTranslatef(_parent.getPlayer().getPosition().x + _cloudOffset.x, 140f, _parent.getPlayer().getPosition().z + _cloudOffset.y);
				glCallList(_dlClouds);
				glPopMatrix();
			}
		}

		ShaderManager.getInstance().enableShader(null);
		glDisable(GL_BLEND);
	}

	public void update() {
		// Move the clouds a bit each update
		_cloudOffset.x += _windDirection.x;
		_cloudOffset.y += _windDirection.y;

		if (_cloudOffset.x >= _clouds.length * 16 / 2 || _cloudOffset.x <= -(_clouds.length * 16 / 2)) {
			_windDirection.x = -_windDirection.x;
		} else if (_cloudOffset.y >= _clouds.length * 16 / 2 || _cloudOffset.y <= -(_clouds.length * 16 / 2)) {
			_windDirection.y = -_windDirection.y;
		}

		if (Continuum.getInstance().getTime() - _lastWindUpdate > _nextWindUpdateInSeconds * 1000) {
			_windDirection.x = (float) _parent.getRandom().randomDouble() / 8;
			_windDirection.y = (float) _parent.getRandom().randomDouble() / 8;
			_nextWindUpdateInSeconds = (short) (Math.abs(_parent.getRandom().randomInt()) % 16 + 32);
			_lastWindUpdate = Continuum.getInstance().getTime();
		}
	}
}