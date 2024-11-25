package de.curses;

import de.curses.util.Files;

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
    public int defineColor(int color, int r, int g, int b) {
        return defineColor(color, r/255F, g/255F, b/255F);
    }
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
     *Good evening everyone – teachers, parents, family, and of course, my fellow graduates.

Today is a moment we’ve been dreaming about for years – or maybe dreading when exams came around. But here we are, standing together, because we did it. We survived the exams, the essays, the group projects, and even those dreaded oral presentations. The Abitur isn’t just a certificate; it’s proof of our determination, our hard work, and the support we’ve given each other along the way.

Let’s be honest – there were moments when we all doubted ourselves. Late nights spent cramming for tests, feeling like we’d never figure out calculus, or wondering how we’d survive without coffee. But those challenges taught us more than any textbook ever could. We learned resilience. We learned how to adapt. And most importantly, we learned that we’re stronger when we stick together.

To our teachers: thank you for your patience, your dedication, and for believing in us even when we struggled to believe in ourselves. You didn’t just teach us facts and formulas – you taught us how to think, how to question, and how to grow. To our families: thank you for being our biggest cheerleaders, for supporting us through every success and every failure, and for always reminding us to keep going.

Now, as we leave this chapter behind, we step into a world that’s full of possibilities. Whether we’re heading to university, starting apprenticeships, traveling the world, or simply figuring things out, we’re ready. We’ve proven that we can handle challenges, and we’ll carry these lessons with us wherever we go.

So, let’s celebrate not just what we’ve achieved, but who we’ve become. Congratulations to the Class of [Year]! This is just the beginning, and I can’t wait to see where life takes us next.

Thank you!
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

    public native void refresh();

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

        Files.loadLibrary("curses");

        init();
        defineColor(17, 0.4F, 0.4F, 0.4F);
        defineColorPair(17, 17, 0);
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

    public void clearBox(int x, int y, int width, int height) {
        String line = " ".repeat(width);
        for(int i = 0; i < height; i++) {
            drawString(line, x,y+i);
        }
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