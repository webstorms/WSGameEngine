package com.webstorms.framework;

import com.webstorms.framework.audio.Audio;
import com.webstorms.framework.input.Input;
import com.webstorms.framework.output.Output;
import com.webstorms.framework.util.Timer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public abstract class Game extends Activity {

	public static final String GAME_ENGINE_TAG = "WSGameEngine";
	public static final String CrashReport = "CrashReport";
	
	// Preferences
	String gameLogTAG = "YourGameName";
	int gameEngineLogType = WSLog.debugging;
	int gameLogType = WSLog.debugging;
	int FPS = 30;
	int maxFrameskippes = 3;
	int screenResizeType = WSScreen.stretchScreen;
	int customTopYGap = 50;
	int customLeftXGap = 50;
	int gameScreenWidth = 480;
	int gameScreenHeight = 800;
	
	// Game Engine components
	WakeLock wakeLock;
	FileIO io;
	Audio audio;
	WSScreen wsScreen;
	Canvas graphics;
	RenderView renderView;
	Input input;
	Output output;
	AssetsBase assets;
	boolean terminateGame;
	boolean crashedLastVisit;
	boolean assetsLoaded;
	
	Screen screen;
	WSLog gameLog;
	
	/**
	 * This class is the blueprint for any game you create. You extend this class and it will take care of all initialization needed
	 * to provide you with all the useful tools needed to create a game.
	 * 
	 */
	
	public Game() {
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.onCreateGameEngine();
		
		this.initializeGameEngine();
		WSLog.w(Game.GAME_ENGINE_TAG, this,"Game Engine created");
			
		new Thread(new Runnable() {

			@Override
			public void run() {
				
				// Check that the developer has initialized the assets
				if(assets != null) {
					assets.load();
					assetsLoaded = true;
					WSLog.w(Game.GAME_ENGINE_TAG, this, "Done loading assets into VRAM");
					
				}
				else {
					WSLog.e(Game.GAME_ENGINE_TAG, this, "The assets for the game haven't been defined!");
					
				}
				
			}
			
		}).start();
		
		this.checkIfAppCrahsedLastVisit();
		this.onCreateGame();
		WSLog.e(Game.GAME_ENGINE_TAG, this, "Launched " + this.gameLogTAG + "!");
		
	}

	public void onCreateGameEngine() {
		
	}
	
	public void onDisposeGame() {
		
	}
	
	/**
	 * <b><i>protected void onCreateGame()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will be called once the Game Engine has been created. Override this
	 * with content that will characterize your game eg. set a screen and create a world.
	 *
	 */
	
	protected abstract void onCreateGame();
	
	private void checkIfAppCrahsedLastVisit() {
		// Ceck if the app crashed last time
		if(this.getIO().readPrimitiveInternalMemoryBoolean(Game.CrashReport) == true) {
			WSLog.d(Game.GAME_ENGINE_TAG, this, "App crashed last time");
			crashedLastVisit = true;
					
		}
		else {
			WSLog.d(Game.GAME_ENGINE_TAG, this, "App didn't crashe last time");
			crashedLastVisit = false;
			
		}
		
		// Flag the app as chrashed and when the app is exited, then mark it as false.
		this.getIO().writePrimitiveInternalMemory(Game.CrashReport, true);
		
	}
	
	// Low level Activity methods
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		wakeLock.acquire();
		screen.resume();
		renderView.resume();
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		wakeLock.release();
		screen.pause();
		renderView.pause();
		WSLog.d(Game.GAME_ENGINE_TAG, this, "Game's underlying activity was paused");
		if(isFinishing()) {
			screen.pause();
			screen.onDispose();
			this.onDisposeGame();
			if(assets != null) {
				assets.onDispose();
			}
			this.getIO().writePrimitiveInternalMemory(Game.CrashReport, false); // App successfully closed
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Game's underlying activity has been disposed");
			
			Timer.disposeAllTimers();
			// If terminate is set to true, the app will be forced to quit after all the code above
			if(this.terminateGame == true) {
				System.exit(0);
			}
			
		}
		
	}
	
	protected void initializeGameEngine() {
		// Setup all the Game Engine components 
		gameLog = new WSLog(this.gameLogTAG);
				
		// Acquire a wakeLock to prevent the phone from sleeping
		PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
				
		io = new FileIO(this);
		this.audio = new Audio(this);
		this.wsScreen = new WSScreen(this, this.screenResizeType, this.customTopYGap, this.customLeftXGap, this.gameScreenWidth, this.gameScreenHeight);
		this.graphics = new Canvas(this.wsScreen.getGameScreen());
		this.renderView = new RenderView(this, this.wsScreen.getGameScreen(), this.FPS, this.maxFrameskippes);
		this.input = new Input(this, this.renderView.getView());
		this.output = new Output(this);
			
		Timer.initializeAllTimers();
		
		this.setContentView(this.renderView.getView());
		
	}
	
	public RenderView getGameThread() {
		return this.renderView;
		
	}
	
	/**
	 * <b><i>public void finishGame()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will start the finishing process of the game. It will indicate to some methods that the game is closing and after the 
	 * last code is executed the game is closed.
	 *
	 */
	
	public void finishGame() {
		finish();
	}
	
	/**
	 * <b><i>public void terminateGame(boolean state)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * When setting this method to true, it will force the app to close after the finishGame() method has been called. 
	 * It will close the VM. This can be usefull when dealing with unrefferenced threads.
	 * 
	 * NOTE: Only use this is you are 99.99 % sure why you are calling this. This can lead to difficulties when not disposing objects correctly.
	 *
	 */
	
	public void terminateGame() {
		this.terminateGame = true;
		this.finishGame();
		
	}
	
	// Getter methods
	
	public boolean getAssetsLoaded() {
		return this.assetsLoaded;
		
	}
	
	/**
	 * <b><i>public boolean getCrashState()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Check if the app crahsed last visit.
	 * 
	 * @return 
	 * If the app crahsed last time.
	 *
	 */
	
	public boolean getCrashState() {
		return this.crashedLastVisit;
		
	}
	
	/**
	 * <b><i>public Screen getCurrentScreen()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the current screen instance.
	 * 
	 * @return 
	 * The current set screen instance.
	 *
	 */
	
	public Screen getCurrentScreen() {
		return screen;
	}
	
	/**
	 * <b><i>public Graphics getGraphics()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the graphics instance of the game.
	 * 
	 * @return 
	 * The graphics object.
	 *
	 */
	
	public Canvas getGraphics() {
        return graphics;
    }

	/**
	 * <b><i>public Input getInput()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the input instance of the game.
	 * 
	 * @return 
	 * The input object.
	 *
	 */
	
	public Input getInput() {
        return input;
    }
	
	public Output getOutput() {
        return this.output;
    }
	
	/**
	 * <b><i>public FileIO getIO()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the FileIO instance of the game.
	 * 
	 * @return 
	 * The FileIO object.
	 *
	 */
	
	public FileIO getIO() {
		return io;
	}
	
	/**
	 * <b><i>public Audio getAudio()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Audio instance of the game.
	 * 
	 * @return 
	 * The Audio object.
	 *
	 */
	
	public Audio getAudio() {
		return audio;
	}
	
	/**
	 * <b><i>public WSScreen getWSScreen()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the WSScreen instance of the game.
	 * 
	 * @return 
	 * The WSScreen object.
	 *
	 */
	
	public WSScreen getWSScreen() {
		return wsScreen;
	}
	    
    /**
	 * <b><i>public WSLog getGameLog()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve the Game Log instance of the game.
	 * 
	 * @return 
	 * The WSLog object.
	 *
	 */
    
    public WSLog getGameLog() {
    	return gameLog;
    }
    
    // Setter methods
    
	/**
	 * <b><i>public void setAssets(Assets assets)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the assets for your game.
	 * 
	 * @param assets 
	 * <br><br>
	 * The new assets for your game.
	 *
	 *		
	 */
	
	public void setAssets(AssetsBase assets) {
		this.assets = assets;
	}
	
	public AssetsBase getGameAssets() {
		return this.assets;
		
	}
	
	/**
	 * <b><i>public void setScreen(Screen screen)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the screen for your game. The screen has two basic methods which is to <b>update</b> and <b>render</b>. The
	 * screens objective is to keep your code tidy.
	 * 
	 * NOTE: This method has to be called at least once!
	 * 
	 * For more information seek the
	 * Screen classes documentation.
	 * 
	 * @param screen 
	 * <br><br>
	 * The screen instance you wish to update and render.
	 *
	 *		
	 */
	
	public void setScreen(Screen screen) {
		if(this.screen != null) {
			this.screen.pause();
			this.screen.onDispose();
			
		}
		this.getGraphics().drawColor(Color.BLACK); // Clear virtual framebuffer
		this.screen = screen;
		this.screen.load();
		this.screen.resume(); 
		WSLog.e(Game.GAME_ENGINE_TAG, this, "New Screen has been created: " + screen.getClassTag());
		
		/*
		// The screen has never been initialized
		if(this.screen == null) {
			if (screen == null) {
				WSLog.e(Game.GAME_ENGINE_TAG, this, "The screen has not been initialized");

			}
			this.screen = screen;
			screen.resume();
		}
		// The screen has been initialized before
		else {
			this.getGraphics().drawColor(Color.BLACK); // Clear virtual framebuffer
			this.screen.pause();
			this.screen.onDispose();
			if (screen == null) {
				WSLog.e(Game.GAME_ENGINE_TAG, this, "Screen instance is null");

			}
			this.screen = screen;
			screen.resume();
			
		} */

	}
	
	// Preference methods
	
	/**
	 * <b><i>public void setTAG(String newTAG)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the Tag of your game which will be used through out your game.
	 * 
	 * @param newTAG 
	 * <br><br>
	 * The new TAG for your game.
	 *
	 *		
	 */
	
	public void setTAG(String newTAG) {
		this.gameLogTAG = newTAG;
    }
	
	/**
	 * <b><i>public void setLogType(int logType)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the log type for the Game Engine (logs from a lower level) and the Game Log (your personal logs).
	 * 
	 * @param state 
	 * <br><br>
	 * Use one of the following options:
	 * <br><br>
	 * 1. public static final int debugging = 0;
	 * <br><br>
	 * 2. public static final int shipping = 1;
	 * <br><br>
	 * 3. public static final int noWarning = 2;
	 *
	 *		
	 */
	
	public void setLogType(int logType) {
		this.gameEngineLogType = logType;
    	this.gameLogType = logType;
    	this.gameLog.setLogType(this.gameEngineLogType);
    }
    
	/**
	 * <b><i>public void setGameEngineLogType(int logType)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the log type for the Game Engine (logs from a lower level).
	 * At default debugging mode will be used.
	 * 
	 * @param logType 
	 * <br><br>
	 * Use one of the following options:
	 * <br><br>
	 * 1. public static final int debugging = 0;
	 * <br><br>
	 * 2. public static final int shipping = 1;
	 * <br><br>
	 * 3. public static final int noWarning = 2;
	 *
	 *		
	 */
	
    public void setGameEngineLogType(int logType) {
    	this.gameEngineLogType = logType;
    	
    }
    
    /**
	 * <b><i>public void setGameLogType(int logType)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the log type for the Game Log (your personal logs).
	 * At default debugging mode will be used.
	 * 
	 * @param logType 
	 * <br><br>
	 * Use one of the following options:
	 * <br><br>
	 * 1. public static final int debugging = 0;
	 * <br><br>
	 * 2. public static final int shipping = 1;
	 * <br><br>
	 * 3. public static final int noWarning = 2;
	 *
	 *		
	 */
    
    public void setGameLogType(int logType) {
    	this.gameLogType = logType;
    	
    }
    
    /**
	 * <b><i>public void setFramesPerSecond(int newFPS)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set frames per Second your Game should run at. At default the game will run 
	 * at 30 fps.
	 * 
	 * @param newFPS 
	 * <br><br>
	 * The number of frames you would like to achieve in a second.
	 *
	 *		
	 */
    
    public void setFramesPerSecond(int newFPS) {
    	FPS = newFPS;
    }
    
    /**
	 * <b><i>public void setMaxFrameSkippes(int newMaxFrameSkippes)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the amount of rendering frames that are allowed to be skipped when 
	 * the system is having difficulties to update and render the game.
	 * <br>
	 * The good side:
	 * This will insure that the game is falling behind on the updating and that it will
	 * keep on responding to interactions and display the right location of game objects.
	 *  <br>
	 * The bad side:
	 * The game might lag when rendering. Lag referring to a jumping motion between transitions of objects moving.
	 * 
	 * @param newMaxFrameSkippes 
	 * <br><br>
	 * The number of frames that are allowed to be skipped.
	 *
	 *		
	 */
    
    public void setMaxFrameSkippes(int newMaxFrameSkippes) {
    	maxFrameskippes = newMaxFrameSkippes;
    }
    
    /**
	 * <b><i>public void setScreenResizeTypeForAllScreens(int screenResizeType)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the amount of rendering frames that are allowed to be skipped when 
	 * the system is having difficulties to update and render the game.
	 * <br>
	 * The good side:
	 * This will insure that the game is falling behind on the updating and that it will
	 * keep on responding to interactions and display the right location of game objects.
	 *  <br>
	 * The bad side:
	 * The game might lag when rendering. Lag referring to a jumping motion between transitions of objects moving.
	 * 
	 * @param screenResizeType 
	 * <br><br>
	 * The type of screen resize type you would like to use:
	 *	
	 */
    
    public void setScreenResizeTypeForAllScreens(int screenResizeType) {
    	this.screenResizeType = screenResizeType;
    }
    
    /**
	 * <b><i>public void setCustomTopYGap(int percent)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the top y gap and as a logical consequence also the bottom y gap.
	 * 
	 * @param percent 
	 * <br><br>
	 * The percent of of all the gap space on the y axis available you would like the top y gap to cover.
	 *	
	 */
    
    public void setCustomTopYGap(int percent) {
    	this.customTopYGap = percent;
    }
	
    /**
	 * <b><i>public void setCustomLeftXGap(int percent)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the left x gap and as a logical consequence also the right x gap.
	 * 
	 * @param percent 
	 * <br><br>
	 * The percent of of all the gap space on the x axis available you would like the left x gap to cover.
	 *	
	 */
    
    public void setCustomLeftXGap(int percent) {
    	this.customLeftXGap = percent;
    }
    
    /**
	 * <b><i>public void setGameScreenWidth(int newWidth)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the width of the Game Screen. The Game Screen will be visible on every device that runs your game.
	 * NOTE: You should rather leave the default setting as it is.
	 * 
	 * @param newWidth 
	 * <br><br>
	 * The new width for your Game Screen.
	 *	
	 */
    
    public void setGameScreenWidth(int newWidth) {
    	this.gameScreenWidth = newWidth;
    }
    
    /**
	 * <b><i>public void setGameScreenHeight(int newHeight)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the height of the Game Screen. The Game Screen will be visible on every device that runs your game.
	 * NOTE: You should rather leave the default setting as it is.
	 * 
	 * @param newHeight 
	 * <br><br>
	 * The new height for your Game Screen.
	 *	
	 */
    
    public void setGameScreenHeight(int newHeight) {
    	this.gameScreenHeight = newHeight;
    }
    
    
}
