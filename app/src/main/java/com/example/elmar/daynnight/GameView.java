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
import com.example.elmar.daynnight.Drawables.GameDrawable;
import com.example.elmar.daynnight.Drawables.Sun;
import com.example.elmar.daynnight.Listeners.IlluminanceSensorListener;

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

    public GameView(Context context) {
        super(context);

        holder = getHolder();
        paint = new Paint();
        drawables = new LinkedList<>();
    }

    @Override
    public void run() {
        createMap();
        while (isPlaying) {
            long startFrameTime = System.currentTimeMillis();
            update();
            draw();
        }
    }

    private void createMap() {
        Sun sun = new Sun(getContext());
        sun.setPosition(new Point(10, 10));
        sun.setSize(120.f);
        drawables.add(sun);
        Random rnd = new Random();

        for (int i = 0; i < 5; i++) {
            Cloud cloud = new Cloud(getContext());
            int xPos = (int)(screenHeight * 0.1) + rnd.nextInt((int)(screenHeight * 0.2));
            int yPos = rnd.nextInt(screenWidth * 2);

            cloud.setPosition(new Point(yPos, xPos));
            int scaleFactor = 1 + rnd.nextInt(4);
            cloud.setSize(350.0f / scaleFactor, 230.0f / scaleFactor);
            drawables.add(cloud);
        }
    }

    public void update() {
        for (GameDrawable gd : drawables) {
            gd.update();

            if (gd instanceof Cloud) {
                Point pos = gd.getPosition();
                if (pos.x > screenWidth) {
                    pos.x = - (int)gd.getSize();
                }

                gd.setPosition(new Point(pos.x + 1, pos.y));
            }
        }
    }

    public void draw() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            int lightPercentage = 255 * (int)lightSensor.getIlluminancePercetage();

            if (range > 0) {
                lightPercentage /= range;
            }

            canvas.drawColor(Color.argb(255, lightPercentage, lightPercentage, 182));
            //paint.setColor(Color.argb(255, lightPercentage, lightPercentage, 0));
            //paint.setTextSize(45);
            //canvas.drawText("FPS:" + fps, 20, 40, paint);

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
    }

    public void setLightSensor(IlluminanceSensorListener sensor) {
        lightSensor = sensor;
    }

    public void setMaxLightRange(float range) {
        this.range = range;
    }

    // 0 - 12:00
    // 1 - 15:00
    // 2 - 18:00
    // 3 - 21:00
    // 4  - 24:00
    public void setDayPhase(int phase) {
        // Translate sun to calculated point
        // Translate moon to calculated point
        // Make clouds transparent accordingly
        // Make stars visible accordingly
        // Change background colours accordingly
    }
}
