package com.continuum.datastructures;

import org.lwjgl.util.vector.Vector3f;

/**
 * Represents the position of a block. This class is used within the
 * collision detection process.
 */
public final class BlockPosition implements Comparable<BlockPosition> {

    public final int x;
    public final int y;
    public final int z;
    private final Vector3f _origin;

    public BlockPosition(int x, int y, int z, Vector3f origin) {
        this.x = x;
        this.y = y;
        this.z = z;
        this._origin = origin;
    }

    double getDistance() {
        return new Vector3f(x - _origin.x, y - _origin.y, z - _origin.z).length();
    }

    public int compareTo(BlockPosition o) {
        double distance = getDistance();
        double oDistance = o.getDistance();

        if (oDistance > distance)
            return -1;

        if (oDistance < distance)
            return 1;

        return 0;
    }
}