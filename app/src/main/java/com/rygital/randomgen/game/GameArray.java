package com.rygital.randomgen.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.rygital.randomgen.App;
import com.rygital.randomgen.DimUtils;
import com.rygital.randomgen.Material;
import com.rygital.randomgen.utils.Colors;

import java.util.concurrent.TimeUnit;

public class GameArray {
    public int columnCount = 100;
    public int rowCount = 100;

    private Canvas canvas;
    private Paint paint;

    private Integer[][] drawableObjectsArray;

    private boolean running = false;

    private SurfaceHolder surfaceHolder;

    public GameArray(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();

        DimUtils dimUtils = App.instance.dimUtils;
        columnCount = dimUtils.getColumnCount();
        rowCount = dimUtils.getRowCount();
        drawableObjectsArray = new Integer[columnCount][rowCount];



        drawableObjectsArray[1][0] = 1;
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
            }

            long lastTime = System.currentTimeMillis();
            if (lastTime - time < 500) {
                //Log.d("TAG", "time: " + (lastTime - time));
                try {
                    TimeUnit.MILLISECONDS.sleep(lastTime - time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void update() {
        for (int j = rowCount - 1; j > 0; j--) {
            for (int i = 0; i < columnCount; i++) {
                moveDown(i, j);
            }
        }
    }

    private void moveDown(int i, int j) {
        if (j - 1 >= 0 && drawableObjectsArray[i][j] == null && drawableObjectsArray[i][j - 1] != null) {

            Log.d("Update", i + " " + j);

            drawableObjectsArray[i][j] = drawableObjectsArray[i][j - 1];
            drawableObjectsArray[i][j - 1] = null;
        }
    }

    private void render() {
        paint.setColor(Colors.yellow);

        canvas.drawColor(Colors.lightBlue);

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                if (drawableObjectsArray[i][j] != null) {
                    Material m = App.instance.dimUtils.getMaterial(j, i);
                    Log.d("TAG draw", String.format("%s %s %s %s", m.left, m.top, m.right, m.bottom));
                    canvas.drawRect(m.left, m.top, m.right, m.bottom, paint);
                }
            }
        }
    }

    public void touch(int x, int y, int action) {
        switch (action) {

        }
        Log.d("TAG", String.valueOf(x) + " " + String.valueOf(y) + MotionEvent.actionToString(action));
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
