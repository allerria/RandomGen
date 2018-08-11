package com.rygital.randomgen;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawField drawField = new DrawField(this);

        setContentView(drawField);
    }


}
