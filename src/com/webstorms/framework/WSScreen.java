package com.webstorms.framework;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;

public class WSScreen extends WSObject {

	/* Last edit: ?? ? 2012
	 * 
	 * Improvements: 
	 * 
	 * Bugs:
	 * - scaleProportionallyScreen not working properly, when setting y and/or x gap (letterbox)
	 */
	
	/** Will stretch the image formed for your virtual screen to stretch and fit onto the real screen.*/
	public static final int stretchScreen = 0;
	
	/** Will scale the image formed for your virtual screen to fit onto the real screen leaving the same sized letteboxes on the side that is to short.*/
	public static final int scaleProportionallyScreen = 1; // Also knows as letterboxing
	
	/** Will scale the image formed for your virtual screen to fill and fit onto the real screen.*/
	public static final int fillProportionallyScreen = 2;
	
	private int realWidth;
	private int realHeight;
	private int gameScreenWidth;
	private int gameScreenHeight;
    private float scaleXFromVirtualToReal; // Screen image static constants
    private float scaleYFromVirtualToreal; // Screen image static constants
    Bitmap gameScreen;
    
    private float scaleXFromRealToVirtual; // Input dynamic, can change for different screens
    private float scaleYFromRealToVirtual; // Input, can change for different screens
    private int leftLetterboxLength, inverseRightLetterboxLength, topLetterboxLength, inverseBottumLetterboxLength;
    private float customLeftGapXScale, customRightGapXScale, customTopGapYScale, customButtomGapYScale = 0f; // All measured in decimal percent eg. 0.8
    private float gapX, gapY; 
	Rect gameScreendst;
    
	/**
	 * This class handles all the screen resizing and scaling. It will scale the virtual framebuffer and get the appropiate dimensions to print
	 * it onto the real screen. There are different types of scaling modes that one can choose from.
	 * 
	 */
	
	public WSScreen(Game game, int screenResizeType, int customTopYGap, int customLeftXGap, int gameScreenWidth, int gameScreenHeight) {
		super(game);
		
		// Real screen width and realHeight
		realWidth = this.getGame().getWindowManager().getDefaultDisplay().getWidth();
    	realHeight = this.getGame().getWindowManager().getDefaultDisplay().getHeight();
    	WSLog.e(Game.GAME_ENGINE_TAG, this, "WSScreen Real Width: " + realWidth);
    	WSLog.e(Game.GAME_ENGINE_TAG, this, "WSScreen Real Width: " + realHeight);
    	// Virtual game screen width and realHeight
    	this.gameScreenWidth = gameScreenWidth;
    	this.gameScreenHeight = gameScreenHeight;
    	
    	// Set up virtual to real ratios
    	scaleXFromVirtualToReal = (float) realWidth/this.gameScreenWidth;
        scaleYFromVirtualToreal = (float) realHeight/this.gameScreenHeight;
        
        gameScreen = Bitmap.createBitmap(getGameScreenWidth(), getGameScreenHeight(), Config.RGB_565);
    	
    	// Default settings for custom screen
    	this.setCustomTopYGap(customTopYGap);
    	this.setCustomLeftXGap(customLeftXGap);
    	
    	// Do all the scaling that will set the rest of the variables with the provided screen resize type
    	this.setScreenResizeTpe(screenResizeType);
		
	}
    
	// Getter methods

    /**
	 * <b><i>public Bitmap getGameScreen()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen instance.
	 * 
	 * @return 
	 * The Game  Screen Bitmap.
	 *
	 */
    
	public Bitmap getGameScreen() {
		return gameScreen;
	}
	
	/**
	 * <b><i>public Rect getGameScreendst()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Rectangle where the Game Screen should fit into.
	 * 
	 * @return 
	 * The Rectangle the Game Screen should fit into.
	 *
	 */
	
	public Rect getGameScreendst() {
		return gameScreendst;
	}
	
