package com.rygital.randomgen.game;

import android.app.Application;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.rygital.randomgen.App;
import com.rygital.randomgen.Cell;
import com.rygital.randomgen.DimUtils;
import com.rygital.randomgen.Material;
import com.rygital.randomgen.utils.Colors;

import java.util.concurrent.TimeUnit;

public class GameArray {
    public int columnCount;
    public int rowCount;

    private Canvas canvas;
    private Paint paint;

    private Integer[][] drawableObjectsArray;

    private boolean running = false;

    private SurfaceHolder surfaceHolder;

    private int prevTouchedX = 0;
    private int prevTouchedY = 0;
    private long lastTouchedTime = 0;

    private int currentMaterialType = 0;

    public GameArray(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();

        DimUtils dimUtils = App.instance.dimUtils;
        columnCount = dimUtils.getColumnCount();
        rowCount = dimUtils.getRowCount();
        drawableObjectsArray = new Integer[columnCount][rowCount];


        drawableObjectsArray[1][0] = 1;
        drawableObjectsArray[1][2] = 1;
        drawableObjectsArray[3][2] = 1;
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
                try {
                    TimeUnit.MILLISECONDS.sleep(500 - (lastTime - time));
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
        Log.d("TAG", MotionEvent.actionToString(action));
        switch (action) {
            case MotionEvent.ACTION_MOVE: {
//                if (System.currentTimeMillis() - lastTouchedTime > 16) {
//                    break;
//                }
//                Log.d("TAG", String.valueOf(prevTouchedX) + " " + String.valueOf(x));
//                Log.d("TAG", String.valueOf(prevTouchedY) + " " + String.valueOf(y));
//                Cell prevCell = App.instance.dimUtils.getClickedCell(prevTouchedY, prevTouchedX);
//                Cell cell = App.instance.dimUtils.getClickedCell(y, x);
//                createObjects(prevCell.column, prevCell.row, cell.column, cell.row);
                prevTouchedX = x;
                prevTouchedY = y;
                break;
            }
            case MotionEvent.ACTION_DOWN: {
                Log.d("TAG", String.valueOf(x) + " " + String.valueOf(y));
                Cell cell = App.instance.dimUtils.getClickedCell(y, x);
                createObject(cell.column, cell.row);
            }
        }
        lastTouchedTime = System.currentTimeMillis();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void createObject(int posX, int posY) {
        if (drawableObjectsArray[posX][posY] == null) {
            drawableObjectsArray[posX][posY] = currentMaterialType;
        }
    }

    public void createObjects(int startPosX, int endPosX, int startPosY, int endPosY) {
        if (startPosX > endPosX) {
            int tmp;
            tmp = startPosX;
            startPosX = endPosX;
            endPosX = tmp;
        }
        if (startPosY > endPosY) {
            int tmp;
            tmp = startPosY;
            startPosY = endPosY;
            endPosY = tmp;
        }
        int diffY = endPosY - startPosY + 1;
        int diffX = endPosX - startPosX + 1;
        for (int posX = startPosX; posX < endPosX; posX++) {
            int posY = ((int) ((double) (posX - startPosX) / diffX * diffY));

            createObject(posX, posY);
        }
    }
}
