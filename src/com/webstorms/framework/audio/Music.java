package com.webstorms.framework.audio;

import java.io.IOException;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class Music extends WSObject {
	
	private MediaPlayer mediaPlayer;
	private AssetFileDescriptor assetDescriptor;
	
	/**
	 * All the methods relating to control a music instance.
	 * 
	 */
	
	public Music(Game game, MediaPlayer mediaPlayer, AssetFileDescriptor assetDescriptor) {
		super(game);
		this.mediaPlayer = mediaPlayer;
		this.assetDescriptor = assetDescriptor;
		try {
			this.mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			this.mediaPlayer.prepare();
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVolume(100); // Default
		
	}

	public void stop() {
		this.mediaPlayer.stop();
		
	}
	
	/**
	 * <b><i>public void play()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Start the music play.
	 *	
	 */
	
	public void play() {
		this.mediaPlayer.start();
		
	}

	public void restart() {
		this.mediaPlayer.reset();
		try {
			this.mediaPlayer.setDataSource(assetDescriptor.getFileDescriptor(), assetDescriptor.getStartOffset(), assetDescriptor.getLength());
			this.mediaPlayer.prepare();
		} 
		catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mediaPlayer.start();

	}
	
	/**
	 * <b><i>public void pause()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Pause the music play.
	 *	
	 */
	
	public void pause() {
		this.mediaPlayer.pause();
		
	}
	
	/**
	 * <b><i>public void loop(boolean bool)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set if you would like to loop the music instance. If it stops it starts again.
	 * <br>
	 * Default: Won't loop.
	 * 
	 * @param bool 
	 * <br><br>
	 * True = Will loop.
	 * <br>
	 * False = Won't loop.
	 *	
	 */
	
	public void loop(boolean bool) {
		this.mediaPlayer.setLooping(bool);
		
	}
	
	/**
	 * <b><i>public boolean isPlaying()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve if the music is playing.
	 * 
	 * @return 
	 * If the music is playing.
	 *
	 */
	
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}
	
	/**
	 * <b><i>public void setVolume(int vL, int vR)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the volume.
	 * 
	 * @param vL
	 * Set the left volume in percent.
	 *	
	 * @param vR
	 * Set the right volume in percent.
	 *
	 */
	
	public void setVolume(int vL, int vR) {
		float volumeLeft = (float) (1 - (Math.log(Audio.SOUND_MAX - vL) / Math.log(Audio.SOUND_MAX)));
		float volumeRight = (float) (1 - (Math.log(Audio.SOUND_MAX - vR) / Math.log(Audio.SOUND_MAX)));
		mediaPlayer.setVolume(volumeLeft, volumeRight);
	}
		
	/**
	 * <b><i>public void setVolume(int volume)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Set the volume.
	 * 
	 * @param volume
	 * Set the volume in percent.
	 *
	 */
	
	public void setVolume(int volume) {
		float v = (float) (1 - (Math.log(Audio.SOUND_MAX - volume) / Math.log(Audio.SOUND_MAX)));
		mediaPlayer.setVolume(v,v);
	}
	
	/**
	 * <b><i>public void release()</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Release this music instance. Do this if it isn't required anymore.
	 *	
	 */
	
	public void dispose() {
		mediaPlayer.release();
	}
	
	
}
