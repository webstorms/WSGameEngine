package com.webstorms.physics;

import android.graphics.Rect;
import android.util.FloatMath;
import android.util.Log;

public class Circle {

	private int centerX;
	private int centerY;
	private int radius;
	
	public Circle(int radius, int centerX, int centerY) {
		this.radius = radius;
		this.centerX = centerX;
		this.centerY = centerY;
		
	}
	
	// Setter methods
	
	public void setRadius(int newRadius) {
		this.radius = newRadius;
		
	}
	
	public void setCenterX(int newCenterX) {
		this.centerX = newCenterX;
		
	}
	
	public void setCenterY(int newCenterY) {
		this.centerY = newCenterY;
		
	}
	
	// Getter methods
	
	public int getRadius() {
		return this.radius;
		
	}
	
	public int getCenterX() {
		return this.centerX;
		
	}
	
	public int getCenterY() {
		return this.centerY;
		
	}
	
	public int getLeft() {
		return this.centerX - this.radius;
		
	}
	
	public int getRight() {
		return this.centerX + this.radius;
		
	}
	
	public int getTop() {
		return this.centerY - this.radius;
		
	}
	
	public int getBottom() {
		return this.centerY + this.radius;
		
	}
	
	public boolean contains(int pX, int pY) {
		// Use Pythagorean theorem
		int a = pX - this.getCenterX();
		int b = pY - this.getCenterY();
		float c = (float) FloatMath.sqrt((float) (Math.pow(a, 2) + Math.pow(b, 2)));
		
		if(this.getRadius() == c || this.getRadius() > c) {
			return true;
			
		}
		else {
			return false;
			
		}
		
	}
	
	public boolean contains(Circle circle) {
		return Circle.intersects(this, circle);
		
	}
	
	public static boolean intersects(Circle circle1, Circle circle2) {
		
		// Use Pythagorean theorem
		int a = circle1.getCenterX() - circle2.getCenterX();
		int b = circle1.getCenterY() - circle2.getCenterY();
		float c = (float) FloatMath.sqrt((float) (Math.pow(a, 2) + Math.pow(b, 2)));
		
		if((circle1.getRadius() + circle2.getRadius()) == c || (circle1.getRadius() + circle2.getRadius()) > c) {
			// Log.d("SpaceDroid", "Motherducking bug!");
			return true;
			
		}
		else {
			return false;
			
		}
				
	}
	
	public static boolean intersects(Rect rect, Circle circle) {

		boolean intersection = false;
		
		// Check Rect 4 corner points
		
		// First point of Rect: top left
		if(circle.contains(rect.left, rect.top)) {
			intersection = true;
			
		}
				
		// Second point of Rect: top right
		else if(circle.contains(rect.right, rect.top)) {
			intersection = true;
			
		}
		
		// Third point of Rect: bottom left
		else if(circle.contains(rect.left, rect.bottom)) {
			intersection = true;
			
		}
		
		// Fourth point of Rect: bottom right
		else if(circle.contains(rect.right, rect.bottom)) {
			intersection = true;
			
		}
		
		// Check Circle 4 side points
		
		// First point: left middle
		
		else if(rect.contains(circle.getLeft(), circle.getCenterY())) {
			intersection = true;
			
		}
		
		// First point: right middle
		
		else if(rect.contains(circle.getRight(), circle.getCenterY())) {
			intersection = true;
			
		}
		
		// First point: middle top
		
		else if(rect.contains(circle.getCenterX(), circle.getTop())) {
			intersection = true;
			
		}
		
		// First point: middle bottom
		
		else if(rect.contains(circle.getCenterX(), circle.getBottom())) {
			intersection = true;
			
		}
		
		return intersection;
	}
	
	
}
