package de.johannes;

import de.curses.NativeCurses;
import de.curses.WindowManager;
import de.curses.window.components.Window;
import de.johannes.snake.SnakeWindow;

public class Main {

    public static String username = "";
    public static final int FPS = 60;

    public static void main(String[] args) throws InterruptedException {
        WindowManager winman = new WindowManager(FPS);
        winman.render();
        winman.handleKey();

        Window game = winman.addWindow(2, new SnakeWindow(NativeCurses.instance()));
    }


    public static String username() {
        return username;
    }
}