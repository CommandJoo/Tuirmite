package de.johannes.curses;

import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.util.Files;

public class Curses {

    static final int BLACK = 1;
    static final int DARK_GRAY = 2;
    static final int LIGHT_GRAY = 3;
    static final int WHITE = 4;
    static final int DARK_RED = 5;
    static final int LIGHT_RED = 6;
    static final int DARK_GREEN = 7;
    static final int LIGHT_GREEN = 8;
    static final int DARK_BLUE = 9;
    static final int LIGHT_BLUE = 10;
    static final int DARK_CYAN = 11;
    static final int LIGHT_CYAN = 12;
    static final int DARK_MAGENTA = 13;
    static final int LIGHT_MAGENTA = 14;
    static final int DARK_YELLOW = 15;
    static final int LIGHT_YELLOW = 16;

    static final char CORNER_UPPER_LEFT = '┌';
    static final char CORNER_UPPER_RIGHT = '┐';
    static final char CORNER_LOWER_LEFT = '└';
    static final char CORNER_LOWER_RIGHT = '┘';
    static final char CORNER_ROUNDED_UPPER_LEFT = '╭';
    static final char CORNER_ROUNDED_UPPER_RIGHT = '╮';
    static final char CORNER_ROUNDED_LOWER_LEFT = '╰';
    static final char CORNER_ROUNDED_LOWER_RIGHT = '╯';

    static final char TEE_DOWN_POINTING = '┬';
    static final char TEE_UP_POINTING = '┴';
    static final char TEE_RIGHT_POINTING = '├';
    static final char TEE_LEFT_POINTING = '┤';
    static final char CROSS = '┼';

    static final char LINE_HORIZONTAL = '─';
    static final char LINE_VERTICAL = '│';

    static final int ATTRIB_REVERSE = 0;
    static final int ATTRIB_DIM = 1;
    static final int ATTRIB_ITALIC = 2;
    static final int ATTRIB_INVIS = 3;
    static final int ATTRIB_BLINK = 4;
    static final int ATTRIB_UNDERLINE = 5;

    public native void init();
    public native void destroy();
    public static void kill() {instance().destroy();}

    public native void cls();
    public static void clear() {instance().cls();}
    public native void setCursor(boolean flag);
    public native void moveCursor(int x, int y);

    public native void setColor(int color);
    public native int defineColor(int color, float r, float g, float b);
    public int defineColor(int color, int r, int g, int b) {
        return defineColor(color, r / 255F, g / 255F, b / 255F);
    }
    public native int defineColorPair(int color, int foreground, int background);

    public native void print(char ch);
    public native void printstr(String str);

    public native int getch();
    public native Mouse getMouseEvent();
    public static int getChar() {return instance().getch();}
    public static int readChar() {return instance().inch();}
    public native int inch();
    public native int moveinch(int x, int y);
    public static int moveReadChar(int x, int y) {return instance().moveinch(x,y);}

    public native void attron(int attr);
    public native void attroff(int attr);
    public static void attrib_on(int attr) {instance.attron(attr);}
    public static void attrib_off(int attr) {instance.attroff(attr);}

    public native int getHeight();
    public static int height() {
        return instance().getHeight();
    }
    public native int getWidth();
    public static int width() {
        return instance().getWidth();
    }

    public native void drawBox(int x, int y, int width, int height);
    public native void drawHorizontalLine(int y, int x1, int x2);
    public native void drawVerticalLine(int x, int y1, int y2);
    public native void drawCorner(int x, int y, int type);

    public void drawHorizontalLine(int y, int x1, int x2, int color) {
        moveCursor(x1,y);
        setColor(color);
        drawHorizontalLine(y,x1,x2);
    }

    public void drawVerticalLine(int x, int y1, int y2, int color) {
        moveCursor(x,y1);
        setColor(color);
        drawVerticalLine(x,y1,y2);
    }

    public void drawString(Object s, int x, int y) {
        moveCursor(x,y);
        printstr(s.toString());
    }

    public void drawString(Object s, int x, int y, int color) {
        moveCursor(x,y);
        setColor(color);
        printstr(s.toString());
    }

    public void drawRoundedCorner(int x, int y, int type, int color) {
            switch (type) {
                case 0:
                    drawString(CursesConstants.CORNER_ROUNDED_LOWER_RIGHT, x,y, color);
                    break;
                case 1:
                    drawString(CursesConstants.CORNER_ROUNDED_UPPER_RIGHT, x,y, color);
                    break;
                case 2:
                    drawString(CursesConstants.CORNER_ROUNDED_UPPER_LEFT, x,y, color);
                    break;
                case 3:
                    drawString(CursesConstants.CORNER_ROUNDED_LOWER_LEFT, x,y, color);
                    break;
                default: break;
        }
    }
    public void drawCorner(int x, int y, int type, int color) {
        moveCursor(x,y);
        setColor(color);
        drawCorner(x, y, type);
    }
    public void drawCorner(int x, int y, int type, int color, boolean rounded) {
        moveCursor(x,y);
        setColor(color);
        if(!rounded) {
            drawCorner(x, y, type);
        }else {
            drawRoundedCorner(x,y,type, color);
        }
    }

    private native void drawTee(int x, int y, int type);
    public void drawTee(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        //4 CROSS
        moveCursor(x,y);
        setColor(color);
        drawTee(x, y, type);
    }
    private native void drawArrow(int x, int y, int type);
    public void drawArrow(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        moveCursor(x,y);
        setColor(color);
        drawArrow(x, y, type);
    }
    public static void clearBox(int x, int y, int width, int height) {
        clearBox(x,y,width,height, WHITE);
    }
    public static void clearBox(int x, int y, int width, int height, int color) {
        String line = " ".repeat(width);
        for (int i = 0; i < height; i++) {
            instance().moveCursor(x,y+i);
            instance().setColor(color);
            instance().printstr(line);
        }
        instance().setColor(WHITE);
    }
    public static void reverseClearBox(int x, int y, int width, int height, int color) {
        String line = " ".repeat(width);
        instance().attron(ATTRIB_REVERSE);
        for (int i = 0; i < height; i++) {
            instance().moveCursor(x,y+i);
            instance().setColor(color);
            instance().printstr(line);
        }
        instance().setColor(WHITE);
        instance().attroff(ATTRIB_REVERSE);
    }

    public native void refresh();

    private static Curses instance;
    public Curses() {
        if (instance != null) {
            throw new RuntimeException("Can only have one instance of NativeCurses running!\nPlease use NativeCurses.instance()");
        }
        instance = this;

        Files.loadLibrary("curses");

        init();
        defineColor(17, 0.4F, 0.4F, 0.4F);
        defineColorPair(17, 17, 0);
    }

    public static Curses instance() {
        return instance != null ? instance : new Curses();
    }

}