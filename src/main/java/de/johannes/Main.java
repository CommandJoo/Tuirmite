package de.johannes;

import de.curses.NativeCurses;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        NativeCurses curses = new NativeCurses();
        boolean stop = false;
        while(!stop) {
            curses.setColor(NativeCurses.DARK_CYAN);
            curses.drawString("Hello World: "+curses.getHeight()+":"+curses.getWidth(), 10+(new Random().nextInt(10)), 2);
            curses.getch();
            curses.cls();
        }
        curses.destroy();
        curses.cls();
    }
}