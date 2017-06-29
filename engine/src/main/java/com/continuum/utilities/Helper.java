package com.continuum.utilities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector2f;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * This is a simple helper class for various tasks.
 */
public class Helper {

    private static final double DIV = 1.0 / 16.0;

    /**
     * Calculates the texture offset for a given position within
     * the texture atlas.
     *
     * @param x X-coordinate
     * @param y Y-coordinate
     * @return The texture offset
     */
    public static Vector2f calcOffsetForTextureAt(int x, int y) {
        return new Vector2f((float) (x * DIV), (float) (y * DIV));
    }

    /**
     * Returns true if the flag at given byte position
     * is set.
     *
     * @param value Byte value storing the flags
     * @param index Index position of the flag
     * @return True if the flag is set
     */
    public static boolean isFlagSet(byte value, short index) {
        return (value & (1 << index)) != 0;
    }

    /**
     * Sets a flag at a given byte position.
     *
     * @param value Byte value storing the flags
     * @param index Index position of the flag
     * @return The byte value containing the modified flag
     */
    public static byte setFlag(byte value, short index) {
        return (byte) (value | (1 << index));
    }
}