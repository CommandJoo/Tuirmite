package de.curses;

import de.curses.util.Files;

public class NativeCurses {

// constants (colors)
    public static final int BLACK =                    1;
    public static final int DARK_GRAY =                2;
    public static final int LIGHT_GRAY =               3;
    public static final int WHITE =                    4;
    public static final int DARK_RED =                 5;
    public static final int LIGHT_RED =                6;
    public static final int DARK_GREEN =               7;
    public static final int LIGHT_GREEN =              8;
    public static final int DARK_BLUE =                9;
    public static final int LIGHT_BLUE =              10;
    public static final int DARK_CYAN =               11;
    public static final int LIGHT_CYAN =              12;
    public static final int DARK_MAGENTA =            13;
    public static final int LIGHT_MAGENTA =           14;
    public static final int DARK_YELLOW =             15;
    public static final int LIGHT_YELLOW =            16;

    public static final char CORNER_UPPER_LEFT =     '┌';
    public static final char CORNER_UPPER_RIGHT =    '┐';
    public static final char CORNER_LOWER_LEFT =     '└';
    public static final char CORNER_LOWER_RIGHT =    '┘';

    public static final char TEE_DOWN_POINTING =     '┬';
    public static final char TEE_UP_POINTING =       '┴';
    public static final char TEE_RIGHT_POINTING =    '├';
    public static final char TEE_LEFT_POINTING =     '┤';
    public static final char CROSS =                 '┼';

    public static final char LINE_HORIZONTAL =       '─';
    public static final char LINE_VERTICAL =         '│';

    public static final int ATTRIB_REVERSE =           0;
    public static final int ATTRIB_DIM =               1;
    public static final int ATTRIB_ITALIC   =          2;



    public native void init();
    public native void destroy();

    public native void cls();
    public native void setCursor(boolean flag);
    public native void moveCursor(int x, int y);

    public native void setColor(int color);
    public native int defineColor(int color, float r, float g, float b);
    public int defineColor(int color, int r, int g, int b) {
        return defineColor(color, r/255F, g/255F, b/255F);
    }
    public native int defineColorPair(int color, int foreground, int background);

    public native void print(char ch);
    public native void printstr(String str);

    public native int getch();

    public native void attron(int attr);
    public native void attroff(int attr);

    public native int getHeight();
    public static int height() {return instance().getHeight();}
    public native int getWidth();
    public static int width() {return instance().getWidth();}

    public native void drawBox(int x, int y, int width, int height);

    public native void drawHorizontalLine(int y, int x1, int x2);
    public native void drawVerticalLine(int x, int y1, int y2);

    public native void drawCorner(int x, int y, int type);
    private native void drawTee(int x, int y, int type);
    public void drawTee(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        //4 CROSS
        setColor(color);
        drawTee(x,y, type);
    }
    private native void drawArrow(int x, int y, int type);
    public void drawArrow(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        setColor(color);
        drawArrow(x,y,type);
    }

    public native void refresh();

    private static NativeCurses instance;

    public NativeCurses() {
        if(instance != null) {
            throw new RuntimeException("Can only have one instance of NativeCurses running!\nPlease use NativeCurses.instance()");
        }
        instance = this;

        Files.loadLibrary("curses");

        init();
        defineColor(17, 0.4F, 0.4F, 0.4F);
        defineColorPair(17, 17, 0);
    }

    public void drawString(String str, int x, int y) {
        moveCursor(x, y);
        printstr(str);
    }

    public void drawString(String str, int x, int y, int color) {
        moveCursor(x, y);
        setColor(color);
        printstr(str);
    }

    public void clearBox(int x, int y, int width, int height) {
        String line = " ".repeat(width);
        for(int i = 0; i < height; i++) {
            drawString(line, x,y+i);
        }
    }

    public static NativeCurses instance() {
        return instance != null ? instance : new NativeCurses();
    }


}