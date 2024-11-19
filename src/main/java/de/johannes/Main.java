package de.johannes;

import de.curses.NativeCurses;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NativeCurses curses = new NativeCurses();
        boolean stop = false;
        while(!stop) {
            curses.setColor(NativeCurses.DARK_CYAN);
            curses.drawBox(0, 0, 20, 10);
            curses.getch();
        }
        curses.destroy();
    }
}