package de.curses;

public class NativeCurses {

    // constants (colors)
    public static final int BLACK = 1;
    public static final int DARK_GRAY = 2;
    public static final int LIGHT_GRAY = 3;
    public static final int WHITE = 4;
    public static final int DARK_RED = 5;
    public static final int LIGHT_RED = 6;
    public static final int DARK_GREEN = 7;
    public static final int LIGHT_GREEN = 8;
    public static final int DARK_BLUE = 9;
    public static final int LIGHT_BLUE = 10;
    public static final int DARK_CYAN = 11;
    public static final int LIGHT_CYAN = 12;
    public static final int DARK_MAGENTA = 13;
    public static final int LIGHT_MAGENTA = 14;
    public static final int DARK_YELLOW = 15;
    public static final int LIGHT_YELLOW = 16;

    static {
        System.loadLibrary("curses");
    }

    public native void init();

    public native void destroy();

    public native void cls();

    public native void setCursor(boolean flag);

    public native void moveCursor(int x, int y);

    public native void setColor(int color);

    public native void print(char ch);

    public native void printstr(String str);

    public native int getch(boolean wait);

    public native int getHeight();

    public native int getWidth();

    public native void drawBox(int x, int y, int width, int height);

    private static NativeCurses instance;

    public NativeCurses() {
        if(instance != null) {
            throw new RuntimeException("Can only have one instance of NativeCurses running!\nPlease use NativeCurses.instance()");
        }
        instance = this;
        init();
    }

    public void drawString(String str, int x, int y) {
        moveCursor(x, y);
        printstr(str);
    }

    public static NativeCurses instance() {
        return instance != null ? instance : new NativeCurses();
    }
}