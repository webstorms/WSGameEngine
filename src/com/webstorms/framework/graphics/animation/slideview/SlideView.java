package com.webstorms.framework.graphics.animation.slideview;

import android.graphics.Rect;

import com.webstorms.framework.Game;
import com.webstorms.framework.graphics.animation.GraphicObject;
import com.webstorms.physics.Shape;

public class SlideView extends GraphicObject {
	
	/* Last edit: 22 July 2012
	 * 
	 * Improvements: 
	 * - Create a fixed amount of visible boards, to reduce updating and rendeing time, yielding better FPS
	 * - Add setGravity(int gravity) // in % (allow the boards to keep on moving for a little bit after having been dragged)
	 * - Don't keep on checking closest board to rest position in the updateUnactive() method
	 * - public void moveBoardToPosition(int pos); and public void updateUnactive() have very similiar code, maybe compromise! 
	 * 
	 * Bugs:
	 * - None (that I know of) :D
	 */
	
	public static final int TYPE_ACTIVE = 0;
	public static final int TYPE_UNACTIVE = 1;
	
	private Integer xStartingPosition;
	private int xSlideVelocity;
	private Integer indexOfBoardToMoveTo;
	
	SlideViewOptions options;
	
	private Integer slideViewFirstBoardPosition;
	private Integer slideViewLastBoardPosition;
	
	private boolean moveBoardToPositionSet;
	private boolean indexOfBoardToMoveToSet;
	
	public SlideView(Game game, SlideViewOptions options, Rect dimensions) {
		super(game);
		this.options = options;
		this.initializeSlideView();
		this.getShape().addRectangle(new Rect(dimensions));
		
	} 
	
	//@Override
	//p/ublic void defineShape(Shape shape) {
		// No need to add content to shape, this is done on line 42 this.getShape().addRectangle(new Rect(dimensions));
	//	
	//}
	
	private void initializeSlideView() {
		if(this.options.getSuppliedBoards().size() != 0) {
			this.slideViewFirstBoardPosition = 1;
			slideViewLastBoardPosition = this.options.getSuppliedBoards().size();
			int boardLength = this.options.getSuppliedBoards().get(0).getShape().getWidth();
			
			// Set up positions of the boards
			for(int amountOfBoards = 0; amountOfBoards < this.options.getSuppliedBoards().size(); amountOfBoards++) {
				if(amountOfBoards == 0) {
					this.options.getSuppliedBoards().get(amountOfBoards).setX(this.options.getRestPosition()); // Use the restPosition as a reffernce
				
				}
				else {
					this.options.getSuppliedBoards().get(amountOfBoards).setX(this.options.getSuppliedBoards().get(amountOfBoards - 1).getX() + boardLength + this.options.getDistanceOfGapBetweenBoards());
					
				}
				
			}
			
			// Move all boards to the desired start position
			int distanceToMove = this.options.getSuppliedBoards().get(0).getX() - this.options.getSuppliedBoards().get(this.options.getStartPosition() - 1).getX(); // -1 To convert from position to index
			this.updateSlideObjects(distanceToMove);
	 		
			// Set the index of the board closest to the rest position, to prevent jerking, because the index is defaulty 0
			this.indexOfBoardToMoveTo = this.options.getStartPosition() - 1; // convert index to position, hence -1
			
		}
		else {
			this.slideViewFirstBoardPosition = null;
			this.slideViewLastBoardPosition = null;
			this.indexOfBoardToMoveTo = null;
			
		}
		
	}
	
	// Setter methods
	
	public void setSlideViewInfo(SlideViewOptions options) {
		
		int referencePoint = this.options.getSuppliedBoards().get(this.slideViewFirstBoardPosition - 1).getX();
		
		this.options = options;
		
		if(this.options.getSuppliedBoards().size() != 0) {
		//	this.slideViewFirstBoardPosition = 1;
			slideViewLastBoardPosition = this.options.getSuppliedBoards().size();
			int boardLength = this.options.getSuppliedBoards().get(0).getShape().getWidth();
			
			// Set up positions of the boards
			for(int amountOfBoards = 0; amountOfBoards < this.options.getSuppliedBoards().size(); amountOfBoards++) {
				if(amountOfBoards == 0) {
					this.options.getSuppliedBoards().get(amountOfBoards).setX(referencePoint); // Use the restPosition as a reffernce
				
				}
				else {
					this.options.getSuppliedBoards().get(amountOfBoards).setX(this.options.getSuppliedBoards().get(amountOfBoards - 1).getX() + boardLength + this.options.getDistanceOfGapBetweenBoards());
					
				}
				
			}
			
			// Move all boards to the desired start position
		//	int distanceToMove = this.options.getSuppliedBoards().get(0).getX() - this.options.getSuppliedBoards().get(this.options.getStartPosition() - 1).getX(); // -1 To convert from position to index
		//	this.updateSlideObjects(distanceToMove);
	 		
			// Set the index of the board closest to the rest position, to prevent jerking, because the index is defaulty 0
		//	this.indexOfBoardToMoveTo = this.options.getStartPosition() - 1; // convert index to position, hence -1
			
		}
		else {
			this.slideViewFirstBoardPosition = null;
			this.slideViewLastBoardPosition = null;
			this.indexOfBoardToMoveTo = null;
			
		}
		
	}
	
	// Getter methods
	
	public SlideViewOptions getSlideViewInfo() {
		return this.options;
		
	}
	
	public boolean isRestBoardFirst() {
		if(this.getPositionOfBoardClosestToRestPosition() == this.slideViewFirstBoardPosition) {
			return true;
			
		}
		else {
			return false;
			
		}
		
	}
	
