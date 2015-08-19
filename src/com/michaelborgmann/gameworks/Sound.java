package com.michaelborgmann.gameworks;

import android.media.SoundPool;

public class Sound {

	SoundPool sounds;
	int id;
	
	public Sound(SoundPool sounds, int id) {
		this.sounds = sounds;
		this.id = id;
	}
	
	public void play(float volume) {
		sounds.play(id, volume, volume, 0, 0, 1);
	}
	
	public void unload() {
		sounds.unload(id);
	}
	
	public SoundPool getSounds() { return sounds; }
	public int getId() { return id; }
	
}