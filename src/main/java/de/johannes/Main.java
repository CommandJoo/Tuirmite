package de.johannes;

import de.curses.NativeCurses;
import de.curses.window.Window;

public class Main {

    public static void main(String[] args) {
        NativeCurses curses = NativeCurses.instance();
        Window window = new Window(0, 0, curses.getWidth()-1, curses.getHeight()-1);
        StringBuilder s = new StringBuilder();
        while(true) {
            char ch = (char)curses.getch(false);
            if(ch == 3/*CTRL+C*/) break;
            window.draw();
        }
        curses.destroy();
        System.exit(0);
    }
}