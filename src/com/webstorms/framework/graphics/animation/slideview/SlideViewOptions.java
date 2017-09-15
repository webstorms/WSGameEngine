package com.webstorms.framework.graphics.animation.slideview;

import java.util.List;

public class SlideViewOptions {

	private int distanceOfGapBetweenBoards = 10;
	private List<Board> suppliedBoards;
	private int restPosition = distanceOfGapBetweenBoards;
	private int xSlideVelocityUnActive = 10;
	private int startPosition = 1;
	
	// Setter methods
	
	public void setDistanceOfGapBetweenBoards(int distanceOfGapBetweenBoards) {
		this.distanceOfGapBetweenBoards = distanceOfGapBetweenBoards;
	}
	
	public void setSuppliedBoards(List<Board> suppliedBoards) {
		this.suppliedBoards = suppliedBoards;
		if(this.suppliedBoards == null || this.suppliedBoards.size() == 0) {
			this.startPosition = 0;
			
		}
		
	}
	
	public void setRestPosition(int restPosition) {
		this.restPosition = restPosition;
	}
	
	public void setxSlideVelocityUnActive(int xSlideVelocityUnActive) {
		this.xSlideVelocityUnActive = xSlideVelocityUnActive;
	}
	
	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}
	
	// Getter methods
	
	public int getDistanceOfGapBetweenBoards() {
		return distanceOfGapBetweenBoards;
	}

	public List<Board> getSuppliedBoards() {
		return suppliedBoards;
	}

	public int getRestPosition() {
		return restPosition;
	}

	public int getxSlideVelocityUnActive() {
		return xSlideVelocityUnActive;
	}

	public int getStartPosition() {
		return startPosition;
	}
	
	
}
