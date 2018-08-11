package com.rygital.randomgen.utils;

import android.graphics.Color;

public class Materials {

    public static int[][] COLORS = new int[][]{
             { 0, 0, 0 },
    /*WATER*/{ Color.rgb(33, 150, 243), Color.rgb(66, 165, 245), Color.rgb(100, 181, 246)},
    /*LAVA*/ { Color.rgb(244, 67, 54), Color.rgb(255, 111, 0), Color.rgb(244, 81, 30)},
    /*STONE*/{ Color.rgb(96, 125, 139), Color.rgb(94, 123, 137), Color.rgb(98, 127, 141)},
    /*GRASS*/{ Color.rgb(124, 179, 66), Color.rgb(139, 195, 74), Color.rgb(156, 204, 101)},
    /*SAND*/ { Color.rgb(255, 213, 79), Color.rgb(255, 214, 69), Color.rgb(255, 225, 59)},
    /*MUD*/  { Color.rgb(121, 85, 72), Color.rgb(110, 75, 62), Color.rgb(100, 65, 52)}
    };

    public static int NOTHING = 0;
    public static int WATER = 1;
    public static int LAVA = 2;
    public static int STONE = 3;
    public static int GRASS = 4;
    public static int SAND = 5;
    public static int MUD = 6;

}
