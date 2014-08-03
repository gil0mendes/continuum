package com.continuum.rendering;

import com.continuum.main.Continuum;
import javolution.util.FastMap;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Provides global access to fonts.
 */
public class FontManager {

	private final FastMap<String, AngelCodeFont> _fonts = new FastMap<String, AngelCodeFont>();
	private static FontManager _instance = null;

	/**
	 * Returns (and creates – if necessary) the static instance
	 * of this helper class.
	 *
	 * @return The instance
	 */
	public static FontManager getInstance() {
		if (_instance == null) {
			_instance = new FontManager();
		}

		return _instance;
	}

	private FontManager() {
		initFonts();
	}

	private void initFonts() {
		try {
			_fonts.put("default", new AngelCodeFont("Font", ResourceLoader.getResource("com/continuum/data/fonts/default.fnt").openStream(), ResourceLoader.getResource("com/continuum/data/fonts/default_0.png").openStream()));
		} catch (SlickException e) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, "Couldn't load fonts. Sorry. " + e.toString(), e);
		} catch (IOException e) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, "Couldn't load fonts. Sorry. " + e.toString(), e);
		}
	}

	public AngelCodeFont getFont(String s) {
		return _fonts.get(s);
	}
}