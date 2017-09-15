package com.webstorms.physics;

import com.webstorms.framework.gameObject.GameObject;
import com.webstorms.framework.util.Util;

public class Physics {
	
	// Last updated: 15/05/2012
	// Improvements needed: Add error log when user calls shapeInShape() method and he hasn't added any context to his shape
	// Known Bugs:
	
	public final String classTAG = this.getClass().getSimpleName();
	// Collision Detection
	
	public static boolean checkCollisionDetection(GameObject gameObject1, GameObject gameObject2) {
		boolean collision = false;
		
		// Get velocities
		int horizontalVelocity = (int) gameObject1.getMovementCoordinator().getHorizontalVelocity();
		int verticalVelocity = (int) gameObject1.getMovementCoordinator().getVerticalVelocity();
		
		if(horizontalVelocity == 0 && verticalVelocity == 0) {
			// This thing is like a rock that doesn't move
			if(Shape.intersects(gameObject1.getShape(), gameObject2.getShape())) {
			collision = true;
				
			}
			
		}
		else {
			
			// Save location
			int initialX = gameObject1.getMovementCoordinator().getX();
			int initialY = gameObject1.getMovementCoordinator().getY();
			
			// Save if the velocities are either + or -
			boolean horizontalVelocityIsNegative;
			boolean verticalVelocityIsNegative;
			
			if(horizontalVelocity < 0) {
				horizontalVelocityIsNegative = true;
				
			}
			else {
				horizontalVelocityIsNegative = false;
			}
			
			if(verticalVelocity < 0) {
				verticalVelocityIsNegative = true;
				
			}
			else {
				verticalVelocityIsNegative = false;
			}
			
			// Make velocities + (if not already so)
			if(horizontalVelocityIsNegative == true) {
				horizontalVelocity *= -1;
			}
			
			if(verticalVelocityIsNegative == true) {
				verticalVelocity *= -1;
			}
			
			// Calculate the partions
			int iteratePartion = 0;
			float horizontalPartion = 0;
			float verticalPartion = 0;
			
			if(horizontalVelocity > verticalVelocity) {
				iteratePartion = horizontalVelocity;
				horizontalPartion = 1; // which is the same as horizontalVelocity/iteratePartion;
				verticalPartion = verticalVelocity/iteratePartion;
				
			}
			else {
				iteratePartion = verticalVelocity;
				horizontalPartion = horizontalVelocity/iteratePartion;
				verticalPartion = 1; // which is the same as verticalVelocity/iteratePartion;
				
			}
			
			// Make partions - if that was their initial state
			if(horizontalVelocityIsNegative != true) {
				horizontalPartion *= -1;
				
			}
			
			if(verticalVelocityIsNegative != true) {
				verticalPartion *= -1;
				
			}
			
	//		Log.d("SpaceDroid", "+++START+++");
			
			for(int a = 0; a < iteratePartion; a++) {
				
	//			Log.d("SpaceDroid", "BX: " + gameObject1.getMovementCoordinator().getX());
	//			Log.d("SpaceDroid", "BY: " + gameObject1.getMovementCoordinator().getY());
				
				// Magic happens here
				gameObject1.setLocation((int) (gameObject1.getMovementCoordinator().getX() + horizontalPartion), (int) (gameObject1.getMovementCoordinator().getY() + verticalPartion));
				
	//			Log.d("SpaceDroid", "AX: " + gameObject1.getMovementCoordinator().getX());
	//			Log.d("SpaceDroid", "AY: " + gameObject1.getMovementCoordinator().getY());
				
				if(Shape.intersects(gameObject1.getShape(), gameObject2.getShape())) {
					collision = true;
					
				}
				
			}
			
	//		Log.d("SpaceDroid", "+++END+++");
			
			// Revert to original
			gameObject1.setLocation(initialX, initialY);
			
			
		}
	
		return collision;
	}

	public static int getImpulseOfCollision(GameObject gameObject1, GameObject gameObject2) {
		return (int) Util.round(gameObject1.getWeight() * gameObject1.getMovementCoordinator().getVelocity() + gameObject2.getWeight() * gameObject2.getMovementCoordinator().getVelocity(), 0);
		
	}
	
	
}
