package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/**
 * Created by elmar on 22.04.2018.
 */

public class Sun extends GameDrawable {
    private Paint paint;
    private int direction = 1;
    private float actualSize = 0.f;

    public Sun(Context context) {
        super(context);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 244, 197, 66));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(getPosition().x, getPosition().y, actualSize, paint);
    }

    @Override
    public void update() {
        float percentage = 0.15f;

        if (actualSize > getMaxSize(percentage) || actualSize < getMinSize(percentage)) {
            direction *= -1;
        }
        actualSize += direction;
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setSize(float size) {
        super.setSize(size);
        this.actualSize = size;
    }

    protected float getMaxSize(float percentage) {
        return getSize() + getSize() * percentage;
    }

    protected float getMinSize(float percentage) {
        return getSize() - getSize() * percentage;
    }
}
