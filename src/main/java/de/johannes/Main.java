package de.johannes;

import de.curses.NativeCurses;
import de.curses.window.Textfield;
import de.curses.window.Window;

import java.util.Random;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        NativeCurses.instance();
        TestWindow window = new TestWindow(0, 0, 60, 30);
        while(true) {
            char ch = (char) NativeCurses.instance().getch(false);
            if(ch==3) break;

            window.drawWindow(ch);

            Thread.sleep(1000/30);
        }
        NativeCurses.instance().destroy();
    }

}