package com.webupdater;

import com.continuum.utilities.Helper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;

class UpdateComponent {

	private final URL _locationURL;
	private final String _fileName;

	public UpdateComponent(URL _webURL, String _fileName) {
		this._locationURL = _webURL;
		this._fileName = _fileName;
	}

	public String getFileName() {
		return _fileName;
	}

	URL getLocationURL() {
		return _locationURL;
	}

	public URL getURL() {
		try {
			return new URL(getLocationURL().toString() + getFileName());
		} catch (MalformedURLException ex) {
			Helper.LOGGER.log(Level.SEVERE, null, ex);
		}

		return null;
	}

	public String getLocalPath() {
		return "DATA/" + getFileName();
	}
}