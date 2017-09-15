package com.webstorms.framework;

public class WSObject {

	private Game game;
	private String classTag = this.getClass().getSimpleName();
	
	protected WSObject(Game game) {
		this.game = game;
		
	}
	
	public Game getGame() {
		return this.game;
		
	}
	
	public String getClassTag() {
		return this.classTag;
		
	}

	
}
