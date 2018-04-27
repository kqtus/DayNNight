package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;

/**
 * Created by elmar on 22.04.2018.
 */

public abstract class GameDrawable {
    private float size;
    private Point position;
    private Context context;

    public GameDrawable(Context context) {
        this.context = context;
        size = 0.f;
        position = new Point(0, 0);
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    protected Context getContext() {
        return context;
    }

    protected Drawable getDrawable(int id) {
        return context.getDrawable(id);
    }

    abstract public void draw(Canvas canvas);
    abstract public void update();
    abstract public void setAlpha(int alpha);
}
