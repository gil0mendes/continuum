package com.continuum.main;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.applet.Applet;

/**
 * The heart and soul of Continuum.
 */
public class ContinuumApple extends Applet {

    private final Game _game;

    public ContinuumApple() {
        _game = Game.getInstance();
        _game.setSandbox(true);
    }

    private void startGame() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    Display.setParent(null);
                    Display.setDisplayMode(new DisplayMode(1024, 576));
                    Display.setTitle("Continuum - Applet");
                    Display.create();

                    _game.initControllers();
                    _game.initGame();
                    _game.startGame();
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
        _game.unpauseGame();
    }

    @Override
    public void stop() {
        _game.pauseGame();
    }

    @Override
    public void destroy() {
        _game.stopGame();
    }
}
