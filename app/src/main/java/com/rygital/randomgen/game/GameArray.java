package com.rygital.randomgen.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

import com.rygital.randomgen.utils.Colors;

import java.util.concurrent.TimeUnit;

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

        drawableObjectsArray[4][0] = 1;
    }

    public void runMainLoop() {


        while (running) {
            long time = System.currentTimeMillis();

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

                long lastTime = System.currentTimeMillis();
                if (lastTime - time < 16) {
                    Log.d("TAG", "time: " + (lastTime - time));
                    try {
                        TimeUnit.MILLISECONDS.sleep(lastTime - time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void update() {
        for (int j = SIZE_Y - 1; j > 0; j--) {
            for (int i = 0; i < SIZE_X; i++) {
                moveDown(i, j);
            }
        }
    }

    private void moveDown(int i, int j) {
        if (j - 1 >= 0 && drawableObjectsArray[i][j] == null && drawableObjectsArray[i][j - 1] != null) {
            drawableObjectsArray[i][j] = drawableObjectsArray[i][j - 1];
            drawableObjectsArray[i][j - 1] = null;
        }
    }

    private void render() {
        paint.setColor(Colors.yellow);

        canvas.drawColor(Colors.lightBlue);

        for (int i = 0; i < SIZE_X; i++) {
            for (int j = 0; j < SIZE_Y; j++) {
                if (drawableObjectsArray[i][j] != null) {
                    canvas.drawRect(i, j, rectSize + i, rectSize + j, paint);
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
