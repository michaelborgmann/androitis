package com.michaelborgmann.androitis;

import com.michaelborgmann.gameworks.Game;
import com.michaelborgmann.gameworks.Graphics;
import com.michaelborgmann.gameworks.Graphics.PixmapFormat;
import com.michaelborgmann.gameworks.Screen;

public class LoadingScreen extends Screen {

	public LoadingScreen(Game game) { super(game); }
	
	public void update(float deltaTime) {
		Graphics graphics = game.getGraphics();
		Assets.background = graphics.loadPixmap("background.png", PixmapFormat.RGB565);
		Assets.logo = graphics.loadPixmap("logo.png", PixmapFormat.ARGB4444);
		Assets.mainmenu = graphics.loadPixmap("mainmenu.png", PixmapFormat.ARGB4444);
		Assets.buttons = graphics.loadPixmap("buttons.png", PixmapFormat.ARGB4444);
		Assets.help1 = graphics.loadPixmap("help1.png", PixmapFormat.ARGB4444);
		Assets.help2 = graphics.loadPixmap("help2.png", PixmapFormat.ARGB4444);
		Assets.help3 = graphics.loadPixmap("help3.png", PixmapFormat.ARGB4444);
		Assets.numbers = graphics.loadPixmap("numbers.png", PixmapFormat.ARGB4444);
		Assets.click = game.getAudio().loadSound("click.ogg");
		Assets.song = game.getAudio().loadMusic("brasilmontuno.mp3");
		
		game.setScreen(new MainMenuScreen(game));
	}
	
	public void render(float deltaTime) {}
	public void pause() {}
	public void resume() {}
	public void remove() {}
	
}