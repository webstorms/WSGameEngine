package com.webstorms.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Environment;

public class FileIO extends WSObject {
	
	AssetManager assets;
	String externalStoragePath;
	OutputStreamWriter outputStreamWriter; 
    InputStreamReader inputStreamReader;
	char[] inputBuffer;
	
	/**
	 * In this class, you can find all methods relating to input and output of files on your phone and on your computer.
	 * One can read bitmaps or Audio from the Assets folder of your Android project stored on your computer.
	 * Or you can read or write String objects on your phone eg. for storing local user data such as highscores.
	 * 
	 */
	
	public FileIO(Game game) {
		super(game);
		this.assets = game.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
		
	}
	
	/**
	 * <b><i>public void disposeBitmap(Bitmap bitmap)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Dispose the Bitmap. This will remove it from the memory and set it to null.
	 *	
	 * @param bitmap 
	 * The bitmap you would like to dispose.
	 *
	 */
	
	public void disposeBitmap(Bitmap bitmap) {
		bitmap.recycle();
		bitmap = null;
		WSLog.d(Game.GAME_ENGINE_TAG, this,"Disposed bitmap");
		
	}
	
	/**
	 * <b><i>public Bitmap readAssetsBitmap(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will return a bitmap object from the assets folder of your project.
	 * 
	 * @param filename 
	 * The name of the Bitmap you wish to import.
	 *
	 * @return Bitmap object
	 *
	 *		
	 */
	
