package com.rygital.randomgen.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import com.rygital.randomgen.model.DrawableObject;
import com.rygital.randomgen.model.Material;

public class GameArray {
    public static final int SIZE_X = 100;
    public static final int SIZE_Y = 100;

    public static final int rectSize = 100;

    private Canvas canvas;
    private Paint paint;

    private Integer[][] drawableObjectsArray = new Integer[SIZE_X][SIZE_Y];


    private boolean running = false;

    private SurfaceHolder surfaceHolder;

    public GameArray(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();

        drawableObjectsArray[0][0] = 1;
    }

    public void init() {
        while (running) {

            canvas = null;
            try {
                canvas = surfaceHolder.lockCanvas(null);
                if (canvas == null) continue;

                update();
                render();

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void update() {
        Log.d("TAG", "update!");

        for (int i = SIZE_X - 1; i >= 0; i--) {
            for (int j = SIZE_Y - 1; j >= 0; j--) {
                if (drawableObjectsArray[i][j] != null) {
                    drawableObjectsArray[i][j - 1] = drawableObjectsArray[i][j];
                    drawableObjectsArray[i][j] = null;
                    // update object
                }
            }
        }
    }

    public void render() {
        Log.d("TAG", "render!");
        paint.setColor(Color.RED);

        canvas.drawColor(Color.GREEN);

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (drawableObjectsArray[i][j] != null) {
                    canvas.drawRect(i*rectSize, j*rectSize, rectSize + i*rectSize, rectSize + j*rectSize, paint);
                    // draw
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
