package com.webstorms.physics;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.util.FloatMath;

public class Coordinator2D extends WSObject {

	// In real world
	public static final int DIRECTION_NORTH = 0;
	public static final int DIRECTION_EAST = 90;
	public static final int DIRECTION_SOUTH = 180;
	public static final int DIRECTION_WEST = 270;
	
	private float x;
	private float y;
	private float velocity;
	private float acceleration; // m*s*-2
	private int degrees;
	private float verticalVelocity;
	private float horizontalVelocity;
	
	public Coordinator2D(Game game) {
		super(game);
		
	}
	
	public Coordinator2D(Game game, int originalX, int originalY, int velocity, float acceleration, int degrees) {
		super(game);
		this.x = originalX;
		this.y = originalY;
		this.velocity = velocity;
		this.acceleration = acceleration/this.getGame().getGameThread().getSetFPS();
		this.degrees = degrees;
		// this.degrees = degrees;
		
		// Calculate horizontal velocity
		
		// sin (alpha) = opp/hyp
		// sin (degrees) = opp/this.velocity / * this.velocity
		// sin (degrees) * this.velocity = this.horizontalVelocity
		this.horizontalVelocity = (float) FloatMath.sin((float) Math.toRadians(degrees)) * velocity;
		
		// Calculate vertical velocity
	
		// cos (alpha) = adj/hyp
		// cos (degrees) = adj/this.velocity / * this.velocity
		// cos (degrees) * this.velocity = this.verticalVelocity
		this.verticalVelocity = (float) FloatMath.cos((float) Math.toRadians(degrees)) * velocity * -1; // 2D grid is different, P(0,0) is at top left corner of screen, hence the -1
		
	}
	
	public void setLocation(int newX, int newY) {
		this.x = newX;
		this.y = newY;
	
	}
	
	public void setVelocity(float velocity) {
		if(this.velocity != velocity) {
			this.velocity = velocity;
			this.horizontalVelocity = FloatMath.sin((float) Math.toRadians(degrees)) * velocity;
			this.verticalVelocity = FloatMath.cos((float) Math.toRadians(degrees)) * velocity * -1;
			
		}
		
	}
	
	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration/this.getGame().getGameThread().getSetFPS();
		
	}
	
	public void setAngle(int degrees) {
		if(this.degrees != degrees) {
			this.degrees = degrees;
			this.horizontalVelocity = (float) FloatMath.sin((float) Math.toRadians(degrees)) * velocity;
			this.verticalVelocity = (float) FloatMath.cos((float) Math.toRadians(degrees)) * velocity * -1;
			
		}
		
	}
	
	public void update() {
		this.x += this.horizontalVelocity;
		this.y += this.verticalVelocity;
		if(this.acceleration != 0) {
			this.setVelocity(this.getVelocity() + this.acceleration);
			
		}
		
	}
	
	public int getAngle() {
		return this.degrees;
		
	}
	
	public int getX() {
		return (int) this.x;
		
	}
	
	public int getY() {
		return (int) this.y;
		
	}
	
	public float getHorizontalVelocity() {
		return horizontalVelocity;
		
	}
	
	public float getVerticalVelocity() {
		return verticalVelocity;
		
	}
	
	public float getVelocity() {
		return this.velocity;
		
	}
	
	public float getAcceleration() {
		return this.acceleration * this.getGame().getGameThread().getSetFPS();
		
	}
	
	
}
