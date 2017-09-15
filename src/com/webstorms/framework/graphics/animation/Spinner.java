package com.webstorms.framework.graphics.animation;

public class Spinner {

	private int value;
	private float velocity;
	
	public Spinner() {
		
	}
	
	public Spinner(float velocity) {
		this.velocity = velocity;
		
	}
	
	public void setValue(int value) {
		this.value = value;
		
	}
	
	public void setVelocity(float velocity) {
		this.velocity = velocity;
		
	}
	
	public void update() {
		if(this.value != 360 && this.value != -360 && this.value < 360 && this.value > -360) {
			this.value += velocity;
			
		}
		else {
			this.value = 0;
			
		}
		
	}
	
	public int getValue() {
		return this.value;
		
	}
	
	public float getVelocity() {
		return this.velocity;
		
	}
	
	
}
