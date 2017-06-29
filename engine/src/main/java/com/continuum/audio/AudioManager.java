package com.continuum.audio;

import com.continuum.main.Continuum;
import javolution.util.FastMap;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.logging.Level;

public class AudioManager {

	private FastMap<String, Audio> _audioFiles = new FastMap();
	private static AudioManager _instance = null;

	/**
	 * Returns (and creates – if necessary) the static instance
	 * of this helper class.
	 *
	 * @return The instance
	 */
	public static AudioManager getInstance() {
		if (_instance == null) {
			_instance = new AudioManager();
		}

		return _instance;
	}

	private AudioManager() {
		loadAudioFiles();
	}

	private void loadAudioFiles() {
		_audioFiles.put("PlaceRemoveBlock", loadAudio("PlaceRemoveBlock"));
	}

	public Audio loadAudio(String s) {
		try {
			return AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/" + s + ".ogg"));
		} catch (IOException e) {
			Continuum.getInstance().getLogger().log(Level.SEVERE, e.getLocalizedMessage());
		}

		return null;
	}

	public Audio getAudio(String s) {
		return _audioFiles.get(s);
	}
}