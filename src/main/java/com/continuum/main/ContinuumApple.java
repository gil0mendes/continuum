package com.continuum.main;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.applet.Applet;
import java.awt.*;

/**
 * The heart and soul of Continuum.
 */
public class ContinuumApple extends Applet {

    private final Continuum _continuum;
    private final Canvas _canvas;

    public ContinuumApple() {
        _continuum = Continuum.getInstance();
        _continuum.setSandboxed(true);

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
            }
        };

        _canvas.setSize(getWidth(), getHeight());

        add(_canvas);

        _canvas.setFocusable(true);
        _canvas.requestFocus();
        _canvas.setIgnoreRepaint(true);
    }

    private void startGame() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Display.setParent(_canvas);
                    Display.setDisplayMode(new DisplayMode(1024, 576));
                    Display.setTitle("Continuum - Applet");
                    Display.create();

                    _continuum.initControllers();
                    _continuum.initGame();
                    _continuum.startGame();
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        };

        t.start();
    }

    @Override
    public void init() {
        startGame();
    }

    @Override
    public void start() {
        _continuum.unpauseGame();
    }

    @Override
    public void stop() {
        _continuum.pauseGame();
    }

    @Override
    public void destroy() {
        _continuum.stopGame();
    }
}
