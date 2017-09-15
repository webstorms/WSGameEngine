package com.webstorms.framework.output;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public class Output extends WSObject {

	private Vibrator vibrator;

	public Output(Game game) {
		super(game);
		this.vibrator = new Vibrator(game);

	}

	// Setter
	
	public void useVibrator(boolean use) {
		this.vibrator.useVibrator(use);

	}

	// "Getter"

	public void vibrate(long milliseconds) {
		this.vibrator.vibrate(milliseconds);

	}
	
}
