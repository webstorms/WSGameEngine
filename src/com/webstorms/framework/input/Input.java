package com.webstorms.framework.input;

import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public class Input extends WSObject {
	
	Keyboard keyboard;
	Touchscreen touchscreen;
	Accelerometer accelerometer;
	OnKeyListener onKeyListener;
	OnTouchListener onTouchListener;
	
	public Input(Game game, View view) {
		super(game);
		this.keyboard = new Keyboard(game, view);
		this.touchscreen = new Touchscreen(game, view);
		this.accelerometer = new Accelerometer(game);
		
	}
    
	public void update() {
		if(this.keyboard.inUse()) {
			this.keyboard.update();
			for(KeyEvent event : this.keyboard.getEvents()) {
				if(this.onKeyListener != null) {
					if(event.getAction() != KeyEvent.ACTION_UP) {
						this.onKeyListener.onKey(event.getKeyCode());
						
					}
					else {
						this.onKeyListener.onKey(null);
						
					}
					
				}				
				
			}			
			
		}
		if(this.touchscreen.inUse()) {
			this.touchscreen.update();
			for(MotionEvent event : this.touchscreen.getEvents()) {
				if(this.onTouchListener != null) {
					if(event.getActionMasked() != MotionEvent.ACTION_UP) {
						this.onTouchListener.onTouch(this.touchscreen.processRawXValue(event.getX()), this.touchscreen.processRawYValue(event.getY()));
						
					}
					if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
						this.onTouchListener.onDown(this.touchscreen.processRawXValue(event.getX()), this.touchscreen.processRawYValue(event.getY()));
						
					}
					else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
						this.onTouchListener.onTouch(null, null);
						
					}
					
				}
				
			}	
				
		}	
		
	}
	
	// Setter
	
	public void useKeyboard(boolean use) {
		this.keyboard.useKeyboard(use);
		
	}
	
	public void useTouchscreen(boolean use) {
		this.touchscreen.useTouchscreen(use);
		
	}
	
	public void useAccelerometer(boolean use) {
		this.accelerometer.useAccelerometer(use);
		
	}
	
	public void setOnKeyListener(OnKeyListener onKeyListener) {
    	this.onKeyListener = onKeyListener;
    	
    }
	
	public void setOnTouchListener(OnTouchListener onTouchListener) {
    	this.onTouchListener = onTouchListener;
    	
    }
	
	// Getter
    
	public boolean isKeyPressed(int keyCode) {
		return this.keyboard.isKeyPressed(keyCode);
        
    }
	
	public boolean isTouchDown() {
        return this.touchscreen.isTouchDown();
        
    }
    
    public int getTouchX() {
        return this.touchscreen.getTouchX();
        
    }
    
    public int getTouchY() {
    	return this.touchscreen.getTouchY();
        
    }
    
    public float getAccelX() {
    	return this.accelerometer.getAccelX();
    	
    }
    
    public float getAccelY() {
    	return this.accelerometer.getAccelY();
    	
    }
    
    public float getAccelZ() {
    	return this.accelerometer.getAccelZ();
    	
    }
	
	
}