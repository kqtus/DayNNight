package com.example.elmar.daynnight.Listeners;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by elmar on 22.04.2018.
 */

public class IlluminanceSensorListener implements SensorEventListener {
    private float illuminancePercentage;

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        illuminancePercentage = event.values[0];
    }

    public float getIlluminancePercetage() {
        return illuminancePercentage;
    }
}