	public boolean isRestBoardLast() {
		if(this.getPositionOfBoardClosestToRestPosition() == this.slideViewLastBoardPosition) {
			return true;
			
		}
		else {
			return false;
			
		}
		
	}
	
	public Integer getIndexOfBoardClosestToRestPosition() {
		return this.indexOfBoardToMoveTo;
		
	}
	
	public Integer getPositionOfBoardClosestToRestPosition() {
		if(this.indexOfBoardToMoveTo != null) {
			return this.indexOfBoardToMoveTo + 1;
			
		}
		else {
			return null;
					
		}
		
	}
	
	// Functional methods
	
	@Override
	public void render() {
		super.render();
		for(int amountOfBoards = 0; amountOfBoards < this.options.getSuppliedBoards().size(); amountOfBoards++) {
			this.options.getSuppliedBoards().get(amountOfBoards).render();
			
		}
		
	}
	
	public void update(int type, Integer xLocationOfTouch) {
		if(type == SlideView.TYPE_ACTIVE) {
			this.updateActive(xLocationOfTouch);
			
		}
		else if(type == SlideView.TYPE_UNACTIVE) {
				this.updateUnactive();

		}
		
	}

	public void updateActive(int touchX) {
		// Move all boards according to finger-movement
		if(xStartingPosition == null) {
			xStartingPosition = touchX;
		}
		if(this.indexOfBoardToMoveToSet == true) {
			this.indexOfBoardToMoveToSet = false;
			
		}
		if(this.moveBoardToPositionSet == true) {
			this.moveBoardToPositionSet = false;
			
		}
		
		this.xSlideVelocity = touchX - this.xStartingPosition;
		this.updateSlideObjects(this.xSlideVelocity);
		this.xStartingPosition = touchX;
		
		// If newIndex is not null, this means that the moving was interuppted with the physical interaction
		if(this.newIndex != null) {
			this.newIndex = null;
		}
		
	}
	
	Integer newIndex = null;
	
	public void moveBoardToPosition(int pos) {
		if(pos > 0 && pos <= this.options.getSuppliedBoards().size()) {
			//	this.getGameEngineLog().d(classTAG, "Setting new index!");
			this.moveBoardToPositionSet = true;
			this.indexOfBoardToMoveTo = pos - 1; // Convert pos to index
			
		}
		
	}
	
	public void updateUnactive() {
		if(this.xStartingPosition != null) {
			this.xStartingPosition = null;
			
		}
		if(this.options.getSuppliedBoards().size() != 0) {
			if( this.indexOfBoardToMoveToSet == false && this.moveBoardToPositionSet == false) {
				if(this.indexOfBoardToMoveToSet == false) {
					this.indexOfBoardToMoveToSet = true;
					
				}
				if(this.moveBoardToPositionSet == false) {
					this.moveBoardToPositionSet = true;
					
				}
				this.getGame().getGameLog().d(this, "Finding index closest to the rest position");
				// Check for board closest to the rest position
				this.indexOfBoardToMoveTo = 0; // First Board object
				int shortestDistanceToRest = this.options.getRestPosition() - this.options.getSuppliedBoards().get(0).getX(); // First Board object
				for(int i = 0; i < this.options.getSuppliedBoards().size(); i++) {
					// Set the refference for the rest board
					if(this.options.getRestPosition() > this.options.getSuppliedBoards().get(i).getX()) {
						if(this.options.getRestPosition() - this.options.getSuppliedBoards().get(i).getX() < shortestDistanceToRest) {
							this.indexOfBoardToMoveTo = i;
							shortestDistanceToRest = this.options.getRestPosition() - this.options.getSuppliedBoards().get(i).getX();

						}
					}
					else {
						if(this.options.getSuppliedBoards().get(i).getX() - this.options.getRestPosition() < shortestDistanceToRest) {
							this.indexOfBoardToMoveTo = i;
							shortestDistanceToRest = this.options.getSuppliedBoards().get(i).getX() - this.options.getRestPosition();
						}

					}

				}

			}
			
			// Move all Boards back into position
			if(this.options.getRestPosition() > this.options.getSuppliedBoards().get(this.indexOfBoardToMoveTo).getX()) {
				// Move right
				// Fine movement
				if(this.options.getRestPosition() - this.options.getSuppliedBoards().get(this.indexOfBoardToMoveTo).getX() < this.options.getxSlideVelocityUnActive()) {
					this.xSlideVelocity = this.options.getRestPosition() - this.options.getSuppliedBoards().get(this.indexOfBoardToMoveTo).getX();
					this.updateSlideObjects(this.xSlideVelocity); // Has to be +

				}
				// Coarse movement
				else {
					this.xSlideVelocity = this.options.getxSlideVelocityUnActive();
					this.updateSlideObjects(this.xSlideVelocity);
					
				}

			}
			else {
				// Move left
				if(this.options.getSuppliedBoards().get(this.indexOfBoardToMoveTo).getX() - this.options.getRestPosition() < this.options.getxSlideVelocityUnActive()) {
					this.xSlideVelocity = this.options.getRestPosition() - this.options.getSuppliedBoards().get(this.indexOfBoardToMoveTo).getX(); // Has to be -
					this.updateSlideObjects(this.xSlideVelocity);
					
				}
				else {
					this.xSlideVelocity = -this.options.getxSlideVelocityUnActive();
					this.updateSlideObjects(this.xSlideVelocity);
					
				}

			}

		}

	}
	
	public void updateSlideObjects(int velocity) { 
		for(int i = 0; i < this.options.getSuppliedBoards().size(); i++) {
			this.updateSingleSlideObject(i, velocity);
			
		}
		
	}
	
	public void updateSingleSlideObject(int index, int velocity) {
		this.options.getSuppliedBoards().get(index).update(velocity);
		
	}
	
	
}