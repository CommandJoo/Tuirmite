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
    public void drawRoundedCorner(int x, int y, int type) {
            switch (type) {
                case 0:
                    drawString(CORNER_ROUNDED_LOWER_RIGHT, x,y);
                    break;
                case 1:
                    drawString(CORNER_ROUNDED_UPPER_RIGHT, x,y);
                    break;
                case 2:
                    drawString(CORNER_ROUNDED_UPPER_LEFT, x,y);
                    break;
                case 3:
                    drawString(CORNER_ROUNDED_LOWER_LEFT, x,y);
                    break;
                default: break;
        }
    }
    public void drawCorner(int x, int y, int type, int color) {
        setColor(color);
        drawCorner(x, y, type);
    }
    public void drawCorner(int x, int y, int type, int color, boolean rounded) {
        setColor(color);
        if(!rounded) {
            drawCorner(x, y, type);
        }else {
            drawRoundedCorner(x,y,type);
        }
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
    public static void clearBox(int x, int y, int width, int height) {
        String line = " ".repeat(width);
        for (int i = 0; i < height; i++) {
            drawString(line, x, y + i);
        }
    }
    public static void clearBox(int x, int y, int width, int height, int color) {
        String line = " ".repeat(width);
        for (int i = 0; i < height; i++) {
            drawString(line, x, y + i, color);
        }
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






    private static boolean checkFormatting(String str, int color) {
        if(str.isEmpty()) return false;

        char ch = str.charAt(0);
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
                instance().attron(ATTRIB_BLINK);
                yield true;
            }
            case 'u' -> {
                instance().attron(ATTRIB_UNDERLINE);
                yield true;
            }
            case 'c' -> {//replace color
                if(str.length() > "x{#xxxxxx}".length()) {
                    instance().setColor(parseColor(str.substring(1, 11)));
                    yield true;
                }
                yield false;
            }
            case 'r' -> {
                alloff();
                if(color != -1) {
                    instance().setColor(color);
                }
                yield true;
            }

            default -> false;
        };
    }

    private static int parseColor(String colorstring) {
        String hex = colorstring.substring(2, colorstring.length()-1);
        if(colorstring.charAt(0) == 'f') {
            return ColorBuilder.create().defineForeground(hex).build();
        }else if(colorstring.charAt(0) == 'b') {
            return ColorBuilder.create().defineBackground(hex).build();
        }else {
            return 0;
        }
    }

    private static void alloff() {
        instance().attroff(ATTRIB_REVERSE);
        instance().attroff(ATTRIB_DIM);
        instance().attroff(ATTRIB_ITALIC);
        instance().attroff(ATTRIB_INVIS);
        instance().attroff(ATTRIB_BLINK);
        instance().attroff(ATTRIB_UNDERLINE);
    }

    public static void drawString(Object str, int x, int y) {
        if(str.toString().contains("$")) {
            String[] formats = str.toString().split("\\$");
            int length = 0;
            for(String s : formats) {
                instance().moveCursor(x+length, y);
                boolean b = false;
                if(!s.isEmpty()) {
                    b = checkFormatting(s, -1);
                }
                instance().printstr(s.substring(b ? 1 : 0));
                length+=s.length() - (b ? 1 : 0);
            }
            alloff();
        }else {
            instance().moveCursor(x,y);
            instance().printstr(str.toString());
        }
    }
    public static void drawString(Object str, int x, int y, int color) {
        if(str.toString().contains("$")) {
            String[] formats = str.toString().split("\\$");
            int length = 0;
            for(String s : formats) {
                instance().moveCursor(x+length, y);
                boolean b = false;
                instance().setColor(color);
                if(!s.isEmpty()) {
                    b = checkFormatting(s, color);
                }
                instance().printstr(b ? s.charAt(0) == 'c' ? s.substring(11) : s.substring(1) : s);
                length+=s.length() - (b ? s.charAt(0) == 'c' ? 11 : 1 : 0);
            }
        }else {
            instance().moveCursor(x,y);
            instance().setColor(color);
            instance().printstr(str.toString());
        }
        instance().setColor(WHITE);
    }

    public static void drawCenteredString(String str, int x, int y, int color) {
        String stripped =  (str.replaceAll("\\$[a-z]", ""));
        drawString(str, x - stripped.length() / 2, y, color);
    }

    public static Curses instance() {
        return instance != null ? instance : new Curses();
    }


}