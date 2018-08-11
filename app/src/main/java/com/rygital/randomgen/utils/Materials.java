package com.rygital.randomgen.utils;

import android.graphics.Color;

public class Materials {

    public static int[][] COLORS = new int[][]{
            { 0, 0, 0 },
            { Color.rgb(33, 150, 243), Color.rgb(66, 165, 245), Color.rgb(100, 181, 246)},
            { Color.rgb(244, 67, 54), Color.rgb(255, 111, 0), Color.rgb(244, 81, 30)},
            { Color.rgb(158, 158, 158), Color.rgb(117, 117, 117), Color.rgb(224, 224, 224)},
            { Color.rgb(124, 179, 66), Color.rgb(76, 175, 80), Color.rgb(174, 234, 0)},
            { Color.rgb(255, 213, 79), Color.rgb(255, 214, 0), Color.rgb(255, 235, 59)},
            { Color.rgb(121, 85, 72), Color.rgb(93, 64, 55), Color.rgb(62, 39, 35)}
    };

    public static int NOTHING = 0;
    public static int WATER = 1;
    public static int LAVA = 2;
    public static int STONE = 3;
    public static int GRASS = 4;
    public static int SAND = 5;
    public static int MUD = 6;

}
