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
package org.continuum.main;

import javolution.util.FastMap;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.util.vector.Vector3f;

/**
 * Helper class to allow global access to properties and game settings.
 */
public final class Configuration {
    /**
     * The mouse sensitivity.
     */
    public static final double MOUSE_SENS = 0.075;
    /**
     * The title string of the game.
     */
    public static final String GAME_TITLE = "Continuum Pre Alpha";
    /**
     * The three dimensions of a chunk.
     */
    public static final Vector3f CHUNK_DIMENSIONS = new Vector3f(16, 256, 16);
    /**
     * The size of the sun.
     */
    public static final double SUN_SIZE = 64;
    /**
     * The string used to generate the default world. If not set, a random seed is used.
     */
    public static final String DEFAULT_SEED = "";
    /**
     * The pixel format used to init. the display.
     */
    public static final PixelFormat PIXEL_FORMAT = new PixelFormat().withDepthBits(24);
    /**
     * The display mode used for window mode.
     */
    public static final DisplayMode DISPLAY_MODE = new DisplayMode(1280, 720);
    /**
     * If set to true, the game is rendered as a full screen application.
     */
    public static final boolean FULLSCREEN = false;

    /* PLAYER */
    public static final double BOBBING_ANGLE = 2.5;

    /* LIGHTING */
    public static final byte MAX_LIGHT = 15;
    public static final double OCCLUSION_AMOUNT_DEFAULT = 1.0 / 16.0;
    public static final double OCCLUSION_AMOUNT_BILLBOARDS = OCCLUSION_AMOUNT_DEFAULT / 8.0;

    /* RESOURCES */
    public static final double PROB_COAL = -2;
    public static final double PROB_GOLD = -3;
    public static final double PROB_SILVER = -2.5;
    public static final double PROB_REDSTONE = -3;
    public static final double PROB_DIAMOND = -4;

    /* -------- */
    private static final FastMap<String, Double> _settingsNumeric = new FastMap<String, Double>();
    private static final FastMap<String, Boolean> settingsBoolean = new FastMap<String, Boolean>();

    static {
        // load the default configurations
        loadDefaults();

        if (Boolean.getBoolean("continuum.demo")) {
            loadDemo();
        } else if (Boolean.getBoolean("continuum.debug")) {
            loadDebug();
        } else if (Boolean.getBoolean("continuum.sandboxed")) {
            loadSandboxed();
        }
    }

    /**
     * Returns a numeric value of a setting for a given key.
     *
     * @param key The key
     * @return The numeric value
     */
    public static Double getSettingNumeric(String key) {
        return _settingsNumeric.get(key);
    }

    /**
     * Returns the boolean value of a setting for a given key.
     *
     * @param key The key
     * @return The boolean value
     */
    public static Boolean getSettingBoolean(String key) {
        return settingsBoolean.get(key);
    }

    /**
     * Sets a boolean value of a setting for a given key.
     *
     * @param key   The key
     * @param value The boolean value
     */
    public static void setSetting(String key, Boolean value) {
        settingsBoolean.put(key, value);
    }

    /**
     * Sets a numeric value of a setting for a given key.
     *
     * @param key   The key
     * @param value The numeric value
     */
    public static void setSetting(String key, Double value) {
        _settingsNumeric.put(key, value);
    }

    /**
     * Loads the default values for the global settings.
     */
    private static void loadDefaults() {
        settingsBoolean.put("SANDBOXED", false);
        settingsBoolean.put("SAVE_CHUNKS", true);
        settingsBoolean.put("ROTATING_BLOCK", true);
        settingsBoolean.put("REPLANT_DIRT", true);
        settingsBoolean.put("PLACING_BOX", true);
        settingsBoolean.put("CHUNK_OUTLINES", false);
        settingsBoolean.put("DEBUG", false);
        settingsBoolean.put("DEBUG_COLLISION", false);
        settingsBoolean.put("CROSSHAIR", true);
        settingsBoolean.put("BOBBING", true);
        settingsBoolean.put("DEMO_FLIGHT", false);
        settingsBoolean.put("GOD_MODE", false);
        _settingsNumeric.put("JUMP_INTENSITY", 0.125);
        _settingsNumeric.put("MAX_GRAVITY", 0.7);
        _settingsNumeric.put("WALKING_SPEED", 0.03);
        _settingsNumeric.put("RUNNING_FACTOR", 1.8);
        _settingsNumeric.put("GRAVITY", 0.006);
        _settingsNumeric.put("MAX_GRAVITY_SWIMMING", 0.01);
        _settingsNumeric.put("GRAVITY_SWIMMING", 0.0001);
        _settingsNumeric.put("FRICTION", 0.08);
        _settingsNumeric.put("V_DIST_X", 32.0);
        _settingsNumeric.put("V_DIST_Z", 32.0);
    }

    public static void loadDebug() {
        settingsBoolean.put("CHUNK_OUTLINES", false);
        settingsBoolean.put("DEBUG", true);
        settingsBoolean.put("DEBUG_COLLISION", false);
        settingsBoolean.put("GOD_MODE", true);
        _settingsNumeric.put("WALKING_SPEED", 0.5);
    }

    public static void loadDemo() {
        settingsBoolean.put("DEBUG", false);
        settingsBoolean.put("PLACING_BOX", false);
        settingsBoolean.put("CROSSHAIR", false);
        settingsBoolean.put("DEMO_FLIGHT", true);
        settingsBoolean.put("GOD_MODE", true);
    }

    public static void loadSandboxed() {
        _settingsNumeric.put("V_DIST_X", 8.0);
        _settingsNumeric.put("V_DIST_Z", 8.0);
    }
}
