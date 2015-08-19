package com.michaelborgmann.androitis;

import com.michaelborgmann.gameworks.Game;
import com.michaelborgmann.gameworks.Screen;

public class GameActivity extends Game {

	public Screen getStartScreen() { return new LoadingScreen(this); }
	
}
