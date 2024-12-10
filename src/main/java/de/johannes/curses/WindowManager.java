package de.johannes.curses;

import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.util.Timer;
import de.johannes.curses.window.components.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class WindowManager {

    private static WindowManager instance;
    private boolean running;

    private final HashMap<Integer, Window> windows;

    public final int fps;
    private final int minWidth, minHeight;
    private final Timer fpsTimer;

    private final List<BiConsumer<Character, Integer>> keyHandlers;

    public WindowManager(final int fps, int minWidth, int minHeight) {
        if (instance != null) throw new IllegalStateException("Only one WindowManager is allowed per Runtime!");
        instance = this;

        this.windows = new HashMap<>();

        this.fps = fps;
        this.minWidth = minWidth;
        this.minHeight = minHeight;

        this.fpsTimer = new Timer();

        this.keyHandlers = new ArrayList<>();
        Curses.instance();
        this.running = true;
    }

    public WindowManager(final int fps) {
        this(fps, -1, -1);
    }

    public void render() {
        new Thread(() -> {
            while (running) {
                if (fpsTimer.check(1000 / fps)) {
                    if(Curses.width() < minWidth || Curses.height() < minHeight) {
                        Curses.clearBox(0, 0, Curses.width(), Curses.height(), ColorBuilder.create().defineForeground("#222233").defineBackground("#222233").build());
                        Curses.drawCenteredString("$uWindow too small", Curses.width()/2, Curses.height()/2-4, ColorBuilder.create().defineBackground("#222233").defineForeground("#9999AA").build());
                        Curses.drawCenteredString("Current (width: $i"+Curses.width()+"$r), (height: $i"+Curses.height()+"$r)", Curses.width()/2, Curses.height()/2-2, ColorBuilder.create().defineBackground("#222233").defineForeground("#DD6666").build());
                        Curses.drawCenteredString("$uPlease use", Curses.width()/2, Curses.height()/2, ColorBuilder.create().defineBackground("#222233").defineForeground("#9999AA").build());
                        Curses.drawCenteredString("Minimum (width: $i"+minWidth+"$r), (height: $i"+minHeight+"$r)", Curses.width()/2, Curses.height()/2+2, ColorBuilder.create().defineBackground("#222233").defineForeground("#66DD66").build());
                    }else {
                        windows.forEach((id1, window) -> {
                            Curses.clearBox(window.x,window.y,window.width,window.height);
                            window.drawBox(window.color);
                            window.drawWindow();
                        });
                    }
                    Curses.instance().refresh();
                    fpsTimer.reset();
                }
            }
        }).start();
    }

    public void handleKey() throws InterruptedException {
        new Thread(() -> {
            while (running) {
                int in = Curses.instance().getch();
                if (in == 3) {
                    try {
                        running = false;
                        Thread.sleep((long) (1000/(fps*0.75)));
                        kill();
                        System.exit(0);
                    } catch(Exception _) {
                    }
                    break;
                }
                for(Window window : windows.values()) {
                    window.handleKey((char)in);
                }
                for(BiConsumer<Character, Integer> cons : this.keyHandlers) {
                    cons.accept((char)in, in);
                }
            }
        }).start();
    }
    public void addKeyHandler(BiConsumer<Character, Integer> handler) {
        this.keyHandlers.add(handler);
    }


    public void kill() {
        running = false;
        Curses.instance().destroy();
    }


    public Window addWindow(int id, Window window) {
        if (this.windows.containsKey(id))
            throw new IllegalArgumentException("Window with ID: " + id + " already registered!");
        this.windows.put(id, window);
        return window;
    }
    public void removeWindow(int id) {
        if(!this.windows.containsKey(id)) throw new IllegalArgumentException("Window with ID: "+id+" doesn't exist!");
        this.windows.remove(id);
    }
    public Window getWindow(int id) {
        return this.windows.getOrDefault(id, null);
    }


    public static WindowManager instance() {
        if (instance == null) return new WindowManager(30);
        return instance;
    }

}
