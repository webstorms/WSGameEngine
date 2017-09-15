package com.webstorms.framework.output;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public class OutputSensor extends WSObject {

	protected boolean inUse;
	
	protected OutputSensor(Game game) {
		super(game);
		
	}
	
	public boolean inUse() {
    	return this.inUse;
    	
    }
	
	
}
