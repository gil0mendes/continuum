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
package org.continuum.rendering;

import org.continuum.main.Continuum;
import javolution.util.FastMap;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.io.IOException;
import java.util.logging.Level;

public class TextureManager {

    private static TextureManager _instance;
    private final FastMap<String, Texture> _textures = new FastMap<String, Texture>();

    public static TextureManager getInstance() {
        if (_instance == null)
            _instance = new TextureManager();

        return _instance;
    }

    public TextureManager() {
        try {
            Continuum.getInstance().getLogger().log(Level.FINE, "Loading textures...");

            _textures.put("custom_lava_still", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/custom_lava_still.png").openStream(), GL_NEAREST));
            _textures.put("custom_water_still", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/custom_water_still.png").openStream(), GL_NEAREST));
            _textures.put("custom_lava_flowing", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/custom_lava_flowing.png").openStream(), GL_NEAREST));
            _textures.put("custom_water_flowing", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/custom_water_flowing.png").openStream(), GL_NEAREST));
            _textures.put("terrain", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/terrain.png").openStream(), GL_NEAREST));
            _textures.put("sun", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/sun.png").openStream(), GL_NEAREST));
            _textures.put("moon", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/moon.png").openStream(), GL_NEAREST));
            _textures.put("slime", TextureLoader.getTexture("png", ResourceLoader.getResource("org/continuum/data/textures/slime.png").openStream(), GL_NEAREST));

            Continuum.getInstance().getLogger().log(Level.FINE, "Finished loading textures!");
        } catch (IOException ex) {
            Continuum.getInstance().getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public void bindTexture(String s) {
        _textures.get(s).bind();
    }
}
