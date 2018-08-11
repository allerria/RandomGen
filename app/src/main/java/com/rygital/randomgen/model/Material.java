package com.rygital.randomgen.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Material extends DrawableObject {

    public Material(int x, int y) {
        super(x, y);
    }

    @Override
    public void onUpdate() {
        x++;
    }

    @Override
    public void onDraw(Canvas canvas, Paint p) {
        canvas.drawRect(x, y, width + x, height + y, p);
    }
}
