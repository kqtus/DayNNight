package com.example.elmar.daynnight.Drawables;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Marek on 10.05.2018.
 */

public class Earth extends GameDrawable {
    private Paint paint;

    public Earth(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.argb(255, 31, 145, 71));
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(getPosition().x, getPosition().y, getSize(), paint);
    }

    @Override
    public void update() { }

    @Override
    public void setAlpha(int alpha) { paint.setAlpha(alpha); }
}
