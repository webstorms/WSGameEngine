package com.webstorms.framework.output;

import android.content.Context;

import com.webstorms.framework.Game;

public class Vibrator extends OutputSensor {

	private android.os.Vibrator v;

	protected Vibrator(Game game) {
		super(game);

	}

	public void vibrate(long milliseconds) {
		if(this.inUse) {
			v.vibrate(milliseconds);
		}

	}

	public void useVibrator(boolean use) {
		this.inUse = use;
		if(use) {
			v = (android.os.Vibrator) this.getGame().getSystemService(Context.VIBRATOR_SERVICE);

		}
		else {
			v = null;


		}

	}


}
