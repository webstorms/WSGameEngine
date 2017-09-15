package com.webstorms.framework.graphics.animation;

import com.webstorms.framework.Game;
import com.webstorms.framework.util.Timer;
import com.webstorms.framework.util.Util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class SpriteSheetAnimation extends GraphicObject {
	
	// Get Bitmap should return the current caption
	
	int animationSheetLength;
	int animationSheetHeight;
	Rect srcRect;
	
	float left;
	float bottom;
	float lengthOfSpriteInSheet;
	float heightOfSpriteInSheet;
	
	boolean repeatAnimation;
	boolean animationCompleted;
	boolean animationStarted;
	
	Timer frameDelayPeriod = new Timer();
	Bitmap[] sprites;
	int currentSprite;
	
	/**
	 * You input a bitmap and the number of sprites (by defining the number of rows and columns), and this class will
	 * take care of animating the sheet.
	 * 
	 */
	
	public SpriteSheetAnimation(Game game, Bitmap bitmap, Rect dst, int amountOfRows, int amountOfColumns) {
		super(game);
		this.setBitmap(bitmap);
		this.animationSheetLength = bitmap.getWidth();
		this.animationSheetHeight = bitmap.getHeight();
		
		this.lengthOfSpriteInSheet = (float) this.animationSheetLength/amountOfColumns;
		this.heightOfSpriteInSheet = (float) this.animationSheetHeight/amountOfRows;
		
		left = (float) 0;
		bottom = (float) this.heightOfSpriteInSheet;
		
		srcRect = new Rect(Math.round(left), Math.round(bottom - this.heightOfSpriteInSheet), Math.round(left + this.lengthOfSpriteInSheet), Math.round(bottom));
		this.getShape().addRectangle(new Rect(dst));
		
		repeatAnimation = false;
		animationCompleted = false;
		animationStarted = false;
		
		// this.getShape().addRectangle(new Rect(0, 0, (int) this.heightOfSpriteInSheet, (int) this.lengthOfSpriteInSheet));
		
	}
	
	public SpriteSheetAnimation(Game game, Bitmap bitmap, Rect src, Rect dst, int amountOfRows, int amountOfColumns, float timeSpan, boolean isLarge) {
		this(game, bitmap, dst, amountOfRows, amountOfColumns);
		
		// Calculate timeSpan
		this.getGame().getGameLog().d(this, "LOL " + (int) ((timeSpan/(amountOfRows * amountOfColumns) * 1000)));
		this.frameDelayPeriod.setDelayTime((int) ((timeSpan/(amountOfRows * amountOfColumns) * 1000)));
		
		// Splitup into multiple Bitmaps
		if(isLarge) {
			int amountOfSprites = amountOfRows * amountOfColumns;
			sprites = new Bitmap[amountOfSprites];
			for(int i = 0; i < amountOfSprites; i++) {
				// Move to new frame
				srcRect.set(Math.round(left), Math.round(bottom - this.heightOfSpriteInSheet), Math.round(left + this.lengthOfSpriteInSheet), Math.round(bottom));
				sprites[i] = Util.getBitmapFromRegion(bitmap, srcRect);
				
				// Change the location, so the next time the frame is set, it will be set accordingly
				left = left + this.lengthOfSpriteInSheet;
				if(left == this.animationSheetLength) {
					bottom = bottom + this.heightOfSpriteInSheet;
					left = 0;

				}
				
			}
			
			this.srcRect = src;
			this.setBitmap(sprites[0]);
			currentSprite = 1;
			
		}
		
	}
	
	//@Override
	// public void defineShape(Shape shape) {
		// No need to add content to shape, this is done on line 46 this.getShape().addRectangle(new Rect(dst));
		
	// }
	
	public void setSrcRect(Rect src) {
		this.srcRect = src;
		
	}
	
	/**
	 * <b><i>public Rect getSrcRect()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the rect that will cover the current source of the sheet.
	 * 
	 * @return 
	 * The current rect that will cover the current source of the sheet.
	 *
	 */
	
	public Rect getSrcRect() {
		return srcRect;
	}
	
	/**
	 * <b><i>public Rect getDstRect()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the rect where the bitmap will be scaled to fit into.
	 * 
	 * @return 
	 * The current rect where the bitmap will be scaled to fit into.
	 *
	 */
	
	public Rect getDstRect() {
		return this.getShape().getRect();
		
	}
	
	/**
	 * <b><i>public void repeatAnimation(boolean repetition)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will set if the animation should be repeated or not.
	 * 
	 * @param state 
	 * <br><br>
	 * 1. True = The animation will repeat once completed and keep on doing so.
	 * <br><br>
	 * 2. False = The animation will play only once.
	 *
	 *		
	 */
	
	public void repeatAnimation(boolean repetition) {
		this.repeatAnimation = repetition;
	}
	
	/**
	 * <b><i>public boolean isAnimationFinished()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve if the animation has finished.
	 * 
	 * @return 
	 * If the animation has finished.
	 *
	 */
	
	public boolean isAnimationFinished() {
		return animationCompleted;
	}
	
	/**
	 * <b><i>public boolean hasBeenStarted()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve if the animation has started.
	 * 
	 * @return 
	 * If the animation has started.
	 *
	 */
	
	public boolean hasBeenStarted() {
		return animationStarted;
	}
	
	/**
	 * <b><i>public void render()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will render the animation (the current picture of the sheet).
	 *
	 */

	@Override
	public void renderBitmap() {
		this.getGame().getGraphics().drawBitmap(this.getBitmap(), this.getSrcRect(), this.getDstRect(), null);
		
	}

	/**
	 * <b><i>public void update()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will update the animation.
	 *
	 */
	
	@Override
	public void update() {
		// Every update will advance to the next frame of the animation
		// Set if animation has started
		if(!animationStarted) {
			animationStarted = true;
		}
		
		if(this.frameDelayPeriod.delay() && !animationCompleted) {
			this.frameDelayPeriod.reset();
			
			if(this.sprites != null) {
				this.setBitmap(this.sprites[this.currentSprite - 1]);	
				currentSprite++;
				if(this.currentSprite == this.sprites.length) {
					if(repeatAnimation) {
						this.currentSprite = 1;
						
					}
					else {
						animationCompleted = true;
						
					}
					
				}
				
			}
			else {
				// Move to new frame
				srcRect.set(Math.round(left), Math.round(bottom - this.heightOfSpriteInSheet), Math.round(left + this.lengthOfSpriteInSheet), Math.round(bottom));
				// Change the location, so the next time the frame is set, it will be set accordingly
				left = left + this.lengthOfSpriteInSheet;
				
				if(Math.round(left) == this.animationSheetLength && Math.round(bottom) == this.animationSheetHeight) {
					if(repeatAnimation) {
						left = 0;
						bottom = (float) this.heightOfSpriteInSheet;
					//	srcRect = new Rect(Math.round(left), Math.round(bottom - this.heightOfSpriteInSheet), Math.round(left + this.lengthOfSpriteInSheet), Math.round(bottom));
					//	left = left + this.lengthOfSpriteInSheet;
						
					}
					else {
					//	this.frameDelayPeriod.dispose();
						animationCompleted = true;
						
					}
					
				}
						
				if(left == this.animationSheetLength) {
					bottom = bottom + this.heightOfSpriteInSheet;
					left = 0;
					
				}
				
			}
			
		}
		
	}
	
	public void dispose() {
		this.frameDelayPeriod.dispose();
		
	}
	
	
}
