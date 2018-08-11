package com.rygital.randomgen.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.rygital.randomgen.App;
import com.rygital.randomgen.ClickEvent;
import com.rygital.randomgen.DimUtils;
import com.rygital.randomgen.Rectangle;
import com.rygital.randomgen.utils.ClickType;
import com.rygital.randomgen.utils.Colors;
import com.rygital.randomgen.utils.Materials;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.rygital.randomgen.utils.Materials.GRASS;
import static com.rygital.randomgen.utils.Materials.LAVA;
import static com.rygital.randomgen.utils.Materials.MUD;
import static com.rygital.randomgen.utils.Materials.OBSIDIAN;
import static com.rygital.randomgen.utils.Materials.SAND;
import static com.rygital.randomgen.utils.Materials.STONE;
import static com.rygital.randomgen.utils.Materials.WATER;

public class GameArray {
    public int columnCount;
    public int rowCount;

    private Canvas canvas;
    private Paint paint;

    private Integer[][] objects;

    private boolean running = false;

    private SurfaceHolder surfaceHolder;

    private int prevX = 0;
    private int prevY = 0;

    private long lastTouchedTime = 0;

    private int currentMaterialType = 1;

    private final int FRAME_TIME = 32;

    private Random random;

    public GameArray(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();

        DimUtils dimUtils = App.instance.dimUtils;
        columnCount = dimUtils.getColumnCount();
        rowCount = dimUtils.getRowCount();
        objects = new Integer[columnCount][rowCount];

        random = new Random();
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

            long delta = System.currentTimeMillis() - time;
            if (delta < FRAME_TIME) {
                try {
                    TimeUnit.MILLISECONDS.sleep(FRAME_TIME - delta);
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
                changeState(i, j);
            }
        }
    }

    private void moveDown(int i, int j) {
        if (j - 1 >= 0) {
//          just down if nothing
            if (objects[i][j] == null && objects[i][j - 1] != null) {
                objects[i][j] = objects[i][j - 1];
                objects[i][j - 1] = null;

            }
//          stone down
            else if (objects[i][j] != null && objects[i][j - 1] != null && objects[i][j] != OBSIDIAN
                    && (objects[i][j - 1] == STONE)) {
                int type = objects[i][j];
                objects[i][j] = objects[i][j - 1];
                objects[i][j - 1] = type;

            }
//          obsidian down
            else if (objects[i][j] != null && objects[i][j - 1] != null && objects[i][j] != STONE
                    && (objects[i][j - 1] == OBSIDIAN)) {
                int type = objects[i][j];
                objects[i][j] = objects[i][j - 1];
                objects[i][j - 1] = type;
            }
//          grass down
            else if (objects[i][j] != null && objects[i][j - 1] != null && objects[i][j] == WATER
                    && (objects[i][j - 1] == GRASS)) {
                int type = objects[i][j];
                objects[i][j] = objects[i][j - 1];
                objects[i][j - 1] = type;
            }
//          liquid physics
            else if (objects[i][j] != null
                    && (objects[i][j] == WATER || objects[i][j] == LAVA || objects[i][j] == MUD)) {
                if (i > 0 && i + 1 < columnCount - 1 && j < rowCount - 2) {
                    if (objects[i - 1][j] == null && objects[i - 1][j + 1] == null) {
                        objects[i - 1][j] = objects[i][j];
                        objects[i][j] = null;
                    } else if (objects[i + 1][j] == null && objects[i + 1][j + 1] == null) {
                        objects[i + 1][j] = objects[i][j];
                        objects[i][j] = null;
                    }
                }
            }
        }
    }

    private void changeState(int i, int j) {
        if (i - 1 > 0) state(i - 1, j, i, j);
        if (i + 1 < columnCount - 1) state(i + 1, j, i, j);
        if (j - 1 > 0) state(i, j - 1, i, j);
        if (j + 1 < rowCount - 1) state(i, j + 1, i, j);
    }

