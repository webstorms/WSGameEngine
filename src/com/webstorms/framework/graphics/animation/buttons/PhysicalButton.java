package com.webstorms.framework.graphics.animation.buttons;

import com.webstorms.framework.Game;
import com.webstorms.framework.util.Util;

public final class PhysicalButton extends Button {
	
	int keyCode;
	
	public PhysicalButton(Game game, String name, ButtonListener listener, int keyCode) {
		super(game, name, listener);
		this.keyCode = keyCode;
		
	}	
	
	@Override
	public int getButtonType() {
		return Button.TYPE_PHYSICAL;
	}
	
	public void update(Integer touchKey) {
		if(touchKey != null) {
			if(Util.isKeyPressed(touchKey, this.keyCode)) {
				this.isPressed = true;
				
			}
			else {
				this.isPressed = false;
				
			}	
			
		}
		else if(this.isPressed()) {
			this.isPressed = false;
			this.listener.onButtonClicked(this);
			
		}
		
	}
	
	
}