package com.webstorms.framework.gameObject;

import com.webstorms.framework.Game;
import com.webstorms.framework.graphics.animation.GraphicObject;
import com.webstorms.physics.Coordinator2D;
import com.webstorms.physics.Physics;

public abstract class GameObject extends GraphicObject {
	
	private WorldObject world;
	private boolean collidable;
	private float weight;
	private Coordinator2D movementCoordinator;
	
	/**
	 * Extend this class when creating game objects in your game.
	 * 
	 */
	
	// Needed: World, gameObjectType
	
	protected GameObject(Game game, WorldObject world) {
		super(game);
		this.world = world;
		this.collidable = true;
		this.movementCoordinator = new Coordinator2D(this.getGame());
		
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
		
	}
	
	public float getWeight() {
		return this.weight;
		
	}
	
	/**
	 * <b><i>public void setCollidable(boolean state)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set if this game object is collidable. If it's collidable, it can collide with other game objects, if not, then it can't. 
	 * For example when a game object is exploding, then you don't want other objects to collide with that obejct, since it's dead yet still exists.
	 * 
	 * @param state
	 * If you would like the game object to be collidable.
	 *
	 */
	
	public WorldObject getWorld() {
		return this.world;
		
	}
	
	public void setCollidable(boolean state) {
		this.collidable = state;
	}
	
	public Coordinator2D getMovementCoordinator() {
		return this.movementCoordinator;
		
	}
	
	public abstract void checkCollisionDetection();
	
	/**
	 * <b><i>public abstract void update()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Override this method with all the content that will update this game object.
	 *
	 */
	
	public void update() {
		this.updateGameObjectPosition();
		
	}

	public void updateGameObjectPosition() {
		this.movementCoordinator.update();
		this.getShape().setLocation(this.movementCoordinator.getX(), this.movementCoordinator.getY());
		
	}
	
	@Override
	public void setLocation(int newX, int newY) {
		super.setLocation(newX, newY);
		this.movementCoordinator.setLocation(newX, newY);
		
	}
	
	/**
	 * <b><i>public boolean checkFastCollisionDetection(GameObjectTemplate[] objects, int velocityOfFastObject)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Check if this game object has collided with any of the provided objects. Use this for fast game objects such as lasers or so.
	 * 
	 * @param objects
	 * All the objects you would like to check if this game object has collided with.
	 * 
	 * @param velocityOfFastObject
	 * The velocity of the fast object.
	 * 
	 * @return 
	 * <br><br>
	 * 1. True = This game object has collided with one of the provided game objects.
	 * <br><br>
	 * 2. False = No collision has taken place.
	 *
	 */
	
	public Integer checkCollisionDetection(GameObject[] objects) {
		Integer indexOfCollidedBody = null;
		
		// Updates regarding interactions with the other objects
		for(int i = 0; i < objects.length; i++) {
			if(objects[i] != null && objects[i].collidable == true && this.collidable == true) {
				if(Physics.checkCollisionDetection(this, objects[i])) {
					indexOfCollidedBody = i;
					
				}
				
			}
		
		}
		
		return indexOfCollidedBody;
	}
	
	
}
