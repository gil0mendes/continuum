package com.continuum.world.characters;

import com.continuum.world.World;
import com.continuum.world.entity.MovableEntity;

/**
 * TODO: Fill with functionality.
 */
public abstract class Character extends MovableEntity {

    public Character(World parent, double walkingSpeed, double runningFactor, double jumpIntensity) {
        super(parent, walkingSpeed, runningFactor, jumpIntensity);
    }
}