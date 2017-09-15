package com.webstorms.physics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Rect;

public final class Shape {
	
	// Note: First element in holder must be rect (the viewing rect!)!
	
	// Need to do: Go from Vector<> to List<>
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	// Currently can only hold rectangles
	private List<Object> holderPolygons = new ArrayList<Object>();;
	
	/**
	 * Comprise a shape out of rectangles for your game object.
	 * 
	 */
	
	public Shape() {
		
	}
	
	public Shape(int x, int y, Rect viewingRect) {
		this.addRectangle(viewingRect);
		this.setLocation(x, y);
		
	}

	public Rect getRect() {
		if(this.holderPolygons.size() != 0) {
			return (Rect) this.holderPolygons.get(0);
			
		}
		return null;
		
	}
	
	public int getX() {
		return this.x;
		
	}
	
	public int getY() {
		return this.y;
		
	}
	
	/**
	 * <b><i>public void addRectangle(Rect rect)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Invoke this method on the object to add a rectangle to its shape.
	 * 
	 * @param rect
	 * The rectangle you would like to add to structure the shape.
	 *
	 */
	
	public void addRectangle(Rect rect) {
		// Add rectangle to the end of the vector which will form the shape of the object
		holderPolygons.add(rect);
		
		if(holderPolygons.size() == 1) {
			this.width = ((Rect) holderPolygons.get(0)).right;
			this.height = ((Rect) holderPolygons.get(0)).bottom;
			
		}
		
	}
	
	public void addCircle(Circle circle) {
		holderPolygons.add(circle);
		
	}
	
	/**
	 * <b><i>public void overrideRectangle(int index, Rect rect)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Invoke this method on the object to add a rectangle to its shape and override it at the same time.
	 * 
	 * @param index
	 * The index of the rect you wish to override.
	 * 
	 * @param rect
	 * The rectangle you would like to add to structure the shape.
	 *
	 */
	
	public void overrideRectangle(int index, Rect rect) {
		this.holderPolygons.remove(index);
		this.holderPolygons.add(index, rect);
		
	}
	
	/**
	 * <b><i>public Vector<Rect> getHolderPolygons()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the rectangles that comprise the shape.
	 * 
	 * @return 
	 * The rectangles that comprise the shape.
	 *
	 */
	
	public List<Object> getHolderPolygons() {
		return holderPolygons;
		
	}
	
	/**
	 * <b><i>public void setLocation(int newX, int newY)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will set the y location and x location of the shape.
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
		if(this.holderPolygons.size() != 0) {
			// Get an instance of the rectangle that covers the whole object
			Rect rect = (Rect) this.getHolderPolygons().get(0);
			// Check the Y change
			int changeY = newY - rect.top;
			// Check the X change
			int changeX = newX - rect.left;
			
			// Stick those values into the method that will magically move the Shape
			this.moveShape(changeX, changeY);
			
		}
		
		// Set x and y
		this.x = newX;
		this.y = newY;
		
	}
	
	/**
	 * <b><i>public int getWidth()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the width of the shape.
	 * 
	 * @return 
	 * The width of the shape.
	 *
	 */
	
	public int getWidth() {
		return this.width;
	}
	
	/**
	 * <b><i>public int getHeight()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the height of the shape.
	 * 
	 * @return 
	 * The height of the shape.
	 *
	 */
	
	public int getHeight() {
		return this.height;
	}
	
	/**
	 * <b><i>public void moveShape(int x, int y)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Move the shape a certain amount.
	 * 
	 * @param x
	 * <br>
	 * x amount to the right = x > 0
	 * <br>
	 * x amount to the left = x < 0
	 *
	 * @param y
	 * <br>
	 * y amount to the bottom = y > 0
	 * <br>
	 * y amount to the top = y < 0
	 *
	 */
	
	public void moveShape(int x, int y) {
		for(int numberOfShapeComponents = 0; numberOfShapeComponents < this.getHolderPolygons().size(); numberOfShapeComponents++) {
			
			if(this.getHolderPolygons().get(numberOfShapeComponents) instanceof Rect) {
				Rect rectangle = (Rect) this.getHolderPolygons().get(numberOfShapeComponents);
				rectangle.set(rectangle.left + x, rectangle.top + y, rectangle.right + x, rectangle.bottom + y);
				
			}
			
			else if(this.getHolderPolygons().get(numberOfShapeComponents) instanceof Circle) {
				Circle circle = (Circle) this.getHolderPolygons().get(numberOfShapeComponents);
				circle.setCenterX(circle.getCenterX() + x);
				circle.setCenterY(circle.getCenterY() + y);
				
			}
			
			
			// Lets override the shape with a new shape that contains the new coordinates
			//this.getHolderPolygons().add(numberOfShapeComponents, rectangle);
		//	this.getHolderPolygons().remove(numberOfShapeComponents);
		//	this.getHolderPolygons().insertElementAt(rectangle, numberOfShapeComponents);
			
		}
		
		this.x = this.x + x;
		this.y = this.y + y;
		
	}
	
