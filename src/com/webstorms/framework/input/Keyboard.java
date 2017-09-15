package com.webstorms.framework.input;

import com.webstorms.framework.Game;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

public class Keyboard extends EventQueue<KeyEvent> implements OnKeyListener {
	
	private boolean[] keys = new boolean[KeyEvent.getMaxKeyCode()];
    
	protected Keyboard(Game game, View view) {
		super(game, view);
		this.view = view;
		this.view.setFocusableInTouchMode(true);
        this.view.requestFocus();
	}
	
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
    	this.addEvent(event);
    	if(keyCode == 24 || keyCode == 25) {
			return false;
			
		}
		return true;

    }

	@Override
	protected void processEvent(KeyEvent event) {
		if(event.getAction() != android.view.KeyEvent.ACTION_UP) {
            if(event.getKeyCode() >= 0 && event.getKeyCode() < keys.length) {
            	this.keys[event.getKeyCode()] = true;
            	
            }
            
        }
		else {
            if(event.getKeyCode() >= 0 && event.getKeyCode() < keys.length) {
            	this.keys[event.getKeyCode()] = false;
            	
            }
            
        }
		
	}

	public boolean isKeyPressed(int keyCode) {
        if (keyCode >= 0 && keyCode < keys.length) {
        	return this.keys[keyCode];
        	
        }
        return false;
        
    }
	
	public void useKeyboard(boolean use) {
		this.inUse = use;
		if(use) {
			this.view.setOnKeyListener(this);
			
		}
		else {
			this.view.setOnKeyListener(null);
			
		}
		
	}
	
	
}
