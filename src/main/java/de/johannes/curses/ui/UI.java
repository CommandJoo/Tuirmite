package de.johannes.curses.ui;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.util.ColorBuilder;

public class UI {

    private static boolean checkFormatting(String str, int color) {
        Curses curses = Curses.instance();
        if(str.isEmpty()) return false;

        char ch = str.charAt(0);
        return switch (ch) {
            case 'h' -> {
                curses.attron(CursesConstants.ATTRIB_REVERSE);
                yield true;
            }
            case 'd' -> {
                curses.attron(CursesConstants.ATTRIB_DIM);
                yield true;
            }
            case 'i' -> {
                curses.attron(CursesConstants.ATTRIB_ITALIC);
                yield true;
            }
            case 'v' -> {
                curses.attron(CursesConstants.ATTRIB_INVIS);
                yield true;
            }
            case 'b' -> {
                curses.attron(CursesConstants.ATTRIB_BLINK);
                yield true;
            }
            case 'u' -> {
                curses.attron(CursesConstants.ATTRIB_UNDERLINE);
                yield true;
            }
            case 'c' -> {//replace color
                if(str.length() > "x{#xxxxxx}".length()) {
                    curses.setColor(parseColor(str.substring(1, 11)));
                    yield true;
                }
                yield false;
            }
            case 'r' -> {
                alloff();
                if(color != -1) {
                    curses.setColor(color);
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
        Curses curses = Curses.instance();
        curses.attroff(CursesConstants.ATTRIB_REVERSE);
        curses.attroff(CursesConstants.ATTRIB_DIM);
        curses.attroff(CursesConstants.ATTRIB_ITALIC);
        curses.attroff(CursesConstants.ATTRIB_INVIS);
        curses.attroff(CursesConstants.ATTRIB_BLINK);
        curses.attroff(CursesConstants.ATTRIB_UNDERLINE);
    }

    public static void drawString(Object str, int x, int y) {
        Curses curses = Curses.instance();

        if(str.toString().contains("$")) {
            String[] formats = str.toString().split("\\$");
            int length = 0;
            for(String s : formats) {
                curses.moveCursor(x+length, y);
                boolean b = false;
                if(!s.isEmpty()) {
                    b = checkFormatting(s, -1);
                }
                curses.printstr(s.substring(b ? 1 : 0));
                length+=s.length() - (b ? 1 : 0);
            }
            alloff();
        }else {
            curses.moveCursor(x,y);
            curses.printstr(str.toString());
        }
    }
    public static void drawString(Object str, int x, int y, int color) {
        Curses curses = Curses.instance();
        if(str.toString().contains("$")) {
            String[] formats = str.toString().split("\\$");
            int length = 0;
            for(String s : formats) {
                curses.moveCursor(x+length, y);
                boolean b = false;
                if(!s.isEmpty()) {
                    b = checkFormatting(s, color);
                }
                curses.setColor(color);
                curses.printstr(b ? s.charAt(0) == 'c' ? s.substring(11) : s.substring(1) : s);
                length+=s.length() - (b ? s.charAt(0) == 'c' ? 11 : 1 : 0);
            }
        }else {
           curses.drawString(str,x,y,color);
        }
        curses.setColor(CursesConstants.WHITE);
    }

    public static void drawCenteredString(String str, int x, int y, int color) {
        String stripped =  (str.replaceAll("\\$[a-z]", ""));
        drawString(str, x - stripped.length() / 2, y, color);
    }

}
