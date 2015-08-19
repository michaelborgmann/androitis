package com.michaelborgmann.gameworks;

import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.michaelborgmann.gameworks.Game;

public class FastRenderView extends SurfaceView implements Runnable {
	
	Game game;
	Bitmap framebuffer;
	SurfaceHolder surface;
	volatile boolean running = false;
	Thread thread = null;

	
	public FastRenderView(Game game, Bitmap framebuffer) {
		super(game);
		this.game = game;
		this.framebuffer = framebuffer;
		this.surface = getHolder();
	}

	public void run() {
	
		Rect destination = new Rect();
		long startTime = System.nanoTime();
		
		while (running) {
			if (!surface.getSurface().isValid()) continue;

			// time difference since last frame
			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
			startTime = System.nanoTime();
			
			// update game logic
			game.getCurrentScreen().update(deltaTime);
			game.getCurrentScreen().render(deltaTime);
			
			// time sin
			Canvas canvas = surface.lockCanvas();
			canvas.getClipBounds(destination);
			canvas.drawBitmap(framebuffer, null, destination, null);
			surface.unlockCanvasAndPost(canvas);
		}
	}

	public void pause() {
		running = false;
		while (true) {
			try { thread.join(); }
			catch (InterruptedException e) { }
		}
	}

	public void resume() {
		running = true;
		thread = new Thread(this);
		thread.start();
	}
}
