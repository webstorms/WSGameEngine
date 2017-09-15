package com.webstorms.framework.util;

public class Executor {

	private boolean hasExecuted;
	private Runnable runnable;
	
	public Executor(Runnable runnable) {
		this.runnable = runnable;
		
	}
	
	public void execute() {
		if(!hasExecuted) {
			hasExecuted = true;
			this.runnable.run();
			
		}
		
	}
	
	public void reset() {
		this.hasExecuted = false;
		
	}
	
	
}
