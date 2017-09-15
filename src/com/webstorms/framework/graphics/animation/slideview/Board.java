package com.webstorms.framework.graphics.animation.slideview;

import com.webstorms.framework.Game;
import com.webstorms.framework.graphics.animation.GraphicObject;

public abstract class Board extends GraphicObject {
	
	/** Extend this class to create your own Boards that can be displayed in a slide view */
	
	protected Board(Game game) {
		super(game);
		
	}
	
	public void update(int velocity) {
		this.getShape().moveShape(velocity, 0);
		
	}
	
	
}
