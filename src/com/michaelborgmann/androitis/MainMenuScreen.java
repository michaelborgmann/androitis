package com.michaelborgmann.androitis;

import java.util.List;

import com.michaelborgmann.gameworks.Game;
import com.michaelborgmann.gameworks.Graphics;
import com.michaelborgmann.gameworks.Input.TouchEvent;
import com.michaelborgmann.gameworks.Screen;

public class MainMenuScreen extends Screen {

	public MainMenuScreen(Game game) { super(game);
		Assets.song.setLooping(true);
		Assets.song.play();
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if (event.x > x && event.x < x + width - 1 &&
			event.y > y && event.y < y + height - 1)
			return true;
		else return false;
	}
	
	public void update(float deltatime) {
		Graphics graphics = game.getGraphics();
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (inBounds(event, 0, graphics.getHeight() - 64, 64, 64)) {
					Settings.soundsystem = !Settings.soundsystem;
					if (Settings.soundsystem) {
						Assets.song.stop();		
					}
					else {
						Assets.click.play(1);
						Assets.song.play();
					}
				}
				
				if (inBounds(event, 82, 224, 154, 31)) {
					game.setScreen(new GameScreen(game));
					if (Settings.soundsystem)
						Assets.click.play(1);
					return;
				}
				
				if (inBounds(event, 82, 224 + 31, 154, 31)) {
					game.setScreen(new HighscoreScreen(game));
					if (Settings.soundsystem)
						Assets.click.play(1);
					return;					
				}
				
				if (inBounds(event, 82, 224 + 62, 154, 31)) {
					game.setScreen(new HelpScreen(game));
					if (Settings.soundsystem)
						Assets.click.play(1);
					return;
				}
			}
		}
	}
	
	public void render(float deltaTime) {
		Graphics graphics = game.getGraphics();
		graphics.drawPixmap(Assets.background, 0, 0);
		graphics.drawPixmap(Assets.logo, 32, 20);
		graphics.drawPixmap(Assets.mainmenu, 64, 220);
		
		if (!Settings.soundsystem)
			graphics.drawPixmap(Assets.buttons, 0, 416, 0, 0, 64, 64);
		else
			graphics.drawPixmap(Assets.buttons, 0, 416, 64, 0, 64, 64);
	}

	public void pause() { Settings.save(game.getFileIO()); }
	public void resume() {}
	public void remove() {}
}