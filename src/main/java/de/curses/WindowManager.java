package de.curses;

import de.curses.util.Timer;
import de.curses.window.Window;

import java.io.IOException;
import java.util.HashMap;

public class WindowManager {

    private static WindowManager instance;
    private boolean running;

    private final HashMap<Integer, Window> windows;
    private Window active;

    public final int fps;
    private final Timer fpsTimer;

    public WindowManager(final int fps) {
        if(instance != null) throw new IllegalStateException("Only one WindowManager is allowed per Runtime!");
        instance = this;

        this.fps = fps;
        this.windows = new HashMap<>();
        this.fpsTimer = new Timer();

        NativeCurses.instance();
        this.running = true;
    }

    public void render() {
        new Thread(() -> {
            while (running) {
                if(fpsTimer.check(1000/fps)) {
                    if(active != null) {
                        try {
                            active.drawWindow();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    fpsTimer.reset();
                }
            }
        }).start();
    }

    public void handleKey() throws InterruptedException {
        new Thread(() -> {
            while (running) {
                int in = NativeCurses.instance().getch();
                if (in == 3) {
                    running = false;
                    kill();
                    System.exit(0);
                    break;
                }
                if(active != null) {
                    active.handleKey((char)in);
                }
            }
        }).start();
    }

    public void kill() {
        NativeCurses.instance().destroy();
    }


    public Window addWindow(int id, Window window) {
        if (this.windows.containsKey(id))
            throw new IllegalArgumentException("Window with ID: " + id + " already registered");
        this.windows.put(id, window);
        return window;
    }

    public Window getWindow(int id) {
        return this.windows.getOrDefault(id, null);
    }

    public Window changeWindow(Window window) {
        this.active = window;
        return this.active;
    }

    public static WindowManager instance() {
        if(instance == null) return new WindowManager(30);
        return instance;
    }

}
