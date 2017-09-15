package com.webstorms.framework.audio;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.media.SoundPool;

public class Sound extends WSObject {

	int soundId;
    SoundPool soundPool;
    float volumeLeft;
	float volumeRight;
	boolean loop;
	
	private int streamID;
	
	public Sound(Game game, SoundPool soundPool, int soundId) {
		super(game);
		this.soundId = soundId;
        this.soundPool = soundPool;
        this.setVolume(100); // Default
        
	}
	
	public void loop(boolean loop) {
		this.loop = loop;
		
	}
	
	public void stop() {
		this.soundPool.stop(this.streamID);
		
	}
	
	public void play() {
		if(loop) {
			streamID = soundPool.play(soundId, volumeLeft, volumeRight, 0, -1, 1);
			
		}
		else {
			streamID = soundPool.play(soundId, volumeLeft, volumeRight, 0, 0, 1);
			
		}
		
	}
	
	public void setVolume(int vL, int vR) {
		volumeLeft = vL/100f;
		volumeRight = vR/100f;
		soundPool.setVolume(this.streamID, volumeLeft, volumeRight);
		
	}
	
	public void setVolume(int volume) {
		volumeLeft = volume/100f;
		volumeRight = volume/100f;
		soundPool.setVolume(this.streamID, volumeLeft, volumeRight);
		
	}
	
	public void resume() {
		soundPool.resume(this.streamID);
		
	}
	
	public void pause() {
		soundPool.pause(this.streamID);
		
	}
	
	public void dispose() {
		soundPool.unload(this.soundId);
		
	}
	
	
}
