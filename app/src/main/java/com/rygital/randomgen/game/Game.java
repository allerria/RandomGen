//package com.rygital.randomgen.game;
//
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.Log;
//import android.view.SurfaceHolder;
//
//import com.rygital.randomgen.model.DrawableObject;
//import com.rygital.randomgen.model.Material;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Game {
//    public static final int SIZE_X = 100;
//    public static final int SIZE_Y = 100;
//
//    private Canvas canvas;
//    private Paint paint;
//
////    private List<DrawableObject> drawableObjects;
//    private DrawableObject[][] drawableObjectsArray = new DrawableObject[SIZE_X][SIZE_Y];
//
//
//    private boolean running = false;
//
//    private SurfaceHolder surfaceHolder;
//
//    public Game(SurfaceHolder surfaceHolder) {
//        this.surfaceHolder = surfaceHolder;
//
////        drawableObjects = new ArrayList<>();
//
//        paint = new Paint();
////        drawableObjects.add(new Material(0, 0));
//
//        drawableObjectsArray[0][0] = new Material(0, 0);
//    }
//
//    public void init() {
//        while (running) {
//
//            canvas = null;
//            try {
//                canvas = surfaceHolder.lockCanvas(null);
//                if (canvas == null) continue;
//
//                update();
//                render();
//
//            } finally {
//                if (canvas != null) {
//                    surfaceHolder.unlockCanvasAndPost(canvas);
//                }
//            }
//        }
//    }
//
//    public void update() {
//        Log.d("TAG", "update!");
//
//        for (DrawableObject drawableObject: drawableObjects) {
//            drawableObject.onUpdate();
//        }
//    }
//
//    public void render() {
//        Log.d("TAG", "render!");
//        paint.setColor(Color.RED);
//
//        canvas.drawColor(Color.GREEN);
//        for (DrawableObject drawableObject: drawableObjects) {
//            drawableObject.onDraw(canvas, paint);
//        }
//    }
//
//    public void setRunning(boolean running) {
//        this.running = running;
//    }
//}
