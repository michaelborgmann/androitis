package com.michaelborgmann.androitis;

import java.util.List;

import com.michaelborgmann.gameworks.Game;
import com.michaelborgmann.gameworks.Graphics;
import com.michaelborgmann.gameworks.Screen;
import com.michaelborgmann.gameworks.Input.TouchEvent;

public class HelpScreen extends Screen {

	public HelpScreen(Game game) { super(game); }

    public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int length = touchEvents.size();
		for (int i = 0; i < length; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 256 && event.y > 416) {
					game.setScreen(new HelpScreen2(game));
					if (Settings.soundsystem) Assets.click.play(1);
					return;
				}
			}
		}
    }
    public void render(float deltaTime) {
    	Graphics graphics = game.getGraphics();
    	graphics.drawPixmap(Assets.background, 0, 0);
    	graphics.drawPixmap(Assets.help1, 64, 100);
    	graphics.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
    }
	public void pause() {}
	public void resume() {}
	public void remove() {}
	
}