package com.example.elmar.daynnight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.elmar.daynnight.Drawables.Cloud;
import com.example.elmar.daynnight.Drawables.Earth;
import com.example.elmar.daynnight.Drawables.GameDrawable;
import com.example.elmar.daynnight.Drawables.Moon;
import com.example.elmar.daynnight.Drawables.Star;
import com.example.elmar.daynnight.Drawables.Sun;
import com.example.elmar.daynnight.Drawables.Thunder;
import com.example.elmar.daynnight.Listeners.IlluminanceSensorListener;
import com.example.elmar.daynnight.Listeners.ShakeSensoListener;
import com.example.elmar.daynnight.Other.Sky;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by elmar on 22.04.2018.
 */

public class GameView extends SurfaceView implements Runnable {
    Thread gameThread;
    SurfaceHolder holder;
    volatile boolean isPlaying;

    protected Canvas canvas;
    protected Paint paint;
    protected int screenWidth;
    protected int screenHeight;
    protected float range;

    protected List<GameDrawable> drawables;
    protected IlluminanceSensorListener lightSensor;
    protected ShakeSensoListener shakeSensor;

    protected long lastShakeTime;

    protected final Point sunVisiblePos;
    protected final Point sunHiddenPos;
    protected Point sunCurrentPos;

    protected final Point moonVisiblePos;
    protected final Point moonHiddenPos;
    protected Point moonCurrentPos;

    protected Point earthPos;

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        paint = new Paint();
        drawables = new LinkedList<>();

        sunVisiblePos = new Point(120, 120);
        sunHiddenPos = new Point(0, 0);
        sunCurrentPos = new Point(sunVisiblePos);

        moonVisiblePos = new Point(100, 100);
        moonHiddenPos = new Point(-100, -100);
        moonCurrentPos = new Point(moonHiddenPos);

        earthPos = new Point(0, 0);
    }

    @Override
    public void run() {
        createMap();

        while (isPlaying) {
            update();
            draw();
        }
    }

    private void createMap() {
        Moon moon = new Moon(getContext());
        moon.setPosition(moonCurrentPos);
        moon.setSize(420.0f);
        drawables.add(moon);

        Sun sun = new Sun(getContext());
        sun.setPosition(sunCurrentPos);
        sun.setSize(220.f);
        drawables.add(sun);

        Random rnd = new Random();

        for (int i = 0; i < 40; i++) {
            Star star = new Star(getContext());
            int scaleFactor = 1 + rnd.nextInt(4);

            star.setSize(60.0f / scaleFactor);
            star.setPosition(getRandomPosOnSky());
            star.setFlickeringFrequency(scaleFactor / 2);
            drawables.add(star);
        }

        for (int i = 0; i < 14; i++) {
            Cloud cloud = new Cloud(getContext());
            int scaleFactor = 1 + rnd.nextInt(4);

            cloud.setPosition(getRandomPosOnSky());
            cloud.setSize(350.0f / scaleFactor, 230.0f / scaleFactor);
            cloud.setSpeed(5 - scaleFactor);
            drawables.add(cloud);
        }

        Earth earth = new Earth(getContext());
        //earth.setPosition();
        earth.setSize(1000.f);
        drawables.add(earth);

        Thunder thunder = new Thunder(getContext());
        thunder.setPosition(getRandomPosOnSky());
        thunder.setSize(350.f, 450.f);
        thunder.setHidingSpeed(2);
        drawables.add(thunder);
    }

    private Point getRandomPosOnSky() {
        Random rnd = new Random();
        int yPos = (int)(screenHeight * 0.1) + rnd.nextInt((int)(screenHeight * 0.6));
        int xPos = rnd.nextInt(screenWidth * 2);

        return new Point(xPos, yPos);
    }

    public void update() {
        for (GameDrawable gd : drawables) {
            gd.update();

            float percentage = (int)lightSensor.getIlluminancePercetage();
            if (range > 0) {
                percentage /= range;
            }

            if (gd instanceof Star) {
                gd.setAlpha((int)((1.0f - percentage) * 255));
            }

            if (gd instanceof Cloud) {
                Point pos = gd.getPosition();

                if (pos.x > screenWidth) {
                    pos.x = - (int)gd.getSize();
                    gd.setPosition(pos);
                }

                gd.setAlpha((int)(percentage * 255));
            }

            if (gd instanceof Moon) {
                Point delta = new Point(
                        (int)((moonVisiblePos.x - moonHiddenPos.x) * -percentage),
                        (int)((moonVisiblePos.y - moonHiddenPos.y) * -percentage)
                );
                moonCurrentPos.x = moonVisiblePos.x + delta.x;
                moonCurrentPos.y = moonVisiblePos.y + delta.y;

                gd.setPosition(moonCurrentPos);
                gd.setAlpha((int)((1.0f - percentage) * 255));
            }

            if (gd instanceof Sun) {
                Point delta = new Point(
                        (int)((sunVisiblePos.x - sunHiddenPos.x) * percentage),
                        (int)((sunVisiblePos.y - sunHiddenPos.y) * percentage)
                );
                sunCurrentPos.x = sunHiddenPos.x + delta.x;
                sunCurrentPos.y = sunHiddenPos.y + delta.y;

                gd.setPosition(sunCurrentPos);
            }

            if (gd instanceof Thunder) {
                long sensorLastShakeTime = shakeSensor.getLastShakeTime();
                if (sensorLastShakeTime > lastShakeTime) {
                    gd.setPosition(getRandomPosOnSky());
                    ((Thunder) gd).show();

                    lastShakeTime = sensorLastShakeTime;
                }
            }

            if (gd instanceof Earth) {
                gd.setPosition(earthPos);
            }
        }
    }

    protected void forceUpdateDrawables() {
        sunHiddenPos.set(screenHeight - 30, screenWidth / 2);
        earthPos.set(screenWidth / 2, screenHeight + 700);
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();

            if (canvas == null) {
                return;
            }

            float percentage = (int)lightSensor.getIlluminancePercetage();

            if (range > 0) {
                percentage /= range;
            }

            canvas.drawColor(Sky.GetBlendedColor(percentage));

            for (GameDrawable gd : drawables) {
                gd.draw(canvas);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setScreenSize(Point size) {
        screenWidth = size.x;
        screenHeight = size.y;

        forceUpdateDrawables();
    }

    public void setLightSensor(IlluminanceSensorListener sensor) {
        lightSensor = sensor;
    }

    public void setShakeSensor(ShakeSensoListener sensor) {
        shakeSensor = sensor;
    }

    public void setMaxLightRange(float range) {
        this.range = range;
    }
}
