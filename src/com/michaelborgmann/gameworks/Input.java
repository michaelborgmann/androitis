package com.michaelborgmann.gameworks;

import java.util.List;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public class Input {
	
	AccelerometerHandler accelerometer;
	TouchHandler touchpad;
	KeyboardHandler keyboard;
	
	public static class TouchEvent {
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_DRAGGED = 2;
		
		public int type;
		public int x, y;
		public int pointer;
		
		public String toString() {
			
			StringBuilder builder = new StringBuilder();
			
			if (type == TOUCH_DOWN) builder.append("touch down, ");
			else if (type == TOUCH_DRAGGED) builder.append("touch dragged, ");
			else builder.append("touch up, "); 
			
			builder.append(pointer);
			builder.append(",");
			builder.append(x);
			builder.append(",");
			builder.append(y);
			
			return builder.toString();
		}
	}

	public static class KeyEvent {
		public static final int KEY_DOWN = 0;
		public static final int KEY_UP = 1;
		
		public int type;
		public int keycode;
		public char keychar;
		
		public String toString() {
			
			StringBuilder builder = new StringBuilder();
			
			if (type == KEY_DOWN) builder.append("key down, ");
			else builder.append("key up, ");
			
			builder.append(keycode);
			builder.append(",");
			builder.append(keychar);
			
			return builder.toString();
		}
	}

	public Input(Context context, View view, float scaled_width, float scaled_height) {
		
		accelerometer = new AccelerometerHandler(context);
		keyboard = new KeyboardHandler(view);
		if (Integer.parseInt(VERSION.SDK) < 5)
			touchpad = new SingleTouchHandler(view, scaled_width, scaled_height);
		else
			touchpad = new MultiTouchHandler(view, scaled_width, scaled_height);
	}

 	public float getAccelX() { return accelerometer.getAccelX(); }
	public float getAccelY() { return accelerometer.getAccelY(); }
	public float getAccelZ() { return accelerometer.getAccelZ(); }

	public int getTouchX(int pointer) { return touchpad.getTouchX(pointer); }
	public int getTouchY(int pointer) { return touchpad.getTouchY(pointer); }
	public List<TouchEvent> getTouchEvents() { return touchpad.getTouchEvents(); }
	
	public boolean isKeyPressed(int keycode) { return keyboard.isKeyPressed(keycode); }
	public boolean isTouchDown(int pointer) { return touchpad.isTouchDown(pointer); }
	public List<KeyEvent> getKeyEvents() { return keyboard.getKeyEvents(); }
	
}