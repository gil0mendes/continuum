package com.continuum.blocks;

import com.continuum.utilities.Helper;
import org.lwjgl.util.vector.Vector2f;

public class BlockColorBlue extends Block {

    @Override
    public Vector2f getTextureOffsetFor(SIDE side) {
        return Helper.calcOffsetForTextureAt(1, 11);
    }
}