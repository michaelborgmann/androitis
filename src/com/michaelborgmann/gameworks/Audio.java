package com.michaelborgmann.gameworks;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.AssetFileDescriptor;
import android.media.SoundPool;
import android.media.AudioManager;


public class Audio {

	AssetManager assets;
	SoundPool sounds;
	
	public Audio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		this.sounds = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
	}
	
	public Music loadMusic(String filename) {
		try {
			AssetFileDescriptor descriptor = assets.openFd(filename);
			return new Music(descriptor);
		}
		catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}
	
	public Sound loadSound(String filename) {
		try {
			AssetFileDescriptor descriptor = assets.openFd(filename);
			int id = sounds.load(descriptor, 1);
			return new Sound(sounds, id);
		}
		catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
	}
}