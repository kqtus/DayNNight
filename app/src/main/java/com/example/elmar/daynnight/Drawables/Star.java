package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import com.example.elmar.daynnight.R;

/**
 * Created by Marek on 09.05.2018.
 */

public class Star extends GameDrawable {
    private Drawable starDrawable;
    private int flickeringFrequency = 0;
    private float actualSize = 0.f;

    public Star(Context context) {
        super(context);
        starDrawable = getDrawable(R.drawable.ic_star);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPosition().x, getPosition().y);

        Rect initialBounds = starDrawable.getBounds();
        starDrawable.setBounds(0, 0, (int)actualSize, (int)actualSize);
        starDrawable.draw(canvas);
        starDrawable.setBounds(initialBounds);
        canvas.restore();
    }

    @Override
    public void update() {
        float percentage = 0.15f;

        if (actualSize > getMaxSize(percentage) || actualSize < getMinSize(percentage)) {
            flickeringFrequency *= -1;
        }

        actualSize += flickeringFrequency;
    }

    @Override
    public void setAlpha(int alpha) {
        starDrawable.setAlpha(alpha);
    }

    @Override
    public void setSize(float size) {
        super.setSize(size);
        starDrawable.setBounds(0, 0, (int)size, (int)size);
        this.actualSize = size;
    }

    public void setFlickeringFrequency(int frequency) {
        this.flickeringFrequency = frequency;
    }

    protected float getMaxSize(float percentage) {
        return getSize() + getSize() * percentage;
    }

    protected float getMinSize(float percentage) {
        return getSize() - getSize() * percentage;
    }
}
