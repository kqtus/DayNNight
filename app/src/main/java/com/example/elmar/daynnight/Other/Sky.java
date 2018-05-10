package com.example.elmar.daynnight.Other;

import android.graphics.Color;

/**
 * Created by Marek on 08.05.2018.
 */

class Col {
    public int a;
    public int r;
    public int g;
    public int b;

    public Col() { }

    public Col(int value) {
        setValue(value);
    }

    public void setValue(int value) {
        a = (value >> 24) & 0xff;
        r = (value >> 16) & 0xff;
        g = (value >> 8) & 0xff;
        b = (value) & 0xff;
    }

    public int getValue() {
        return Color.argb(a, r, g, b);
    }

    public int shiftBy(Col delta, float percent) {
        Col ret = new Col(getValue());
        ret.a += delta.a * percent;
        ret.r += delta.r * percent;
        ret.g += delta.g * percent;
        ret.b += delta.b * percent;

        return ret.getValue();
    }
}

public class Sky {

    private static int nightColor = Color.argb(255, 21, 3, 4);
    private static int dayColor = Color.argb(255, 94, 174, 255);
    private static int colorDelta;

    static {
        Col nc = new Col(nightColor);
        Col dc = new Col(dayColor);

        colorDelta = Color.argb(
                dc.a - nc.a,
                dc.r - nc.r,
                dc.g - nc.g,
                dc.b - nc.b);
    }

    public static int GetNightColor() {
        return nightColor;
    }

    public static int GetDayColor() {
        return dayColor;
    }

    public static int GetBlendedColor(float percentage) {
        Col nc = new Col(nightColor);
        Col dt = new Col(colorDelta);
        return nc.shiftBy(dt, percentage);
    }
}
