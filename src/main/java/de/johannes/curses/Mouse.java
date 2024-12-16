package de.johannes.curses;

public class Mouse {

    public static final int BUTTON_1_CLICKED = 1;
    public static final int BUTTON_1_DOUBLE_CLICKED = 2;

    public static final int BUTTON_2_CLICKED = 3;
    public static final int BUTTON_2_DOUBLE_CLICKED = 4;

    public static final int BUTTON_3_CLICKED = 5;
    public static final int BUTTON_3_DOUBLE_CLICKED = 6;

    public static final int BUTTON_4_CLICKED = 7;
    public static final int BUTTON_4_DOUBLE_CLICKED = 8;

    public static final int OTHER = 10;

    public short deviceId;
    public int x,y;
    public int state;

}
