package com.webstorms.framework.graphics.animation.buttons;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.webstorms.framework.Game;
import com.webstorms.framework.graphics.animation.GraphicObject;
import com.webstorms.physics.Shape;

public class VirtualButton extends Button {
	
	private Bitmap buttonTouched;
	GraphicObject graphicObject = new GraphicObject(this.getGame());
	
	public VirtualButton(Game game, String name, ButtonListener listener, int x, int y, Rect dimensions, Bitmap normal, Bitmap touched) {
		super(game, name, listener);
		this.graphicObject.setBitmap(normal);
		this.graphicObject.getShape().addRectangle(dimensions);
		this.graphicObject.setLocation(x, y);
		this.buttonTouched = touched;
		
	}
	
	@Override
	public int getButtonType() {
		return Button.TYPE_VIRTUAL;
		
	}
	
	public int getX() {
		return this.graphicObject.getX();
		
	}
	
	public int getY() {
		return this.graphicObject.getY();
		
	}
	
	public Shape getShape() {
		return this.graphicObject.getShape();
		
	}
	
	public void update(Integer touchX, Integer touchY) {
		if(touchX != null && touchY != null) {			
			if(graphicObject.getShape().contains(touchX, touchY)) {
				this.isPressed = true;
				
			}
			else {
				this.isPressed = false;
				
			}
		}
		else if(this.isPressed == true) {
			this.isPressed = false;
			this.listener.onButtonClicked(this);
			
		}
		
	}
	
	public void render() {
		if(!this.isPressed) {
			graphicObject.renderBitmap();
			
		}
		else {
			this.getGame().getGraphics().drawBitmap(this.buttonTouched, null, graphicObject.getShape().getRect(), null);
			
		}
		
	}
	
	
}
