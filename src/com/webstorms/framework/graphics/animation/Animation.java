package com.webstorms.framework.graphics.animation;

import com.webstorms.physics.Shape;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class Animation {
	
	public static Bitmap rotateBitmap(Bitmap bitmap, int rotation) {
		Matrix rotateMatrix = new Matrix();
		rotateMatrix.postRotate(rotation, bitmap.getWidth()/2, bitmap.getHeight()/2);
        
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotateMatrix, true);
		
	}
	
	public static Matrix rotateMatrix(Bitmap bitmap, Shape shape, int rotation) {
		
		float scaleWidth = ((float) shape.getWidth()) / bitmap.getWidth();
        float scaleHeight = ((float) shape.getHeight()) / bitmap.getHeight();
        
		Matrix rotateMatrix = new Matrix();
		rotateMatrix.postScale(scaleWidth, scaleHeight);
		rotateMatrix.postRotate(rotation, shape.getWidth()/2, shape.getHeight()/2);
		rotateMatrix.postTranslate(shape.getX(), shape.getY());
		
        
		return rotateMatrix;
		
	}
	
	
}
