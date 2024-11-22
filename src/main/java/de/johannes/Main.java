package de.johannes;

import de.curses.NativeCurses;
import de.curses.WindowManager;
import de.curses.util.Timer;
import de.curses.window.Window;

import javax.sound.midi.SysexMessage;

public class Main {

    public static String username = "";
    public static final int FPS = 20;

    private static boolean stop;
    public static char key;
    public static Timer fpsTimer;

    public static void main(String[] args) throws InterruptedException {
        WindowManager winman = WindowManager.instance();
        winman.render();
        winman.handleKey();

        Window login = winman.addWindow(0, new LoginWindow());
        Window test = winman.addWindow(1, new TestWindow());
        winman.changeWindow(login);
    }
}