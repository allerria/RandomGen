package com.rygital.randomgen;

import android.content.Context;
import android.content.res.Resources;
import android.os.Parcelable;

public class DimUtils {

    private Context context;
    private int rowCount;
    private int columnCount;

    public DimUtils(Context context, int height, int width) {
        this.context = context;

        rowCount = (int)pxToDp(context, height) / MATERIAL_SIZE;
        columnCount = (int)pxToDp(context, width) / MATERIAL_SIZE;
    }

    public int getRowCount() {
        return rowCount;
    }
    public int getColumnCount() {
        return columnCount;
    }

    public static final int MATERIAL_SIZE = 20;//dp
    public static final int TOOLBAR_SIZE = 56;//dp

    public Material getMaterial(int row, int column) {
        int pxSize = (int)dpToPx(context, MATERIAL_SIZE);
        int left = column * pxSize;
        int top = row * pxSize;
        return new Material(left, left+pxSize, top, top+pxSize);
    }

    public Cell getClickedCell(int x, int y) {
        int pxSize = (int)dpToPx(context, MATERIAL_SIZE);
        return new Cell(x/pxSize, y/pxSize);
    }

    public static float dpToPx(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static float pxToDp(final Context context, final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}

class Cell {
    int row;
    int column;

    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
