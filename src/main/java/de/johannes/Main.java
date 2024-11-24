package de.johannes;

import de.curses.WindowManager;
import de.curses.window.components.Window;

public class Main {

    public static String username = "";
    public static final int FPS = 20;

    public static void main(String[] args) throws InterruptedException {
        WindowManager winman = new WindowManager(FPS);
        winman.render();
        winman.handleKey();

        Window login = winman.addWindow(0, new LoginWindow(), true);
        Window test = winman.addWindow(1, new TestWindow(), false);
    }


    public static String username() {
        return username;
    }
}