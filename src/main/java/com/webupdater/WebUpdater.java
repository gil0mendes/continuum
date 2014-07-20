package com.webupdater;

import com.continuum.utilities.Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;

public class WebUpdater {

	public WebUpdater() {
	}

	ArrayList<UpdateComponent> getUpdateComponents() {
		ArrayList<UpdateComponent> result = new ArrayList<UpdateComponent>();

		try {
			URL url = new URL("http://www.continuum.designture.net/data/");

			result.add(new UpdateComponent(url, "sun.png"));
			result.add(new UpdateComponent(url, "moon.png"));
			result.add(new UpdateComponent(url, "terrain.png"));
			result.add(new UpdateComponent(url, "clouds.png"));
		} catch (MalformedURLException ex) {
			Helper.LOGGER.log(Level.SEVERE, null, ex);
		}

		return result;
	}

	boolean isUpToDate() {
		ArrayList<UpdateComponent> components = getUpdateComponents();

		for (UpdateComponent c : components) {
			File f = new File(c.getLocalPath());

			if (!f.exists()) {
				Helper.LOGGER.log(Level.INFO, "Continuum is out of date.");
				return false;
			} else {
				try {
					URL u = c.getURL();
					URLConnection uc = u.openConnection();

					int length = uc.getContentLength();

					if (length != f.length()) {
						return false;
					}

				} catch (IOException ex) {
					Helper.LOGGER.log(Level.SEVERE, null, ex);
					return false;
				}
			}
		}

		Helper.LOGGER.log(Level.INFO, "Continuum is up-to-date.");
		return true;
	}

	public boolean update() {
		if (isUpToDate()) {
			Helper.LOGGER.log(Level.INFO, "Skipping update process.");
			return true;
		}

		Helper.LOGGER.log(Level.INFO, "Downloading new files...");
		File gfxDir = new File("DATA");

		gfxDir.mkdir();

		try {
			ArrayList<UpdateComponent> components = getUpdateComponents();

			for (UpdateComponent c : components) {
				Helper.LOGGER.log(Level.INFO, "Downloading... {0}", c.getFileName());

				InputStream is;
				FileOutputStream fos;

				is = c.getURL().openStream();
				fos = new FileOutputStream(c.getLocalPath());

				int i;
				while ((i = is.read()) != -1) {
					fos.write(i);
				}

				Helper.LOGGER.log(Level.INFO, "Finished downloading... {0}", c.getFileName());

				fos.flush();
				fos.close();
				is.close();
			}

		} catch (MalformedURLException ex) {
			Helper.LOGGER.log(Level.SEVERE, null, ex);
			return false;
		} catch (IOException ex) {
			Helper.LOGGER.log(Level.SEVERE, null, ex);
			return false;
		}

		Helper.LOGGER.log(Level.INFO, "Finished updating Continuum!");
		return true;
	}
}