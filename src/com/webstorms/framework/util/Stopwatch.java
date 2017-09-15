package com.webstorms.framework.util;

public class Stopwatch {
	
	// TODO: Add Pause and Resume code
	// Also add getSeconds, getMilliseconds methods
	
	int startTime;
	
	public void start() {
		startTime = (int) System.currentTimeMillis();
		
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	public int stop() {
		return (int) (System.currentTimeMillis() - startTime);
		
	}
	
	
}
