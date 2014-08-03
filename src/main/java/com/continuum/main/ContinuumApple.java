package com.continuum.main;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.applet.Applet;
import java.awt.*;

/**
 * The heart and soul of Continuum.
 */
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
				} catch (InterruptedException e) {}
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
                    Display.setParent(_canvas);;
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
