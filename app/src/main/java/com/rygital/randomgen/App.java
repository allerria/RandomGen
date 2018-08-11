package com.rygital.randomgen;

import android.app.Application;

public class App extends Application {

    public static App instance;
    public DimUtils dimUtils;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }


}
