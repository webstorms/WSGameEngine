package com.webstorms.framework.input;

import java.util.LinkedList;

import android.view.View;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

public abstract class EventQueue<T> extends WSObject {

	protected View view;
	protected boolean inUse;
	private LinkedList<T> events = new LinkedList<T>();
    private LinkedList<T> eventQueue = new LinkedList<T>();
    
    protected EventQueue(Game game, View view) {
		super(game);
		this.view = view;
		
	}
    
    public synchronized void update() {
    	for(T event : eventQueue) {
    		this.processEvent(event);
    		
    	}
        events = new LinkedList<T>(eventQueue);
        eventQueue.clear();
        
    }
    
    protected void processEvent(T event) {
    	
    }
    
    public synchronized void addEvent(T newEvent) {
    	eventQueue.add(newEvent);
    	
    }
    
    public LinkedList<T> getEvents() {
        return events;
        
    }
    
    public boolean inUse() {
    	return this.inUse;
    	
    }
    
    
}
