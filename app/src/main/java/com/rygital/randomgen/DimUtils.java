package com.rygital.randomgen;

import android.content.Context;
import android.util.Log;

import com.rygital.randomgen.game.Point;

public class DimUtils {

    private Context context;
    private int rowCount;
    private int columnCount;

    private int cellPxSize;
    private int toolbarPxWidth;
    private int iconPxRadius;
    private Point[] points = new Point[6];
    int iconCellHeight;

    public DimUtils(Context context, int height, int width) {
        this.context = context;

        cellPxSize = (int) dpToPx(MATERIAL_SIZE);

        rowCount = (int) pxToDp(height) / MATERIAL_SIZE;
        columnCount = (int) pxToDp(width) / MATERIAL_SIZE;
        Log.d("DimUtils", String.format("row %s; column %s", rowCount, columnCount));

        toolbarPxWidth = (int) dpToPx(TOOLBAR_SIZE);
        iconPxRadius = (int) dpToPx(ICON_SIZE / 2);

        iconCellHeight = height / points.length;
        for (int i = 0; i < points.length; i++) {
            int y = iconCellHeight * i + height / (points.length * 2);
            points[i] = new Point(toolbarPxWidth / 2, y);
        }

    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public static final int MATERIAL_SIZE = 10;//dp
    public static final int TOOLBAR_SIZE = 56;//dp
    public static final int ICON_SIZE = 24;//dp

    public Rectangle getMaterial(int row, int column) {
        int left = column * cellPxSize + toolbarPxWidth;
        int top = row * cellPxSize;
        return new Rectangle(left, left + cellPxSize, top, top + cellPxSize);
    }

    public Point getIconCenter(int index) {
        return points[index];
    }

    public int getIconPxRadius() {
        return iconPxRadius;
    }

    public int getToolbarPxWidth() {
        return toolbarPxWidth;
    }

    public ClickEvent getClickEvent(int x, int y) {
        if (y <= toolbarPxWidth) {
            return new ClickEvent(x / iconCellHeight + 1);
        } else {
            return new ClickEvent(x / cellPxSize, (y - toolbarPxWidth) / cellPxSize);
        }
    }

    public float dpToPx(final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public float pxToDp(final float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }
}

