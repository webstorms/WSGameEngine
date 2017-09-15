package com.webstorms.framework;

public abstract class Screen extends WSObject {

	/*
	 * In the pause and dispose method, listeners are being disposed, check if they exist first!
	 * 
	 */
	
	/**
	 * This class is the blueprint for any screen you create and use within the game. It handles the lower level things
	 * and works in a cooperative manner with the game engine.
	 * 
	 */
	
	public Screen(Game game) {
		super(game);
		
	}
	
	public void load() {
		
	}
	
	/**
	 * <b><i>public void update()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Leave this method as it is and to add functionality to this screen extend following methods:
	 * <br>
	 * public void introTransitionUpdate()
	 * <br>
	 * public void introTransitionCompletedUpdate()
	 * <br>
	 * public void outTransitionUpdate()
	 * <br>
	 * public void outTransitionCompletedUpdate()
	 *	
	 */
	
	public abstract void update();
	
	/**
	 * <b><i>public void render()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Override the method to set what you would like to render.
	 *	
	 */
	
	public abstract void render();
	
	/**
	 * <b><i>public void resume()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Initialize all the sensory that should be used within this screen.
	 * This will be called when the activity resumes.
	 *	
	 */
	
	public void resume() {
		
	}
	
	/**
	 * <b><i>public void pause()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will disable all sensory and will be called when the activity pauses.
	 *	
	 */
	
	public void pause() {
		this.getGame().getInput().useKeyboard(false);
		this.getGame().getInput().useTouchscreen(false);
		this.getGame().getInput().useAccelerometer(false);
		
		this.getGame().getInput().setOnKeyListener(null);
		this.getGame().getInput().setOnTouchListener(null);
		
	}
	
	/**
	 * <b><i>public void onDispose()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will be called when the screen is to be disposed. You can do all your cleanups in here.
	 *	
	 */
	
	public void onDispose() {
		
	}
	
	
}
