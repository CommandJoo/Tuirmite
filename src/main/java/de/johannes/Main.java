package de.johannes;

import de.curses.NativeCurses;

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