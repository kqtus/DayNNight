package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

import com.example.elmar.daynnight.R;

/**
 * Created by elmar on 22.04.2018.
 */

public class Cloud extends GameDrawable {
    private Paint paint;
    private int direction = 1;
    private int rscId = 0;
    private int speed = 0;
    private Drawable cloudDrawable;

    public Cloud(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 255, 255, 255));
        cloudDrawable = getDrawable(R.drawable.ic_cloud);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPosition().x, getPosition().y);
        cloudDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void update() {
        Point pos = getPosition();
        pos.x += speed;
        setPosition(pos);
    }

    @Override
    public void setAlpha(int alpha) {
        cloudDrawable.setAlpha(alpha);
    }

    @Override
    public void setSize(float size) {
        cloudDrawable.setBounds(0, 0, (int)size, (int)size);
        super.setSize(size);
    }

    public void setSize(float width, float height) {
        cloudDrawable.setBounds(0, 0, (int)width, (int)height);
        super.setSize(width);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