	/**
	 * <b><i>public static boolean pointInShape(int x, int y, Shape shape)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Check if the given point is in the given shape.
	 * 
	 * @param x
	 * The x location of the point.
	 * 
	 * @param y
	 * The y location of the point.
	 * 
	 * @param shape
	 * The shape you wish to check if a point is in.
	 * 
	 * @return 
	 * If the given point is in the given shape.
	 *
	 */
	
	public boolean contains(int pX, int pY) {
		boolean pointInShape = false;
		int numberShape1StartingPoint;
		if(this.getHolderPolygons().size() == 0) {
			numberShape1StartingPoint = 0;
		}
		else if(this.getHolderPolygons().size() == 1) {
			numberShape1StartingPoint = 0;
		}
		else {
			numberShape1StartingPoint = 1;
		}
		for(int numberShape1 = numberShape1StartingPoint; numberShape1 < this.getHolderPolygons().size(); numberShape1++) {
			
			// Check what kind of shape shape.getHolderPolygons().get(numberShape1) is (Rect or Circle)
			
			if(this.getHolderPolygons().get(numberShape1) instanceof Rect) {
				if(((Rect) this.getHolderPolygons().get(numberShape1)).contains(pX, pY) == true) {
					pointInShape = true;
					
				}
				
			}
			else if(this.getHolderPolygons().get(numberShape1) instanceof Circle) {
				if(((Circle) this.getHolderPolygons().get(numberShape1)).contains(pX, pY) == true) {
					pointInShape = true;
					
				}
				
			}
			
		}
		
		return pointInShape;
	}
	
	public boolean contains(Shape shape) {
		return Shape.intersects(this, shape);
		
	}
	
	/**
	 * <b><i>public static boolean shapeInShape(Shape shape1, Shape shape2)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Check if the given shape is in the other given shape.
	 * 
	 * @param shape1
	 * The first shape.
	 * 
	 * @param shape2
	 * The second shape.
	 * 
	 * @return 
	 * If the given rectangle is in the other rectangle.
	 *
	 */
	
	public static boolean intersects(Shape shape1, Shape shape2) {
		boolean shapeInShape = false;
		int numberShape1StartingPoint;
		int numberShape2StartingPoint;
		if(shape1.getHolderPolygons().size() == 0) {
			// Y U No add any rectangles?!
			// Add error log
			numberShape1StartingPoint = 0;
		}
		else if(shape1.getHolderPolygons().size() == 1) {
			// Because we only have the view frame, we can only use this
			numberShape1StartingPoint = 0;
		}
		else {
			numberShape1StartingPoint = 1;
		}
		if(shape2.getHolderPolygons().size() == 0) {
			// Y U No add any rectangles?!
			// Add error log
			numberShape2StartingPoint = 0;
		}
		else if(shape2.getHolderPolygons().size() == 1) {
			// Because we only have the view frame, we can only use this
			numberShape2StartingPoint = 0;
		}
		else {
			numberShape2StartingPoint = 1;
		}
		for(int numberShape1 = numberShape1StartingPoint; numberShape1 < shape1.getHolderPolygons().size(); numberShape1++) {
			
			for(int numberShape2 = numberShape2StartingPoint; numberShape2 < shape2.getHolderPolygons().size(); numberShape2++) {
				
				// Check what kind of shape shape.getHolderPolygons().get(numberShape1) is (Rect or Circle)
				
				if(shape1.getHolderPolygons().get(numberShape1) instanceof Rect && shape2.getHolderPolygons().get(numberShape2) instanceof Rect) {
					
					if(Rect.intersects((Rect) shape1.getHolderPolygons().get(numberShape1), (Rect) shape2.getHolderPolygons().get(numberShape2))) {
						shapeInShape = true;
						
					}
					
				}
				
				else if(shape1.getHolderPolygons().get(numberShape1) instanceof Circle && shape2.getHolderPolygons().get(numberShape2) instanceof Rect) {
					
					if(Circle.intersects((Rect) shape2.getHolderPolygons().get(numberShape2), (Circle) shape1.getHolderPolygons().get(numberShape1))) {
						shapeInShape = true;
						
					}
					
				}
				
				else if(shape1.getHolderPolygons().get(numberShape1) instanceof Rect && shape2.getHolderPolygons().get(numberShape2) instanceof Circle) {
					
					if(Circle.intersects((Rect) shape1.getHolderPolygons().get(numberShape1), (Circle) shape2.getHolderPolygons().get(numberShape2))) {
						shapeInShape = true;
						
					}
					
				}
				
				else if(shape1.getHolderPolygons().get(numberShape1) instanceof Circle && shape2.getHolderPolygons().get(numberShape2) instanceof Circle) {
					
					if(Circle.intersects((Circle) shape1.getHolderPolygons().get(numberShape1), (Circle) shape2.getHolderPolygons().get(numberShape2))) {
						shapeInShape = true;
						
					}
					
				}
				
			}
			
		}
		
		return shapeInShape;
	}
	
	
}
