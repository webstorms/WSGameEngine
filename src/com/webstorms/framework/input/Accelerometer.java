package com.webstorms.framework.input;

import com.webstorms.framework.Game;
import com.webstorms.framework.WSObject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class Accelerometer extends WSObject implements SensorEventListener {

	private boolean inUse;
	SensorManager manager;
    Sensor accelerometer;
	float accelX;
	float accelY;
	float accelZ;
	
	protected Accelerometer(Game game) {
		super(game);
		manager = (SensorManager) this.getGame().getSystemService(Context.SENSOR_SERVICE);
    	accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
    	
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		accelX = event.values[0];
        accelY = event.values[1];
        accelZ = event.values[2];
        
	}

	public float getAccelX() {
        return accelX;
        
    }

    public float getAccelY() {
        return accelY;
        
    }

    public float getAccelZ() {
        return accelZ;
        
    }
	
    public void useAccelerometer(boolean use) {
    	this.inUse = use;
		if(use) {
			manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
			
		}
		else {
			manager.unregisterListener(this, accelerometer);
			
		}
		
	}
	
    public boolean inUse() {
    	return this.inUse;
    	
    }
    
    
}