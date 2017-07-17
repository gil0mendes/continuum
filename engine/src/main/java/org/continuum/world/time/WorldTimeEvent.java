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

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleMath;

import java.math.RoundingMode;

/**
 * Created by gil0mendes on 02/07/2017.
 */
public class WorldTimeEvent extends TimeEventBase {
    public WorldTimeEvent(long worldTimeMs) {
        super(worldTimeMs);
    }

    public boolean matchesDaily(float fraction) {
        Preconditions.checkArgument(fraction >= 0 && fraction <= 1, "fraction must be in [0..1]");

        long fracInMs = DoubleMath.roundToLong(fraction * WordTime.DAY_LENGHT, RoundingMode.HALF_UP);
        long diff = getDayTimeInMs() - fracInMs;

        return 2 * diff < WordTime.TICK_EVENT_RATE && 2 * diff >= -WordTime.TICK_EVENT_RATE;
    }

    @Override
    public String toString() {
        return String.format("WorldTimeEvent [%s ms -> %.2f days]", getWorldTimeInMs(), getWorldTime());
    }
}
