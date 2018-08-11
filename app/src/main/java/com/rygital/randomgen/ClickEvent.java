package com.rygital.randomgen;

import com.rygital.randomgen.utils.ClickType;

public class ClickEvent {
    private int clickType;
    private int materialType;
    public int row;
    public int column;


    public ClickEvent(int row, int column) {
        this.clickType = ClickType.CREATE_MATERIAL_CLICK;
        this.row = row;
        this.column = column;
    }

    public ClickEvent(int materialType) {
        this.clickType = ClickType.ICON_CLICK;
        this.materialType = materialType;
    }
}
