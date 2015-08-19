package com.michaelborgmann.gameworks;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

abstract public class Game extends Activity {

	FileIO assets;
	Audio audio;
	Music music;
	Sound sound;
	Input input;
	FastRenderView renderView;
	Graphics graphics;
	Screen screen;
	WakeLock wakeLock;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		// set fullscreen mode
		getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		// portrait orientation 
		boolean isPortrait = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
		
		int screen_width = isPortrait ? 320 : 480;
		int screen_height = isPortrait ? 480 : 320;

		// create fixed framebuffer (320 x 480)
		Bitmap framebuffer = Bitmap.createBitmap(screen_width, screen_height, Config.RGB_565);

		// scale framebuffer to screen sizes
		float scaled_width = (float) screen_width / getWindowManager().getDefaultDisplay().getWidth();
		float scaled_height = (float) screen_height / getWindowManager().getDefaultDisplay().getHeight();
		
		// initialize game framework
		renderView = new FastRenderView(this, framebuffer);
		graphics = new Graphics(getAssets(), framebuffer);
		screen = getStartScreen();
		assets = new FileIO(getAssets());
		input = new Input(this, renderView, scaled_width, scaled_height);
		audio = new Audio(this);
		
		setContentView(renderView);

		// Wake Lock is faulty!?
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
		//wakeLock.acquire();
	}

	public void onPause() {
		super.onPause();
//		wakeLock.release();
		renderView.pause();
		screen.pause();
		if (isFinishing()) screen.remove();
	}

	public void onResume() {
		super.onResume();
//		wakeLock.acquire();
		screen.resume();
		renderView.resume();
	}
	
	public Input getInput() { return input; }

	public FileIO getFileIO() { return assets; }
	
	public Audio getAudio() { return audio; }
	
	public Graphics getGraphics() { return graphics; };
	
	public Screen getCurrentScreen() { return screen; }
	
	public void setScreen(Screen screen) {
		if (screen == null) throw new IllegalArgumentException("Screen must not be null");
		this.screen.pause();
		this.screen.remove();
		screen.resume();
		screen.update(0);
		this.screen = screen;
	}
	
	abstract public Screen getStartScreen();

}
