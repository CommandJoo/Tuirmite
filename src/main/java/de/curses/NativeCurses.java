package de.curses;

/**
 * The type Native curses.
 */
public class NativeCurses {

    /**
     * The constant BLACK.
     */
// constants (colors)
    public static final int BLACK = 1;
    /**
     * The constant DARK_GRAY.
     */
    public static final int DARK_GRAY = 2;
    /**
     * The constant LIGHT_GRAY.
     */
    public static final int LIGHT_GRAY = 3;
    /**
     * The constant WHITE.
     */
    public static final int WHITE = 4;
    /**
     * The constant DARK_RED.
     */
    public static final int DARK_RED = 5;
    /**
     * The constant LIGHT_RED.
     */
    public static final int LIGHT_RED = 6;
    /**
     * The constant DARK_GREEN.
     */
    public static final int DARK_GREEN = 7;
    /**
     * The constant LIGHT_GREEN.
     */
    public static final int LIGHT_GREEN = 8;
    /**
     * The constant DARK_BLUE.
     */
    public static final int DARK_BLUE = 9;
    /**
     * The constant LIGHT_BLUE.
     */
    public static final int LIGHT_BLUE = 10;
    /**
     * The constant DARK_CYAN.
     */
    public static final int DARK_CYAN = 11;
    /**
     * The constant LIGHT_CYAN.
     */
    public static final int LIGHT_CYAN = 12;
    /**
     * The constant DARK_MAGENTA.
     */
    public static final int DARK_MAGENTA = 13;
    /**
     * The constant LIGHT_MAGENTA.
     */
    public static final int LIGHT_MAGENTA = 14;
    /**
     * The constant DARK_YELLOW.
     */
    public static final int DARK_YELLOW = 15;
    /**
     * The constant LIGHT_YELLOW.
     */
    public static final int LIGHT_YELLOW = 16;

    /**
     * The constant CORNER_UPPER_LEFT.
     */
    public static final char CORNER_UPPER_LEFT = '┌';
    /**
     * The constant CORNER_UPPER_RIGHT.
     */
    public static final char CORNER_UPPER_RIGHT = '┐';
    /**
     * The constant CORNER_LOWER_LEFT.
     */
    public static final char CORNER_LOWER_LEFT ='└';
    /**
     * The constant CORNER_LOWER_RIGHT.
     */
    public static final char CORNER_LOWER_RIGHT = '┘';
    /**
     * The constant TEE_DOWN_POINTING.
     */
    public static final char TEE_DOWN_POINTING = '┬';
    /**
     * The constant TEE_UP_POINTING.
     */
    public static final char TEE_UP_POINTING = '┴';
    /**
     * The constant TEE_RIGHT_POINTING.
     */
    public static final char TEE_RIGHT_POINTING = '├';
    /**
     * The constant TEE_LEFT_POINTING.
     */
    public static final char TEE_LEFT_POINTING = '┤';
    /**
     * The constant CROSS.
     */
    public static final char CROSS = '┼';
    /**
     * The constant LINE_HORIZONTAL.
     */
    public static final char LINE_HORIZONTAL = '─';
    /**
     * The constant LINE_VERTICAL.
     */
    public static final char LINE_VERTICAL = '│';

    static {
        System.loadLibrary("curses");
    }

    /**
     * Init NativeCurses.
     */
    public native void init();

    /**
     * Destroy NativeCurses.
     */
    public native void destroy();

    /**
     * Clear Screen.
     */
    public native void cls();

    /**
     * Sets cursor. true = cursor active, false = no cursor
     *
     * @param flag the flag
     */
    public native void setCursor(boolean flag);

    /**
     * Move cursor.
     *
     * @param x the x
     * @param y the y
     */
    public native void moveCursor(int x, int y);

    /**
     * Sets color.
     *
     * @param color the color
     */
    public native void setColor(int color);

    /**
     * Define color int.
     *
     * @param color the color
     * @param r     the red 0-1.0F
     * @param g     the green 0-1.0F
     * @param b     the blue 0-1.0F
     * @return the color id
     */
    public native int defineColor(int color, float r, float g, float b);

    /**
     * Define color pair int.
     *
     * @param color      the color
     * @param foreground the foreground
     * @param background the background
     * @return the color pair id
     */
    public native int defineColorPair(int color, int foreground, int background);

    /**
     * Print a character.
     *
     * @param ch the ch
     */
    public native void print(char ch);

    /**
     * Print a string.
     *
     * @param str the str
     */
    public native void printstr(String str);

    /**
     * Gets a Character from the user.
     *
     * @return the int-code for the inputted character
     */
    public native int getch();

    /**
     * Gets console height.
     *
     * @return the height
     */
    public native int getHeight();

    /**
     * Gets console width.
     *
     * @return the width
     */
    public native int getWidth();

    /**
     * Draw box.
     *
     * @param x      the x
     * @param y      the y
     * @param width  the width
     * @param height the height
     */
    public native void drawBox(int x, int y, int width, int height);

    /**
     * Draw horizontal line.
     *
     * @param y  the y
     * @param x1 the x 1
     * @param x2 the x 2
     */
    public native void drawHorizontalLine(int y, int x1, int x2);

    /**
     * Draw vertical line.
     *
     * @param x  the x
     * @param y1 the y 1
     * @param y2 the y 2
     */
    public native void drawVerticalLine(int x, int y1, int y2);

    /**
     * Draw corner.
     *
     * @param x    the x
     * @param y    the y
     * @param type the type 0 = Lower Right, 1 = Upper Right, 2 = Upper Left, 3 = Lower Left
     */
    public native void drawCorner(int x, int y, int type);

    /**
     * Draw tee.
     *
     * @param x    the x
     * @param y    the y
     * @param type the type 0 = Right Tee, 1 = Left Tee, 2 = Bottom Tee, 3 = Top Tee, 4 = Cross
     */
    public native void drawTee(int x, int y, int type);

    /**
     * The constant instance.
     */
    private static NativeCurses instance;

    /**
     * Instantiates a new Native curses.
     */
    public NativeCurses() {
        if(instance != null) {
            throw new RuntimeException("Can only have one instance of NativeCurses running!\nPlease use NativeCurses.instance()");
        }
        instance = this;
        init();
    }

    /**
     * Draw string.
     *
     * @param str the str
     * @param x   the x
     * @param y   the y
     */
    public void drawString(String str, int x, int y) {
        moveCursor(x, y);
        printstr(str);
    }

    /**
     * Draw string.
     *
     * @param str   the str
     * @param x     the x
     * @param y     the y
     * @param color the color
     */
    public void drawString(String str, int x, int y, int color) {
        setColor(color);
        moveCursor(x, y);
        printstr(str);
    }

    /**
     * Instance native curses.
     *
     * @return the native curses
     */
    public static NativeCurses instance() {
        return instance != null ? instance : new NativeCurses();
    }


}