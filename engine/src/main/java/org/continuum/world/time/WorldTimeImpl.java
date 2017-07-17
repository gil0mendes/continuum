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

import org.continuum.entitySystem.systems.BaseComponentSystem;
import org.continuum.entitySystem.systems.UpdateSubscriberSystem;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by gil0mendes on 02/07/2017.
 */
public class WorldTimeImpl extends BaseComponentSystem implements WordTime, UpdateSubscriberSystem {
    private static final float WORLD_TIME_MULTIPLIER = 48f;

    /**
     * We must use atomic values for no interruptions.
     */
    private AtomicLong worldTime = new AtomicLong(0);

    private Time

    @Override
    public void update(float delta) {

    }

    @Override
    public long getMilliseconds() {
        return 0;
    }

    @Override
    public float getSeconds() {
        return 0;
    }

    @Override
    public float getDays() {
        return 0;
    }

    @Override
    public float getTimeRate() {
        return 0;
    }

    @Override
    public void setMilliseconds(long time) {

    }

    @Override
    public void setDays(float timeInDays) {

    }
}
