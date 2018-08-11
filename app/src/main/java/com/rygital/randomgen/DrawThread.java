package com.rygital.randomgen;

import android.view.SurfaceHolder;

import com.rygital.randomgen.game.GameArray;

class DrawThread extends Thread {

    private GameArray game;
    private SurfaceHolder surfaceHolder;

    DrawThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        game = new GameArray(surfaceHolder);

    }

    void setRunning(boolean running) {

        game.setRunning(running);
    }

    @Override
    public void run() {
        game.init();
    }
}
