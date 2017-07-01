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

import org.lwjgl.opengl.Display;

import java.applet.Applet;
import java.awt.*;

public class ContinuumApple extends Applet {

    private Continuum _continuum;
    private Canvas _canvas;
    private Thread _gameThread;

    @Override
    public void init() {
        setLayout(new BorderLayout());

        _canvas = new Canvas() {
            @Override
            public void addNotify() {
                super.addNotify();
                startGame();
            }

            @Override
            public void removeNotify() {
                super.removeNotify();
                _continuum.stopGame();

                try {
                    _gameThread.join();
                } catch (InterruptedException e) {
                }
            }
        };

        _canvas.setSize(getWidth(), getHeight());

        add(_canvas);

        _canvas.setFocusable(true);
        _canvas.requestFocus();
        _canvas.setIgnoreRepaint(true);
    }

    private void startGame() {
        _gameThread = new Thread() {
            @Override
            public void run() {
                try {
                    Display.setParent(_canvas);
                    ;
                    Display.create();

                    _continuum.initControllers();
                    _continuum.initGame();
                    _continuum.startGame();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        };

        _gameThread.start();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {
        remove(_canvas);
        super.destroy();
    }
}
