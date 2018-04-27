package com.example.elmar.daynnight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.elmar.daynnight.Listeners.IlluminanceSensorListener;

public class MainActivity extends Activity {
    private GameView gameView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        IlluminanceSensorListener lightListener = new IlluminanceSensorListener();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_UI);

        gameView.setScreenSize(getScreenSize());
        gameView.resume();
        gameView.setLightSensor(lightListener);
        gameView.setMaxLightRange(light.getMaximumRange());
    }

    protected Point getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
