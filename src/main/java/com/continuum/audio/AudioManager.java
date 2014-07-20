package com.continuum.audio;

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
		try {
			_audioFiles.put("FootGrass1", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/FootGrass1.ogg")));
			_audioFiles.put("FootGrass2", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/FootGrass2.ogg")));
			_audioFiles.put("FootGrass3", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/FootGrass3.ogg")));
			_audioFiles.put("FootGrass4", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/FootGrass4.ogg")));
			_audioFiles.put("FootGrass5", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/FootGrass5.ogg")));
			_audioFiles.put("PlaceRemoveBlock", AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("com/continuum/data/sounds/PlaceRemoveBlock.ogg")));
		} catch (IOException e) {
			Game.getInstance().getLogger().log(Level.SEVERE, e.getLocalizedMessage());
		}
	}

	public Audio getAudio(String s) {
		return _audioFiles.get(s);
	}
}