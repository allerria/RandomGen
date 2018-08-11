package com.rygital.randomgen.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class DrawableObject {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public DrawableObject(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 100;
        this.height = 100;
    }

    public abstract void onUpdate();
    public abstract void onDraw(Canvas canvas, Paint p);
}
