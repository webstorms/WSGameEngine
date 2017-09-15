package com.webstorms.framework.graphics.animation.buttons;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public abstract class Button extends WSObject {
	
	public static final int TYPE_VIRTUAL = 0;
	public static final int TYPE_PHYSICAL = 1;
	
	String name;
	boolean isPressed;
	ButtonListener listener;
	
	protected Button(Game game, String name, ButtonListener listener) {
		super(game);
		this.name = name;
		this.listener = listener;
		
	}
	
	public String getName() {
		return this.name;
		
	}
	
	public boolean isPressed() {
		return this.isPressed;
		
	}
	
	public abstract int getButtonType();
	
	
}
