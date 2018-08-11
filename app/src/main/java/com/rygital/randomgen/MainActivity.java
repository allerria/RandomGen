package com.rygital.randomgen;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.rygital.randomgen.game.GameArray;

public class MainActivity extends Activity {

    DrawField drawField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