	/**
	 * <b><i>public float getScaleXFromRealToVirtual()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the scale on the x axis from real to virtual.
	 * 
	 * @return 
	 * The scale on the x axis from real to virtual.
	 *
	 */
	
	public float getScaleXFromRealToVirtual() {
		return scaleXFromRealToVirtual;
	}
	
	/**
	 * <b><i>public float getScaleYFromRealToVirtual()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the scale on the y axis from real to virtual.
	 * 
	 * @return 
	 * The scale on the y axis from real to virtual.
	 *
	 */
	
	public float getScaleYFromRealToVirtual() {
		return scaleYFromRealToVirtual;
	}
	
	/**
	 * <b><i>public int getLeftLetterboxLength()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the left letterbox length.
	 * <br>
	 * NOTE: When using <i>public static final int stretchScreen</i> or <i>public static final int fillProportionallyScreen</i>,
	 * the letterbox will be 0.
	 * 
	 * @return 
	 * The left letterbox's length.
	 *
	 */
	
	public int getLeftLetterboxLength() {
		return leftLetterboxLength;
	}
	
	/**
	 * <b><i>public int getInverseRightLetterboxLength()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the right inverse letterbox length.
	 * <br>
	 * NOTE: When using <i>public static final int stretchScreen</i> or <i>public static final int fillProportionallyScreen</i>,
	 * the letterbox will be 0.
	 * 
	 * @return 
	 * The right inverse letterbox's length.
	 *
	 */
	
	public int getInverseRightLetterboxLength() {
		return inverseRightLetterboxLength;
	}
	
	/**
	 * <b><i>public int getTopLetterboxLength()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the top letterbox length.
	 * <br>
	 * NOTE: When using <i>public static final int stretchScreen</i> or <i>public static final int fillProportionallyScreen</i>,
	 * the letterbox will be 0.
	 * 
	 * @return 
	 * The top letterbox's length.
	 *
	 */
	
	public int getTopLetterboxLength() {
		return topLetterboxLength;
	}
	
	/**
	 * <b><i>public int getInverseBottumLetterboxLength()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the left inverse letterbox length.
	 * <br>
	 * NOTE: When using <i>public static final int stretchScreen</i> or <i>public static final int fillProportionallyScreen</i>,
	 * the letterbox will be 0.
	 * 
	 * @return 
	 * The left inverse letterbox's length.
	 *
	 */
	
	public int getInverseBottumLetterboxLength() {
		return inverseBottumLetterboxLength;
	}
	
	// Developer methods not acquired by Game Engine to work 
	
	public int getRealWidth() {
		return this.realWidth;
		
	}
	
	public int getRealHeight() {
		return this.realHeight;
		
	}
	
	/**
	 * <b><i>public int getGameScreenWidth()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen width.
	 * 
	 * @return 
	 * The Game Screen width.
	 *
	 */
	
	public int getGameScreenWidth() {
		return gameScreenWidth;
	}
	
	/**
	 * <b><i>public int getGameScreenHeight()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen realHeight.
	 * 
	 * @return 
	 * The Game Screen realHeight.
	 *
	 */
	
	public int getGameScreenHeight() {
		return gameScreenHeight;
	}
	
	/**
	 * <b><i>public int getGameScreenExtendedWidth()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen Extended Width.
	 * 
	 * @return 
	 * The Game Screen Extended Width.
	 *
	 */
	
	public int getGameScreenExtendedWidth() {
		return (int) (realWidth * scaleXFromRealToVirtual);
	}
	
	/**
	 * <b><i>public int getGameScreenExtendedHeight()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen Extended Height.
	 * 
	 * @return 
	 * The Game Screen Extended Height.
	 *
	 */
	
	public int getGameScreenExtendedHeight() {
		return (int) (realHeight*scaleYFromRealToVirtual);
	}
	
