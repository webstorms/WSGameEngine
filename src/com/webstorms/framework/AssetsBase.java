package com.webstorms.framework;

public abstract class AssetsBase extends WSObject {
	
	protected AssetsBase(Game game) {
		super(game);
		
	}

	public abstract void load();
	
	public abstract void onDispose();
	
	
}
