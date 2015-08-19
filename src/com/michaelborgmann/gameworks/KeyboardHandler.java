package com.michaelborgmann.gameworks;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.View.OnKeyListener;

import com.michaelborgmann.gameworks.Input.KeyEvent;
import com.michaelborgmann.gameworks.Pool.PoolObjectFactory;

public class KeyboardHandler implements OnKeyListener {
	
	boolean[] pressedKeys = new boolean[128];
	Pool<KeyEvent> keyEventPool;
	List<KeyEvent> keyEventsBuffer = new ArrayList<KeyEvent>();    
	List<KeyEvent> keyEvents = new ArrayList<KeyEvent>();	
	
	public KeyboardHandler(View view) {
		
		PoolObjectFactory<KeyEvent> factory = new PoolObjectFactory<KeyEvent>() {
			public KeyEvent createObject() { return new KeyEvent(); }
		};
		
		keyEventPool = new Pool<KeyEvent>(factory, 100);
		view.setOnKeyListener(this);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		
	}
	
    public boolean onKey(View v, int keycode, android.view.KeyEvent event) {
    	
    	if (event.getAction() == android.view.KeyEvent.ACTION_MULTIPLE) return false;
    	
    	synchronized (this) {
    		
    		KeyEvent keyEvent = keyEventPool.newObject();
    		keyEvent.keycode = keycode;
    		keyEvent.keychar = (char) event.getUnicodeChar();
    		
    		if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
    			keyEvent.type = KeyEvent.KEY_DOWN;
    			if (keycode > 0 && keycode < 127)
    				pressedKeys[keycode] = true;
    		}
    		
    		if (event.getAction() == android.view.KeyEvent.ACTION_UP) {
    			keyEvent.type = KeyEvent.KEY_UP;
    			if (keycode > 0 && keycode < 127)
    				pressedKeys[keycode] = false;    			
    		}
    		
    		keyEventsBuffer.add(keyEvent);
    	}
    	
    	return false;
    }
    
    public boolean isKeyPressed(int keycode) {
    	if (keycode < 0 || keycode > 127) return false;
    	return pressedKeys[keycode];
    }
    
    public List<KeyEvent> getKeyEvents() {
    	
    	synchronized (this) {
    		
    		int length = keyEvents.size();
    		for (int i = 0; i < length; i++)
    			keyEventPool.free(keyEvents.get(i));
    		
    		keyEvents.clear();
    		keyEvents.addAll(keyEventsBuffer);
    		keyEventsBuffer.clear();
    		
    		return keyEvents;
    	}
    }

}