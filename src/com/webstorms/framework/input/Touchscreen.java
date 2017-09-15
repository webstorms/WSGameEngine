package com.webstorms.framework.input;

import com.webstorms.framework.Game;
import com.webstorms.framework.util.Util;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Touchscreen extends EventQueue<MotionEvent> implements OnTouchListener {
	
	private Integer touchX;
	private Integer touchY;
	
	protected Touchscreen(Game game, View view) {
		super(game, view);
		this.view = view;
		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		this.addEvent(event);
		return true;
		
	}

	@Override
	protected void processEvent(MotionEvent event) {
		if((event.getAction() != MotionEvent.ACTION_UP)) {	
			
			boolean xCoordinateIsValid = Util.inRangeFrom(this.getGame().getWSScreen().getLeftLetterboxLength(), this.getGame().getWSScreen().getInverseRightLetterboxLength(), (int)(event.getX()));
			boolean yCoordinateIsValid = Util.inRangeFrom(this.getGame().getWSScreen().getTopLetterboxLength(), this.getGame().getWSScreen().getInverseBottumLetterboxLength(), (int)(event.getY()));
			
			if(xCoordinateIsValid && yCoordinateIsValid) {
				touchX = this.processRawXValue(event.getX());
				touchY = this.processRawXValue(event.getY());	
				
			}
			
		}
		else {
        	this.touchX = null;
        	this.touchY = null;
        	
		}
		
	}
	
	public int processRawXValue(float rawX) {
		// Convert the touch hence the ability for a valid engagement with the UI
		// minus leftLetterBox will subtract from the screens display if its (+) and add if its (-)
		// This applies with the scaleProportionallyScreen and fillProportionallyScreen methods
		// NOTE: Has to be leftLetterBox and not rightLetterbox
		return (int) ((rawX - this.getGame().getWSScreen().getLeftLetterboxLength()) * this.getGame().getWSScreen().getScaleXFromRealToVirtual());
		
	}
	
	public int processRawYValue(float rawY) {
		// Convert the touch hence the ability for a valid engagement with the UI
		// minus topLetterbox will subtract from the screens display if its (+) and add if its (-)
		// This applies with the scaleProportionallyScreen and fillProportionallyScreen methods
		// NOTE: Has to be topLetterbox and not bottumLetterbox
		return (int) ((rawY - this.getGame().getWSScreen().getTopLetterboxLength()) * this.getGame().getWSScreen().getScaleYFromRealToVirtual());
	}
	
	public boolean isTouchDown() {
		return this.touchX != null && this.touchY != null;
		
	}
	
	public Integer getTouchX() {
		return this.touchX;
		
	}
	
	public Integer getTouchY() {
		return this.touchY;
		
	}
	
	public void useTouchscreen(boolean use) {
		this.inUse = use;
		if(use) {
			this.view.setOnTouchListener(this);
			
		}
		else {
			this.view.setOnTouchListener(null);
			
		}
		
	}
	
	
}
