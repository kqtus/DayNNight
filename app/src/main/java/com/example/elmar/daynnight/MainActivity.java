package com.example.elmar.daynnight;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

import com.example.elmar.daynnight.Listeners.IlluminanceSensorListener;
import com.example.elmar.daynnight.Listeners.ShakeSensoListener;

public class MainActivity extends Activity {
    private GameView gameView;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

        IlluminanceSensorListener lightListener = new IlluminanceSensorListener();
        ShakeSensoListener shakeListener = new ShakeSensoListener();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor shake = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(lightListener, light, SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(shakeListener, shake, SensorManager.SENSOR_DELAY_UI);

        gameView.setScreenSize(getScreenSize());
        gameView.resume();
        gameView.setLightSensor(lightListener);
        gameView.setShakeSensor(shakeListener);
        gameView.setMaxLightRange(light.getMaximumRange());
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Point screenSize = getScreenSize();

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int temp = screenSize.y;
            screenSize.y = screenSize.x;
            screenSize.x = temp;
        }

        gameView.setScreenSize(screenSize);
    }

    protected Point getScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        return size;
    }
}
