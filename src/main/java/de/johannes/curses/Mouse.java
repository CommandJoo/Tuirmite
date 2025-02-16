package de.johannes.curses;

public class Mouse {

    public static final int BUTTON1_PRESSED = 2;
    public static final int BUTTON1_RELEASED = 1;
    public static final int BUTTON1_CLICKED = 4;
    public static final int BUTTON1_DOUBLE_CLICKED = 8;
    public static final int BUTTON1_TRIPLE_CLICKED = 16;
    public static final int BUTTON2_PRESSED = 64;
    public static final int BUTTON2_RELEASED = 32;
    public static final int BUTTON2_CLICKED = 128;
    public static final int BUTTON2_DOUBLE_CLICKED = 256;
    public static final int BUTTON2_TRIPLE_CLICKED = 512;
    public static final int BUTTON3_PRESSED = 2048;
    public static final int BUTTON3_RELEASED = 1024;
    public static final int BUTTON3_CLICKED = 4096;
    public static final int BUTTON3_DOUBLE_CLICKED = 8192;
    public static final int BUTTON3_TRIPLE_CLICKED = 16384;
    public static final int BUTTON4_PRESSED = 65536;
    public static final int BUTTON4_RELEASED = 32768;
    public static final int BUTTON4_CLICKED = 131072;
    public static final int BUTTON4_DOUBLE_CLICKED = 262144;
    public static final int BUTTON4_TRIPLE_CLICKED = 524288;
    public static final int BUTTON_SHIFT = 67108864;
    public static final int BUTTON_CTRL = 33554432;
    public static final int BUTTON_ALT = 134217728;

    public short deviceId;
    public int x,y;
    public int state;

    public boolean check(int key) {
        return (state & key) != 0;
    }
}
