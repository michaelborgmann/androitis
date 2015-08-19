package com.michaelborgmann.gameworks;

import java.io.IOException;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Music implements OnCompletionListener {

	MediaPlayer player;
	boolean loaded = false;
	
	public Music(AssetFileDescriptor descriptor) {
		player = new MediaPlayer();
		try {
			player.setDataSource(
					descriptor.getFileDescriptor(),
					descriptor.getStartOffset(),
					descriptor.getLength());
			player.prepare();
			loaded = true;
			player.setOnCompletionListener(this);
		}
		catch (Exception e) {
			throw new RuntimeException("Couldn't load music");
		}
	}
	
	public void onCompletion(MediaPlayer player) {
		synchronized (this) { loaded = false; }
	}
	
	public void unload() {
		if (player.isPlaying()) player.stop();
		player.release();
	}
	
	public boolean isLooping() {
		return player.isLooping();
	}
	
	public boolean isPlaying() {
		return player.isPlaying();
	}
   
	public boolean isStopped() {
		return !loaded;
	}
	
	public void play() {
		if (player.isPlaying()) return;
		
		try {
			synchronized (this) {
				if (!loaded) player.prepare();
					player.start();
			}
		}
		catch (IllegalStateException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}

	public void setLooping(boolean looping) {
		player.setLooping(looping);
	}
	
	public void setVolume(float volume) {
		player.setVolume(volume,  volume);
	}

	public void stop() {
		player.stop();
		synchronized (this) { loaded = false; }
	}
	
	public void pause() {
		if (player.isPlaying()) player.pause();
	}
}