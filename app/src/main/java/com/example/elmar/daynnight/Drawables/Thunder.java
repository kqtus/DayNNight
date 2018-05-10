package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.elmar.daynnight.R;

/**
 * Created by Marek on 09.05.2018.
 */

public class Thunder extends GameDrawable {
    private Drawable thunderDrawable;
    private int hidingSpeed = 1;

    public Thunder(Context context) {
        super(context);
        thunderDrawable = getDrawable(R.drawable.ic_thunderbolt);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPosition().x, getPosition().y);
        thunderDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void update() {
        int currentAlpha = thunderDrawable.getAlpha();
        if (currentAlpha > 0) {
            currentAlpha = Math.max(0, currentAlpha - hidingSpeed);
        }

        setAlpha(currentAlpha);
    }

    @Override
    public void setAlpha(int alpha) {
        thunderDrawable.setAlpha(alpha);
    }

    @Override
    public void setSize(float size) {
        super.setSize(size);
        thunderDrawable.setBounds(0, 0, (int)size, (int)size);
    }

    public void setSize(float width, float height) {
        super.setSize(width);
        thunderDrawable.setBounds(0, 0, (int)width, (int)height);
    }

    public void show() {
        setAlpha(255);
    }

    public void setHidingSpeed(int speed) {
        this.hidingSpeed = speed;
    }
}