	/**
	 * <b><i>public int getCustomTopYGap()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Custom Top Y Gap.
	 * 
	 * @return 
	 * The Custom Top Y Gap.
	 *
	 */
	
	public int getCustomTopYGap() {
		return (int)(gapY*customTopGapYScale);
	}
	
	/**
	 * <b><i>public int getCustomBottumYGap()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Custom Bottom Y Gap.
	 * 
	 * @return 
	 * The Custom Bottom Y Gap.
	 *
	 */
	
	public int getCustomBottumYGap() {
		return (int)(gapY*customButtomGapYScale);
	}
	
	/**
	 * <b><i>public int getCustomLeftXGap()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Custom Left X Gap.
	 * 
	 * @return 
	 * The Custom Left X Gap.
	 *
	 */
	
	public int getCustomLeftXGap() {
		return (int)(gapX*customLeftGapXScale);
	}
	
	/**
	 * <b><i>public int getCustomRightXGap()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Custom Right X Gap.
	 * 
	 * @return 
	 * The Custom Right X Gap.
	 *
	 */
	
	public int getCustomRightXGap() {
		return (int)(gapX*customRightGapXScale);
	}
	
	/**
	 * <b><i>public Rect getGameScreenDimensions()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen Dimensions.
	 * 
	 * @return 
	 * The Game Screen Dimensions.
	 *
	 */
	
	public Rect getGameScreenDimensions() {
		return new Rect(0,0,getGameScreenWidth(),getGameScreenHeight());
	}
	
	/**
	 * <b><i>public Rect getGameScreenExtendedDimensions()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Screen Extended Dimensions.
	 * 
	 * @return 
	 * The Game Screen Extended Dimensions.
	 *
	 */
	
	public Rect getGameScreenExtendedDimensions() {
		return new Rect(0,0,getGameScreenExtendedWidth(),getGameScreenExtendedHeight());
	}
	
	// Setter methods
	
	/**
	 * <b><i>public void setCustomTopYGap(int percent)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the custom top y gap in percent.
	 * 
	 * @param percent
	 * <br><br>
	 * The percent of gap on the y axis you would like the top gap to fill.
	 *
	 */
	
	public void setCustomTopYGap(int percent) {
    	customTopGapYScale = percent/100f;
    	customButtomGapYScale = 1 - customTopGapYScale;
	}
	
	/**
	 * <b><i>public void setCustomLeftXGap(int percent)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the custom left x gap in percent.
	 * 
	 * @param percent 
	 * <br><br>
	 * The percent of gap on the x axis you would like the left gap to fill.
	 *
	 */
	
    public void setCustomLeftXGap(int percent) {
    	customLeftGapXScale = percent/100f;
    	customRightGapXScale = 1 - customLeftGapXScale;
    }
	
    /**
	 * <b><i>public void setScreenResizeTpe(int screenResizeType)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the screen resize type with the provided setting.
	 * 
	 * @param screenResizeType
	 * <br><br>
	 * The percent of gap on the x axis you would like the left gap to fill.
	 *
	 */
    
