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
package org.continuum.world.time;

import com.google.common.math.LongMath;

/**
 * A base class for different timer events.
 * <p>
 * Created by gil0mendes on 02/07/2017.
 */
public abstract class TimeEventBase {
    private long worldTimeMs;
    private long timeInDay;

    public TimeEventBase(long worldTimeMs) {
        this.worldTimeMs = worldTimeMs;
        this.timeInDay = LongMath.mod(worldTimeMs, WordTime.DAY_LENGHT);
    }

    /**
     * @return the time of day as a fraction
     */
    public float getDayTime() {
        return timeInDay / (float) WordTime.DAY_LENGHT;
    }

    /**
     * @return the time of day in milliseconds
     */
    public long getDayTimeInMs() {
        return timeInDay;
    }

    /**
     * @return the world time in days
     */
    public float getWorldTime() {
        return worldTimeMs / (float) WordTime.DAY_LENGHT;
    }

    /**
     * @return the world time in milliseconds
     */
    public long getWorldTimeInMs() {
        return worldTimeMs;
    }
}