	public Bitmap readAssetsBitmap(String filename) throws IOException {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options(); 
			options.inPurgeable = true;
			Bitmap bitmap = BitmapFactory.decodeStream(assets.open(filename), null, options);
			if(bitmap == null) {
				WSLog.d(Game.GAME_ENGINE_TAG, this,"File cannot be opened: It's value is null");
				throw new IOException("File cannot be opened: It's value is null");
			}
			else {
				WSLog.d(Game.GAME_ENGINE_TAG, this,"File successfully read: " + filename);
				return bitmap;
			}
		} 
		catch (IOException e) {
			WSLog.d(Game.GAME_ENGINE_TAG, this,"File cannot be opened: " + e.getMessage());
			throw new IOException("File cannot be opened: " + e.getMessage());
		}
		
	}
	
	/**
	 * <b><i>Typeface readTypeface(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will return a Typeface object (font) from the assets folder of your project.
	 * 
	 * @param filename 
	 * The name of the Typeface you wish to import.
	 *
	 * @return Typeface object
	 *
	 *		
	 */
	
	public Typeface readTypeface(String filename) {
		return Typeface.createFromAsset(assets, filename); 
		
	}
	
	/**
	 * <b><i>public Bitmap readAssetsAudio(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will return a audio object from the assets folder of your project.
	 * 
	 * @param filename 
	 * The name of the Audio you wish to import.
	 *
	 * @return AssetFileDescriptor object
	 *
	 *		
	 */
	
	public AssetFileDescriptor readAssetsAudio(String filename) throws IOException {
		try {
			WSLog.d(Game.GAME_ENGINE_TAG, this,"File successfully read: " + filename);
			return assets.openFd(filename);
			
		} 
		catch (IOException e) {
			WSLog.d(Game.GAME_ENGINE_TAG, this,"Audio cannot be opened: " + e.getMessage());
			throw new IOException("Audio cannot be opened: " + e.getMessage());
			
		}
		
	}

	/**
	 * <b><i>public Bitmap writeInternalMemory(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * This method will write a String object to the memory of a phone.
	 * 
	 * @param filename 
	 * The name of the String file you wish to write.
	 *
	 *		
	 */
	
	public boolean writeInternalMemory(String filename, String message) {
		try {
			outputStreamWriter = new OutputStreamWriter(this.getGame().openFileOutput(filename, Context.MODE_PRIVATE));
			outputStreamWriter.write(message);
			outputStreamWriter.flush();
			outputStreamWriter.close();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Successfully wrote file " + filename + " to the internal memory");
			return true;
		} 
		catch (IOException e) {
			WSLog.d(Game.GAME_ENGINE_TAG, this, e.getMessage());
			return false;
		}
		
	}
	
	/**
	 * <b><i>public void writePrimitiveInternalMemory(String filename, int value)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Write a integer value to the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to write.
	 *
	 *		
	 */
	
	public void writePrimitiveInternalMemory(String filename, int value) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(filename, value);
		editor.commit();
		
	}
	
	/**
	 * <b><i>public int readPrimitiveInternalMemoryInteger(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve a integer value from the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public int readPrimitiveInternalMemoryInteger(String filename) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		return preferences.getInt(filename, 0);
		
	}
	
	/**
	 * <b><i>public void writePrimitiveInternalMemory(String filename, boolean value)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Write a boolean value to the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to write.
	 *
	 *		
	 */
	
	public void writePrimitiveInternalMemory(String filename, boolean value) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(filename, value);
		editor.commit();
		
	}
	
	/**
	 * <b><i>public boolean readPrimitiveInternalMemoryBoolean(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve a boolean value from the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public boolean readPrimitiveInternalMemoryBoolean(String filename) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		return preferences.getBoolean(filename, false);
		
	}
	
	/**
	 * <b><i>public void writePrimitiveInternalMemory(String filename, String value)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Write a String value to the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to write.
	 *
	 *		
	 */
	
	public void writePrimitiveInternalMemory(String filename, String value) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(filename, value);
		editor.commit();
		
	}
	
	/**
	 * <b><i>public boolean readPrimitiveInternalMemoryString(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Retrieve a String value from the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public String readPrimitiveInternalMemoryString(String filename) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		return preferences.getString(filename, "");
		
	}
	
	/**
	 * <b><i>public void writeObjectToMemory(String filename, Object object)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Write a object to the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to write to.
	 *
	 *		
	 */
	
	public void writeObjectToMemory(String filename, Object object) {
		FileOutputStream fos;
		try {
			fos = this.getGame().openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(object);
			os.close();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object successfully written: " + filename);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be written: " + filename);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be written: " + filename);
			
		}
		
	}
	
	/**
	 * <b><i>public void writeBitmapToMemory(String filename, Bitmap bitmap)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Write a bitmap to the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to write to.
	 *
	 *		
	 */
	
	public void writeBitmapToMemory(String filename, Bitmap bitmap) {
		FileOutputStream fos;
		// Use the compress method on the Bitmap object to write image to the OutputStream
		try {
			fos = this.getGame().openFileOutput(filename, Context.MODE_PRIVATE);
			// Writing the bitmap to the output stream
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
			fos.close();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Bitmap successfully written: " + filename);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Bitmap couldn't be written: " + filename);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Bitmap couldn't be written: " + filename);
			
		}
		
	}
	
	/**
	 * <b><i>public Object readObjectFromMemory(String filename, Object object)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Read a object from the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public Object readObjectFromMemory(String filename) {
		Object defautObject = null;
		FileInputStream fis;
		try {
			fis = this.getGame().openFileInput(filename);
			ObjectInputStream is = new ObjectInputStream(fis);
			defautObject = is.readObject();
			is.close();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object successfully read: " + filename);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (StreamCorruptedException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		}
		
		return defautObject;
		
	}
	
	/**
	 * <b><i>public Bitmap readBitmapFromMemory(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Read a bitmap from the phone's internal storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public Bitmap readBitmapFromMemory(String filename) {
		Bitmap defautBitmap = null;
		File filePath = this.getGame().getFileStreamPath(filename);
		FileInputStream fi;
		try {
			fi = new FileInputStream(filePath);
			defautBitmap = BitmapFactory.decodeStream(fi);
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Bitmap successfully read: " + filename);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Bitmap couldn't be opened: " + filename);
			
		}
		
		return defautBitmap;
		
	}
	
	/*
	 * NOTE: Rather make use of the public void writeObjectToMemory(String filename, List<Object> objects) method.
	public void writeObjectsToMemory(String filename, List<Object> objects) {
		this.writePrimitiveInternalMemory(filename, objects.size());
		FileOutputStream fos;
		try {
			for(int i = 0; i < objects.size(); i++) {
				fos = game.openFileOutput(filename + "_" + i, Context.MODE_PRIVATE);
				ObjectOutputStream os = new ObjectOutputStream(fos);
				os.writeObject(objects.get(i));
				os.close();
			}
			this.gameEngineLog.d(classTAG, "Object successfully written: " + filename);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			this.gameEngineLog.d(classTAG, "Object couldn't be opened: " + filename);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			this.gameEngineLog.d(classTAG, "Object couldn't be opened: " + filename);
			
		}
		
	} */
	
	/**
	 * <b><i>public Object readObjectsFromMemory(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Read multiple objects from the phone's internal storage that are stored in a List<>.
	 * 
	 * @param filename 
	 * The name of the file you wish to read from.
	 *
	 *		
	 */
	
	public Object readObjectsFromMemory(String filename) {
		int defaultObjectSize = this.readPrimitiveInternalMemoryInteger(filename);
		List<Object> defautObjects = new ArrayList<Object>();
		FileInputStream fis;
		try {
			for(int i = 0; i < defaultObjectSize; i++) {			
				fis = this.getGame().openFileInput(filename + "_" + i);
				ObjectInputStream is = new ObjectInputStream(fis);
				defautObjects.add(is.readObject());
				is.close();
				
			}
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object successfully read: " + filename);
			
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (StreamCorruptedException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (IOException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			WSLog.d(Game.GAME_ENGINE_TAG, this, "Object couldn't be opened: " + filename);
			
		}
		
		return defautObjects;
		
	}
	
	/**
	 * <b><i>public void removeFile(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Remove created files on the internal memory storage.
	 * 
	 * @param filename 
	 * The name of the file you wish to remove from the internal memory.
	 *
	 *		
	 */
	
	public void removeFile(String filename) {
		SharedPreferences preferences = this.getGame().getPreferences(Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.remove(filename);
		editor.commit();
		
	}
	
	/**
	 * <b><i>public void removeBitmap(String filename)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Remove written bitmaps from the internal memory storage.
	 * 
	 * @param filename 
	 * The name of the bitmap you wish to remove from the internal memory.
	 *
	 *		
	 */
	
	public void removeBitmap(String filename) {
		this.writeObjectToMemory(filename, null);
		
	}
	
	
}
