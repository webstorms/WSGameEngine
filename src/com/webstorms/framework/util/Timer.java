package com.webstorms.framework.util;

import java.util.ArrayList;
import java.util.List;

public class Timer extends Object {

	private static List<Timer> allTimers;
	private Long startTime;
	private int delayTime;
	private boolean registered;
	
	public Timer() {
		this.registered = true;
		allTimers.add(this);
		
	}
	
	public Timer(int delayTime) {
		this();
		this.delayTime = delayTime;
		
	}
	
	public void setRegistered(boolean state) {
		if(state && !this.registered) {
			this.registered = true;
			this.allTimers.add(this);
			
		}
		else if(this.registered) {
			this.registered = false;
			this.allTimers.remove(this);
			
		}
		
	}
	
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
		
	}
	
	public boolean delay() {
		if(this.startTime == null) {
			this.startTime = System.currentTimeMillis();
		}
		
		if(startTime + this.delayTime < System.currentTimeMillis()) {
			this.dispose();
			return true;
			
		}
		else {
			return false;
			
		}
		
	}
	
	// The same as resume, just renew the startTime
	
	public void reset() {
		this.startTime = null;
		this.registered = true;
		this.allTimers.add(this);
		
	}
	
	public void resume() {
		this.startTime = System.currentTimeMillis();
		
	}
	
	public void dispose() {
		if(this.registered && this.allTimers.contains(this)) {
			this.allTimers.remove(this);
			
		}
		
	}
	
	public void pause() {
		// Check if timer is still running
		if(!this.delay()) {
			// Calculate new delayTime
			delayTime = (int) ((startTime + this.delayTime) - System.currentTimeMillis());
			
		}
		
		
	}
	
	public static void resumeAllTimers() {
		List<Timer> timers = new ArrayList<Timer>(Timer.allTimers);
		for (Timer timer : timers) {
		    timer.resume();
		}
		
	}
	
	public static void pauseAllTimers() {
		List<Timer> timers = new ArrayList<Timer>(Timer.allTimers);
		for (Timer timer : timers) {
		    timer.pause();
		}
		
	}
	
	public static void disposeAllTimers() {
		Timer.allTimers.clear();
		Timer.allTimers = null;
		
	}
	
	public static void initializeAllTimers() {
		allTimers = new ArrayList<Timer>();
		
	}
	
	
}
