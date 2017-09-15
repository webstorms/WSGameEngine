package com.webstorms.framework.graphics.animation;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;
import com.webstorms.physics.Circle;
import com.webstorms.physics.Shape;

public class GraphicObject extends WSObject {
	
	private Bitmap bitmap;
	private Shape shape;
	private Paint shapeColor;
	
	public GraphicObject(Game game) {
		super(game);
		this.shape = new Shape();
		this.shapeColor = new Paint();
	//	this.defineShape(shape);
		
	}
	
	public Bitmap getBitmap() {
		return this.bitmap;
		
	}
	
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		
	}
	
	/**
	 * <b><i>public void defineGameObjectPositionShape()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Override this method with statements that will define your widget objects shape.
	 *
	 */
	
	// This method should always be overridden with nice sweet content 
	// public void defineShape(Shape shape) {
		
	// }
	
	/**
	 * <b><i>public int getX()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the x (left) location of the game object.
	 * <br>
	 * The top left location is (0,0).
	 * 
	 * @return 
	 * The x location of the game object.
	 *
	 */
	
	public int getX() {
		return this.shape.getX();
	}
	
	/**
	 * <b><i>public int getY()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the y (top) location of the game object.
	 * <br>
	 * The top left location is (0,0).
	 * 
	 * @return 
	 * The y location of the game object.
	 *
	 */
	
	public int getY() {
		return this.shape.getY();
	}
	
	
	/**
	 * <b><i>public void setX(int newX)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will set the x location of the game object.
	 * <br>
	 * The top left location is (0,0).
	 * 
	 * @param newX 
	 * The new x location.	
	 */
	
	public void setX(int newX) {
		this.setLocation(newX, this.getY());
	}
	
	/**
	 * <b><i>public void setY(int newY)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will set the y location of the game object.
	 * <br>
	 * The top left location is (0,0).
	 * 
	 * @param newY 
	 * The new y location.	
	 */
	
	public void setY(int newY) {
		this.setLocation(this.getX(), newY);
	}
	
	/**
	 * <b><i>public void setLocation(int newX, int newY)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will set the y location and x location of the game object.
	 * <br>
	 * The top left location is (0,0).
	 * 
	 * @param newX 
	 * The new x location.
	 * 
	 * @param newY 
	 * The new y location.	
	 * 	
	 */
	
	public void setLocation(int newX, int newY) {
		this.shape.setLocation(newX, newY);
		
	}
	
	/**
	 * <b><i>public Shape getGameObjectPositionShape()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the shape where the game object fits into.
	 * 
	 * @return 
	 * The shape where the game object fits into.
	 *
	 */
	
	public Shape getShape() {
		return this.shape;
	}
	
	/**
	 * <b><i>protected void drawShapes()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * For development purposes: This method will render the shape. All the rectangles that comprise the shape.
	 *
	 */
	
	protected void renderShapes(int color) {
		
		int numberOfComponents;
		
		if(this.shapeColor.getColor() != color) {
			this.shapeColor.setColor(color);
			
		}
		
		if(this.shape.getHolderPolygons().size() == 0) {
			numberOfComponents = 0;
			
		}
		else if(this.shape.getHolderPolygons().size() == 1) {
			numberOfComponents = 0;
			
		}
		else {
			numberOfComponents = 1;
			
		}
		for(int numberOfIterations = numberOfComponents; numberOfIterations < this.shape.getHolderPolygons().size(); numberOfIterations++) {
			if(this.shape.getHolderPolygons().get(numberOfIterations) instanceof Rect) {
				this.getGame().getGraphics().drawRect((Rect) this.shape.getHolderPolygons().get(numberOfIterations), this.shapeColor);
				
			}
			else if(this.shape.getHolderPolygons().get(numberOfIterations) instanceof Circle) {
				this.getGame().getGraphics().drawCircle(((Circle) this.shape.getHolderPolygons().get(numberOfIterations)).getCenterX(), ((Circle) this.shape.getHolderPolygons().get(numberOfIterations)).getCenterY(), ((Circle) this.shape.getHolderPolygons().get(numberOfIterations)).getRadius(), this.shapeColor);
				
			}
			
		}
		
	}
	
	/**
	 * <b><i>public abstract void update()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Override this method with all the content that will update this widget object.
	 *
	 */
	
	public void update() {
		
	}
	
	/**
	 * <b><i>public abstract void render()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Override this method with all the content that will render this widget object.
	 *
	 */
	
	public void render() {
		this.renderBitmap();
		
	}
	
	public void renderBitmap() {
		if(this.bitmap != null) {
			this.getGame().getGraphics().drawBitmap(this.bitmap, null, this.shape.getRect(), null);
			
		}
		
	}
	
	public void onDispose() {
		
	}
	
	
}
