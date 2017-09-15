package com.webstorms.framework;

import android.util.Log;

public class WSLog {

	public static final int debugging = 0;
	public static final int shipping = 1;
	
	private static int logType = debugging;
	
	private String projectTAG;
	
	/**
	 * This class gives logging capability to the game. 
	 * 
	 */
	
	WSLog(String projectTAG) {
		this.projectTAG = projectTAG;
		
	}
	
	public static void setLogType(int logType) {
		WSLog.logType = logType;
		
	}
	
	/**
	 * <b><i>public void d(String className, String msg)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Log a debug log.
	 * 
	 * @param className 
	 * <br>
	 * The name used to identify the source of the message.
	 * 
	 * @param msg
	 * <br>
	 * The message you would like to log.
	 *	
	 */
	
public static void d(String projectTAG, Object classTAG, String msg) {
		
		classTAG = classTAG.getClass().getSimpleName();
		
		if(logType == debugging) {
			Log.d(projectTAG, classTAG + ": " + msg + ".");
			
		}
		
	}
	
	public void d(Object classTAG, String msg) {
		
		classTAG = classTAG.getClass().getSimpleName();
		
		if(logType == debugging) {
			Log.d(this.projectTAG, classTAG + ": " + msg + ".");
			
		}
		
	}
	
	/**
	 * <b><i>public void w(String className, String msg)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Log a warning log.
	 * 
	 * @param className 
	 * <br>
	 * The name used to identify the source of the message.
	 * 
	 * @param msg
	 * <br>
	 * The warning you would like to log.
	 *	
	 */

	public static void w(String projectTAG, Object classTAG, String msg) {
		
		classTAG = classTAG.getClass().getSimpleName();
		
		if(logType == debugging) {
			Log.w(projectTAG, classTAG + ": " + msg + ".");
			
		}
		
	}
	
	public void w(Object classTAG, String msg) {
	
		classTAG = classTAG.getClass().getSimpleName();
		
		if(logType == debugging) {
			Log.w(this.projectTAG, classTAG + ": " + msg + ".");
			
		}
		
	}
	
	/**
	 * <b><i>public void e(String className, String msg)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Log a error log.
	 * 
	 * @param className 
	 * <br>
	 * The name used to identify the source of the message.
	 * 
	 * @param msg
	 * <br>
	 * The error you would like to log.
	 *	
	 */
	
	public static void e(String projectTAG, Object classTAG, String msg) {
		
		classTAG = classTAG.getClass().getSimpleName();
	
		Log.e(projectTAG, classTAG + ": " + msg + ".");

		
	}

	public void e(Object classTAG, String msg) {
		
		classTAG = classTAG.getClass().getSimpleName();
		
		Log.e(this.projectTAG, classTAG + ": " + msg + ".");
		
	}
	
	
}
