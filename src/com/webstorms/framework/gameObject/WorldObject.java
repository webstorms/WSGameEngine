package com.webstorms.framework.gameObject;

import java.util.ArrayList;
import java.util.List;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public abstract class WorldObject extends WSObject {

	public static final boolean STATE_RESUMED = false;
	public static final boolean STATE_PAUSED = true;
	
	private boolean state;
	List<GameObject> gameObjectsHolder = new ArrayList<GameObject>();
	
	/**
	 * Extend this class when creating a world that will control the whole game.
	 * 
	 */
	
	protected WorldObject(Game game) {
		super(game);
		this.resume();
		
	}
	
	public boolean getState() {
		return this.state;
		
	}
	
	public List<GameObject> getGameObjectsHolder() {
		return this.gameObjectsHolder;
		
	}
	
	public void resume() {
		this.state = WorldObject.STATE_RESUMED;
		new CollisionDetection();
		
	}
	
	public void pause() {
		this.state = WorldObject.STATE_PAUSED;
		
	}
	
	public abstract void update();
	
	public abstract void render();
	
	private void updateCollisionDetection() {
		
		List<GameObject> copyOfGameObjects = new ArrayList<GameObject>(this.gameObjectsHolder);
		
		for(int i = 0; i < copyOfGameObjects.size(); i++) {
			copyOfGameObjects.get(i).checkCollisionDetection();
			
		}
		
	}
	
	public void attachGameObject(GameObject gameObject) {
		gameObjectsHolder.add(gameObject);
		
	}
	
	public void detachGameObject(GameObject gameObject) {
		gameObject.onDispose();
		gameObjectsHolder.remove(gameObject);
		
	}
	
	public class CollisionDetection implements Runnable {

		public CollisionDetection() {
			Thread collisionDetection = new Thread(this);
			collisionDetection.start();
	        
		}
		
		@Override
		public void run() {
			while(state != WorldObject.STATE_PAUSED) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				updateCollisionDetection();
				
			}
			
		}
		
		
	}
	
	
}
