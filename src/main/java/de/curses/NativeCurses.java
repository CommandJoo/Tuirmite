package de.curses;

import de.curses.util.Files;

public class NativeCurses {

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
    static final int ATTRIB_BLINKING = 4;
    static final int ATTRIB_UNDERLINE = 5;

    public native void init();
    public native void destroy();

    public native void cls();
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
    public native int inch();
    public native int moveinch(int x, int y);

    public native void attron(int attr);
    public native void attroff(int attr);

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
    public void drawCorner(int x, int y, int type, int color) {
        setColor(color);
        drawCorner(x, y, type);
    }
    private native void drawTee(int x, int y, int type);
    public void drawTee(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        //4 CROSS
        setColor(color);
        drawTee(x, y, type);
    }
    private native void drawArrow(int x, int y, int type);
    public void drawArrow(int x, int y, int type, int color) {
        //0 RIGHT POINTING
        //1 LEFT POINTING
        //2 UP POINTING
        //3 DOWN POINTING
        setColor(color);
        drawArrow(x, y, type);
    }

    public native void refresh();

    private static NativeCurses instance;
    public NativeCurses() {
        if (instance != null) {
            throw new RuntimeException("Can only have one instance of NativeCurses running!\nPlease use NativeCurses.instance()");
        }
        instance = this;

        Files.loadLibrary("curses");

        init();
        defineColor(17, 0.4F, 0.4F, 0.4F);
        defineColorPair(17, 17, 0);
    }

    private static boolean checkFormatting(char ch) {
        return switch (ch) {
            case 'h' -> {
                instance().attron(ATTRIB_REVERSE);
                yield true;
            }
            case 'd' -> {
                instance().attron(ATTRIB_DIM);
                yield true;
            }
            case 'i' -> {
                instance().attron(ATTRIB_ITALIC);
                yield true;
            }
            case 'v' -> {
                instance().attron(ATTRIB_INVIS);
                yield true;
            }
            case 'b' -> {
                instance().attron(ATTRIB_BLINKING);
                yield true;
            }
            case 'u' -> {
                instance().attron(ATTRIB_UNDERLINE);
                yield true;
            }
            case 'r' -> {
                instance().attroff(ATTRIB_REVERSE);
                instance().attroff(ATTRIB_DIM);
                instance().attroff(ATTRIB_ITALIC);
                instance().attroff(ATTRIB_INVIS);
                instance().attroff(ATTRIB_BLINKING);
                instance().attroff(ATTRIB_UNDERLINE);
                yield true;
            }
            default -> false;
        };
    }

    public static void drawString(String str, int x, int y) {
        String[] formats = str.split("\\$");
        int length = 0;
        for(String s : formats) {
            instance().moveCursor(x+length, y);
            boolean b = false;
            if(!s.isEmpty()) {
                b = checkFormatting(s.charAt(0));
            }
            instance().printstr(s.substring(b ? 1 : 0));
            length+=s.length() - (b ? 1 : 0);
        }
    }
    public static void drawString(String str, int x, int y, int color) {
        String[] formats = str.split("\\$");
        int length = 0;
        for(String s : formats) {
            instance().moveCursor(x+length, y);
            boolean b = false;
            if(!s.isEmpty()) {
                b = checkFormatting(s.charAt(0));
            }
            instance().setColor(color);
            instance().printstr(s.substring(b ? 1 : 0));
            length+=s.length() - (b ? 1 : 0);
        }
        instance().setColor(WHITE);
    }

    public static void clearBox(int x, int y, int width, int height) {
        String line = " ".repeat(width);
        for (int i = 0; i < height; i++) {
            drawString(line, x, y + i);
        }
    }

    public static NativeCurses instance() {
        return instance != null ? instance : new NativeCurses();
    }


}