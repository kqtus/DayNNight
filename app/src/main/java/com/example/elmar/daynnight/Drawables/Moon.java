package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.elmar.daynnight.R;

/**
 * Created by Marek on 09.05.2018.
 */

public class Moon extends GameDrawable {
    private Drawable moonDrawable;

    public Moon(Context context) {
        super(context);
        moonDrawable = getDrawable(R.drawable.ic_moon);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(getPosition().x, getPosition().y);
        moonDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    public void update() {}

    @Override
    public void setAlpha(int alpha) {
        moonDrawable.setAlpha(alpha);
    }

    @Override
    public void setSize(float size) {
        moonDrawable.setBounds(0, 0, (int)size, (int)size);
        super.setSize(size);
    }
}
