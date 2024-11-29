package de.johannes;

import de.johannes.curses.Curses;
import de.johannes.curses.WindowManager;
import de.johannes.curses.window.components.Window;
import de.johannes.example.Example;
import de.johannes.snake.SnakeWindow;

public class Main {

    public static String username = "";
    public static final int FPS = 60;

    public static void main(String[] args) throws InterruptedException {
        WindowManager winman = new WindowManager(FPS, 50, 20);
        winman.render();
        winman.handleKey();

        Window example = winman.addWindow(0, new Example());
    }


    public static String username() {
        return username;
    }
}