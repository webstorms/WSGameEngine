package com.webstorms.framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class RenderView extends WSObject implements Runnable {
	
	Bitmap gameScreen;
	SurfaceView surfaceView;
	Thread gameloop;
	boolean running;

	int sleepTime;
	int numberOfFramesSkipped;
	int maxFrameSkips;
	long beginTime;
	long endTime;
	long lastTime;
	int differenceTime;
	int framePeriod;
	Canvas frameBuffer;
	int frameCount;

	int realFPS;
	int setFPS;

	/**
	 * This class is the game loop that will update and render the game.
	 * 
	 */

	public RenderView(Game game, Bitmap gameScreen, int fps, int maxFrameSkips) {
		super(game);
		this.gameScreen = gameScreen;
		surfaceView = new SurfaceView(game);
		this.setFPS = fps;
		this.framePeriod = 1000/this.setFPS;
		this.maxFrameSkips = maxFrameSkips;
		lastTime = System.currentTimeMillis();
		beginTime = System.currentTimeMillis();

	}

	public SurfaceView getView() {
		return this.surfaceView;

	}

	@Override
	public void run() {
		while(running) {
			if(this.surfaceView.getHolder().getSurface().isValid()) {

				beginTime = System.currentTimeMillis();
				this.getGame().getInput().update(); // Synchronize input and call all attached listeneres
				this.getGame().getCurrentScreen().update();
				this.renderFrameBuffer();
				
				// Frame Per Second Count
				frameCount++;

				if(lastTime + 1000 < System.currentTimeMillis()) {
					WSLog.d(Game.GAME_ENGINE_TAG, this, "REAL FPS: " + frameCount);
					this.realFPS = frameCount;
					lastTime = System.currentTimeMillis();
					frameCount = 0;

				}

				endTime = System.currentTimeMillis();
				differenceTime = (int) (endTime - beginTime);
				sleepTime = (int) (framePeriod - differenceTime);

				if(sleepTime > 0) {
					try {
						Thread.sleep(sleepTime);
						
					} 
					catch (InterruptedException exception) {
						exception.printStackTrace();
						
					}
					
				}
				else {
					while(sleepTime < 0 && numberOfFramesSkipped < this.maxFrameSkips) {
						WSLog.d(Game.GAME_ENGINE_TAG, this, "Game thread is only updating the update method and is not rendering anything");
						this.getGame().getCurrentScreen().update();
						sleepTime += framePeriod;
						numberOfFramesSkipped++;

					}
					
				}

			}

		}


	}

	public int getRealFPS() {
		return this.realFPS;

	}

	public int getSetFPS() {
		return this.setFPS;
		
	}
	
	private void renderFrameBuffer() {
		// Update the current virtual screen image
		this.getGame().getCurrentScreen().render();
		// Render the current virtual screen to the real phone screen
		frameBuffer = this.surfaceView.getHolder().lockCanvas();
		if(frameBuffer != null) { // Fix for mysterious bug ( FATAL EXCEPTION: Thread)
			frameBuffer.drawBitmap(this.gameScreen, null, this.getGame().getWSScreen().getGameScreendst(), null);
			this.surfaceView.getHolder().unlockCanvasAndPost(frameBuffer);

		}
		else {
			WSLog.e(Game.GAME_ENGINE_TAG, this, "Surface has not been created or otherwise cannot be edited");

		}
		
	}

	public void resume() { 
		this.running = true;
		gameloop = new Thread(this);
		gameloop.start();      

	}   

	public void pause() { 
		this.running = false;
		running = false;                        
		while(true) {
			try {
				gameloop.join();
				break;
			} 
			catch (InterruptedException e) {
				// retry
			}

		}

	}


}