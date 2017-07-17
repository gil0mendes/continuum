package org.continuum.engine;

import org.continuum.main.Configuration;

import java.io.FileInputStream;
import java.util.Properties;

public final class Continuum {

    private Continuum() {
    }

    /**
     * TODO just a temporary work around for this working.
     *
     * @param args
     */
    public static void main(String[] args) {
        // load file with system properties if exists
        try {
            FileInputStream propFile = new FileInputStream("config.cfg");
            Properties p = new Properties(System.getProperties());
            p.load(propFile);
            System.setProperties(p);
        } catch (Exception e) {
        }

        // load debug
        // Configuration.loadDebug();

        // start engine
        org.continuum.main.Continuum.main(args);
    }
}
