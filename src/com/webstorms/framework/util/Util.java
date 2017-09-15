package com.webstorms.framework.util;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Util {
	
	// http://stackoverflow.com/questions/80476/how-to-concatenate-two-arrays-in-java
	public static <T> T[] concat(T[] a, T[] b) {
	    final int alen = a.length;
	    final int blen = b.length;
	    if (alen == 0) {
	        return b;
	        
	    }
	    if (blen == 0) {
	        return a;
	        
	    }
	    final T[] result = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), alen + blen);
	    System.arraycopy((T[]) a, 0, result, 0, alen);
	    System.arraycopy((T[]) b, 0, result, alen, blen);
	    return result;
	    
	}

	/**
	 * <b><i>public static int centerObject(int objectLength, int start, int end)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Get the x value of centering a object between to points.
	 * 
	 * @return 
	 * The x value that centers the object between to points.
	 *
	 */
	
	public static int centerObject(int objectLength, int start, int end) {
		return ((end - start) - objectLength)/2 + start;
		
	}
	
	public static int getRandomNumberBetween(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt(max - min) + min;
        if(randomNumber == min) {
            // Since the random number is between the min and max values, simply add 1
            return min + 1;
        }
        else {
            return randomNumber;
        }

    }
	
	public static int getRandomNumberFrom(int min, int max) {
        Random foo = new Random();
        int randomNumber = foo.nextInt((max + 1) - min) + min;
        
        return randomNumber;

    }
	
	public static boolean getRandomBoolean() {
        Random foo = new Random();
        return foo.nextBoolean();

    }
	
	public static boolean inRangeBetween(int start, int end, int value) {
		return (value > start && value < end);
		
	}
	
	public static boolean inRangeFrom(int start, int end, int value) {
		return ((value > start || value == start) && (value < end || value == end));
	}
	
	public static float round(float value, int decimalPlaces) {
		float p = (float) Math.pow(10,decimalPlaces);
		value *= p;
		float tmp = Math.round(value);
		return (float)tmp/p;
		
		
	}
	
	public static int getAbsoluteAngle(int degrees) {
		int absoluteValue;
		
		if(degrees > 0) {
			absoluteValue = degrees;
			
		}
		else {
			absoluteValue = 360 + degrees;
			
		}
		
		return absoluteValue;
		
	}
	
	public static Bitmap getBitmapFromRegion(Bitmap bitmap, Rect region) {
	    Bitmap output = Bitmap.createBitmap(region.right - region.left, region.bottom - region.top, Config.ARGB_4444);
	    Canvas canvas = new Canvas(output);
	    canvas.drawBitmap(bitmap, region, new Rect(0, 0, region.right - region.left, region.bottom - region.top), null);
	    return output;
	    
	}
	
	/**
	 * <b><i>public boolean isKeyPressed(Integer keyCode1, Integer keyCode2)</i></b>
	 * <br>
	 * Since: API 1
	 * <br>
	 * <br>
	 * Check if the provided key is pressed.
	 *
	 *@param 
	 *Provide the key you would like to check if its pressed and the key to compare to.
	 *
	 *@return 
	 * If the provided key is currently pressed.
	 *
	 */
    
    public static boolean isKeyPressed(Integer keyCode1, Integer keyCode2) {
    	if(keyCode1.equals(keyCode2)) {
    		return true;
    		
    	}
    	else {
    		return false;
    		
    	}
    	
    }
	
    
}
