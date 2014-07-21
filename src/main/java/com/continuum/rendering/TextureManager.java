package com.continuum.rendering;

import com.continuum.main.Game;
import javolution.util.FastMap;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static org.lwjgl.opengl.GL11.GL_NEAREST;

import java.io.IOException;
import java.util.logging.Level;

public class TextureManager {

    private static TextureManager _instance;
    private final FastMap<String, Texture> _textures = new FastMap<String, Texture>(32);

    public static TextureManager getInstance() {
        if (_instance == null)
            _instance = new TextureManager();

        return _instance;
    }

    public TextureManager() {
        try {
            Game.getInstance().getLogger().log(Level.FINE, "Loading textures...");

            _textures.put("terrain", TextureLoader.getTexture("png", ResourceLoader.getResource("com/continuum/data/textures/terrain.png").openStream(), GL_NEAREST));
            _textures.put("sun", TextureLoader.getTexture("png", ResourceLoader.getResource("com/continuum/data/textures/sun.png").openStream(), GL_NEAREST));
            _textures.put("moon", TextureLoader.getTexture("png", ResourceLoader.getResource("com/continuum/data/textures/moon.png").openStream(), GL_NEAREST));
            _textures.put("slime", TextureLoader.getTexture("png", ResourceLoader.getResource("com/continuum/data/textures/slime.png").openStream(), GL_NEAREST));

            Game.getInstance().getLogger().log(Level.FINE, "Finished loading textures!");
        } catch (IOException ex) {
            Game.getInstance().getLogger().log(Level.SEVERE, null, ex);
        }
    }

    public void bindTexture(String s) {
        _textures.get(s).bind();
    }
}