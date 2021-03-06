package com.rygital.randomgen;

import android.view.SurfaceHolder;

import com.rygital.randomgen.game.GameArray;

class DrawThread extends Thread {

    private GameArray game;

    DrawThread(SurfaceHolder surfaceHolder) {
        game = new GameArray(surfaceHolder);

    }

    void setRunning(boolean running) {
        game.setRunning(running);
    }

    void touch(int x, int y, int action) {
        game.touch(x, y, action);
    }

    @Override
    public void run() {
        game.runMainLoop();
    }
}
