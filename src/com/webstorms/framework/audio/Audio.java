package com.webstorms.framework.audio;

import java.io.IOException;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class Audio extends WSObject {
	
	public static final int SOUND_MAX = 100;
	public static final int SOUND_MUTE = 0;
	
	private SoundPool soundPool;
	
	/**
	 * Factory class that generates Music and Sound instances.
	 * 
	 */
	
	public Audio(Game game) {
		super(game);
		this.getGame().setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
		
		// Set Music Stream volume to Ring Stream volume
		AudioManager audioManager = (AudioManager) this.getGame().getSystemService(Context.AUDIO_SERVICE);
		float percentage = audioManager.getStreamVolume(AudioManager.STREAM_RING) / (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) * percentage), 0);
		
		
	}
	
	/**
	 * <b><i>public Music newMusic(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve a music instance.
	 * 
	 * @param filename
	 * The name of the music file (preferable ".mp3").
	 * 
	 * @return
	 * A Music instance.
	 *
	 */
	
	public Music newMusic(String filename) {
		AssetFileDescriptor assetDescriptor = null;
			try {
				assetDescriptor = this.getGame().getIO().readAssetsAudio(filename);
			} catch (IOException e) {
			}
		return new Music(this.getGame(), new MediaPlayer(), assetDescriptor);
		
	}
	
	/**
	 * <b><i>public Sound newSound(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve a sound instance.
	 * 
	 * @param filename
	 * The name of the sound file (preferable ".ogg").
	 * 
	 * @return
	 * A Sound instance.
	 *
	 */
	
	public Sound newSound(String filename) {
		AssetFileDescriptor assetDescriptor = null;
		int soundId = 0;
			try {
				assetDescriptor = this.getGame().getIO().readAssetsAudio(filename);
			} catch (IOException e) {
			}
			soundId = this.soundPool.load(assetDescriptor, 1);
		return new Sound(this.getGame(), this.soundPool, soundId);
	}
	
	public void pauseAllSounds() {
		this.soundPool.autoPause();
		
	}
	
	public void resumeAllSounds() {
		this.soundPool.autoResume();
		
	}
	
	public void disposeMusic(Music music) {
		music.dispose();
		music = null;
		
	}
	
	public void disposeSound(Sound sound) {
		sound.dispose();
		sound = null;
		
	}
	
	
}
