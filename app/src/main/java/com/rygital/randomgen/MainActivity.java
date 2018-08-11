package com.rygital.randomgen;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;

public class MainActivity extends Activity {

    DrawField drawField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated

        App.instance.dimUtils = new DimUtils(this, height, width);

        drawField = new DrawField(this);
        setContentView(drawField);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = ((int) event.getX());
        int y = ((int) event.getY());
        int action = event.getAction();
        drawField.touch(x, y, action);
        return super.onTouchEvent(event);
    }
}