	public void setScreenResizeTpe(int screenResizeType) {
		
    	switch(screenResizeType) {
		case WSScreen.stretchScreen:
			WSLog.e(Game.GAME_ENGINE_TAG, this, "StretchScreen type");
			gapX = 0;
			gapY = 0;
			gameScreendst = new Rect(0,0,(int)realWidth,(int)realHeight);
			leftLetterboxLength = 0;
		    inverseRightLetterboxLength = (int) realWidth;
		    topLetterboxLength = 0;
		    inverseBottumLetterboxLength = (int) realHeight;
		    scaleXFromRealToVirtual = (float) getGameScreenWidth()/realWidth;
		    scaleYFromRealToVirtual = (float) getGameScreenHeight()/realHeight;
			break;
			
		case WSScreen.scaleProportionallyScreen:
			if(scaleYFromVirtualToreal > scaleXFromVirtualToReal) {
				// X will fill and Y will have a gap
				WSLog.e(Game.GAME_ENGINE_TAG, this, "ScalingProportioanlly type with Y gaps");
				gapX = 0;
				gapY = (int) (realHeight-(getGameScreenHeight()*scaleXFromVirtualToReal)); // Inside of viewing fields and will be (+)
				gameScreendst = new Rect(0,(int)(gapY*customTopGapYScale),(int)(realWidth),(int)(realHeight - gapY*customButtomGapYScale));
				leftLetterboxLength = 0;
			    inverseRightLetterboxLength = (int) realWidth;
			    topLetterboxLength = (int)(gapY*customTopGapYScale);
			    inverseBottumLetterboxLength = (int) (realHeight - gapY*customButtomGapYScale);
			    scaleXFromRealToVirtual = (float) getGameScreenWidth()/realWidth;
		    	scaleYFromRealToVirtual = scaleXFromRealToVirtual;
				
			}
			else {
				// Y will fill and X will have a gap
				WSLog.e(Game.GAME_ENGINE_TAG, this, "ScalingProportioanlly type with X gaps");
		    	gapX = (int) (realWidth-(getGameScreenWidth()*scaleYFromVirtualToreal)); // Inside of viewing fields and will be (+)
				gapY = 0;
				gameScreendst = new Rect((int)(gapX*customLeftGapXScale),0,(int)(realWidth - gapX*customRightGapXScale),(int)(realHeight));
				leftLetterboxLength = (int)(gapX*customLeftGapXScale);
				inverseRightLetterboxLength = (int) (realWidth - gapX*customRightGapXScale);
			    topLetterboxLength = 0;
			    inverseBottumLetterboxLength = (int) realHeight;
			    scaleYFromRealToVirtual = (float) getGameScreenHeight()/realHeight;
		    	scaleXFromRealToVirtual = scaleYFromRealToVirtual;
		    	
			}	
			break;
			
		case WSScreen.fillProportionallyScreen:
			if(scaleYFromVirtualToreal > scaleXFromVirtualToReal) {
				// X will cut off both sides and Y will fill
				WSLog.e(Game.GAME_ENGINE_TAG, this, "FillProportionallyScreen type with Y fill and X cut off");
		    	gapX = (int) ((realWidth-(getGameScreenWidth()*scaleYFromVirtualToreal))/2); // Outside of viewing fields and will be (-)
				gapY = 0;
				gameScreendst = new Rect((int)gapX,0,(int) (realWidth-gapX),(int)realHeight);
				leftLetterboxLength = (int) gapX;
			    inverseRightLetterboxLength = (int) (realWidth - gapX);
			    topLetterboxLength = 0;
			    inverseBottumLetterboxLength = (int) realHeight;
			    scaleYFromRealToVirtual = (float) getGameScreenHeight()/realHeight;
		    	scaleXFromRealToVirtual = scaleYFromRealToVirtual;
		    	
			}
			else {
				// Y will cut off both sides and X will fill
				WSLog.e(Game.GAME_ENGINE_TAG, this, "FillProportionallyScreen type with X fill and Y cut off");
				gapX = 0;
				gapY = (int) ((realHeight-(getGameScreenHeight()*scaleXFromVirtualToReal))/2); // Outside of viewing fields and will be (-)
				gameScreendst = new Rect(0,(int)gapY,(int) realWidth, (int) (realHeight-gapY));
				leftLetterboxLength = 0;
			    inverseRightLetterboxLength = (int) realWidth;
			    topLetterboxLength = (int)gapY;
			    inverseBottumLetterboxLength = (int) (realHeight - gapY);
			    scaleXFromRealToVirtual = (float) getGameScreenWidth()/realWidth;
		    	scaleYFromRealToVirtual = scaleXFromRealToVirtual;
		    	
			}	
			break;
    	}	
    	
    }
	
	
}