    private void state(int i, int j, int i1, int j1) {
        if (objects[i][j] != null && objects[i1][j1] != null) {
            if ((objects[i][j] == MUD || objects[i][j] == SAND) && objects[i1][j1] == LAVA) {
                objects[i][j] = STONE;
                objects[i1][j1] = STONE;
            }

            if (objects[i][j] == WATER && objects[i1][j1] == LAVA) {
                objects[i][j] = OBSIDIAN;
                objects[i1][j1] = OBSIDIAN;
            }

            if (objects[i][j] == GRASS && objects[i1][j1] == LAVA) {
                objects[i][j] = LAVA;
                objects[i1][j1] = LAVA;
            }

            if (objects[i][j] == SAND && objects[i1][j1] == WATER) {
                objects[i][j] = MUD;
                objects[i1][j1] = MUD;
            }
        }
    }

    private void render() {
        canvas.drawColor(Colors.lightBlue);

        drawToolbar();

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                if (objects[i][j] != null) {
                    setColor(i, j);
                    Rectangle m = App.instance.dimUtils.getMaterial(j, i);
                    canvas.drawRect(m.left, m.top, m.right, m.bottom, paint);
                }
            }
        }
    }

    private void drawToolbar() {
        DimUtils dimUtils = App.instance.dimUtils;

        int width = dimUtils.getToolbarPxWidth();

        paint.setColor(Color.DKGRAY);
        canvas.drawRect(0, 0, width, canvas.getHeight(), paint);

        for (int i = 0; i < 6; i++) {
            paint.setColor(Materials.COLORS[i + 1][0]);
            canvas.drawCircle(dimUtils.getIconCenter(i).x, dimUtils.getIconCenter(i).y, dimUtils.getIconPxRadius(),
                    paint);
        }

    }

    private void setColor(int i, int j) {
        paint.setColor(Materials.COLORS[objects[i][j]][random.nextInt(2) + 1]);
    }

    public void touch(int x, int y, int action) {
        ClickEvent clickEvent = App.instance.dimUtils.getClickEvent(y, x);
        Log.d("TAG", MotionEvent.actionToString(action));
        log(prevX, x, prevY, y);
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                prevX = x;
                prevY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (clickEvent.clickType == ClickType.CREATE_MATERIAL_CLICK) {
                    ClickEvent prevClickEvent = App.instance.dimUtils.getClickEvent(prevY, prevX);
                    prevY = y;
                    prevX = x;
                    log(prevClickEvent.column, clickEvent.column, prevClickEvent.row, clickEvent.row);
                    createObjects(prevClickEvent.column, prevClickEvent.row, clickEvent.column, clickEvent.row);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (clickEvent.clickType == ClickType.CREATE_MATERIAL_CLICK) {
                    ClickEvent prevClickEvent = App.instance.dimUtils.getClickEvent(prevY, prevX);
                    log(prevClickEvent.column, clickEvent.column, prevClickEvent.row, clickEvent.row);
                    createObjects(prevClickEvent.column, prevClickEvent.row, clickEvent.column, clickEvent.row);
                } else {
                    currentMaterialType = clickEvent.materialType;
                }
                break;
            }
        }
        lastTouchedTime = System.currentTimeMillis();
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void createObject(int posX, int posY) {
        if (posX >= 0 && posY >= 0 && posX < columnCount && posY < rowCount && objects[posX][posY] == null) {
            objects[posX][posY] = currentMaterialType;
        }
    }

    public void createObjects(int startPosX, int startPosY, int endPosX, int endPosY) {
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
        for (int posX = startPosX; posX <= endPosX; posX++) {
            int posY = startPosY + ((int) ((double) (posX - startPosX) / diffX * diffY));
            createObject(posX, posY);
        }
    }

    private void log(int startX, int endX, int startY, int endY) {
        Log.d("TAG", String.valueOf(startX) + " " + String.valueOf(endX) + " " + String.valueOf(startY) + " " + String.valueOf(endY));
    }
}
