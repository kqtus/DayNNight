package com.example.elmar.daynnight.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * Created by Marek on 09.05.2018.
 */

public class ShakeSensoListener implements SensorEventListener {
    private long lastUpdate = 0;
    private long lastShakeTime = 0;
    private float x;
    private float y;
    private float z;
    private float lastX;
    private float lastY;
    private float lastZ;

    private static final int SHAKE_THRESHOLD = 400;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 300) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = event.values[0];
                y = event.values[1];
                z = event.values[2];

                float speed = Math.abs(x+y+z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    lastShakeTime = lastUpdate;
                }
                lastX = x;
                lastY = y;
                lastZ = z;
            }
        }
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) { }

    public long getLastShakeTime() {
        return lastShakeTime;
    }
}
