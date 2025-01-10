package de.johannes.curses.ui;

import de.johannes.curses.Curses;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.util.Timer;
import de.johannes.curses.ui.components.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

public class WindowManager {

    private static WindowManager instance;
    private boolean running;

    private HashMap<Integer, Window> addQueue;
    private final HashMap<Integer, Window> windows;

    public final int fps;
    private final int minWidth, minHeight;
    private final boolean roundColors;

    private final Timer fpsTimer;
    private final List<BiConsumer<Character, Integer>> keyHandlers;

    public WindowManager(int fps, int minWidth, int minHeight, boolean roundColors) {
        if (instance != null) throw new IllegalStateException("Only one WindowManager is allowed per Runtime!");
        instance = this;

        this.addQueue = new HashMap<>();
        this.windows = new HashMap<>();

        this.fps = fps;
        this.minWidth = minWidth;
        this.minHeight = minHeight;
        this.roundColors = roundColors;

        this.fpsTimer = new Timer();

        this.keyHandlers = new ArrayList<>();
        Curses.instance();
        this.running = true;
    }

    public WindowManager(final int fps) {
        this(fps, -1, -1, false);
    }

    public void render() {
        new Thread(() -> {
            while (running) {
                if (fpsTimer.check(1000 / fps)) {
                    if(!addQueue.isEmpty()) {
                        for(Integer key : this.addQueue.keySet()) {
                            this.windows.put(key, this.addQueue.get(key));
                        }
                        this.addQueue = new HashMap<>();
                    }
                    if(Curses.width() < minWidth || Curses.height() < minHeight) {
                        Curses.clearBox(0, 0, Curses.width(), Curses.height(), ColorBuilder.create().defineForeground("#222233").defineBackground("#222233").build());
                        UI.drawCenteredString("$uWindow too small", Curses.width()/2, Curses.height()/2-4, ColorBuilder.create().defineBackground("#222233").defineForeground("#9999AA").build());
                        UI.drawCenteredString("Current (width: $i"+Curses.width()+"$r), (height: $i"+Curses.height()+"$r)", Curses.width()/2, Curses.height()/2-2, ColorBuilder.create().defineBackground("#222233").defineForeground("#DD6666").build());
                        UI.drawCenteredString("$uPlease use", Curses.width()/2, Curses.height()/2, ColorBuilder.create().defineBackground("#222233").defineForeground("#9999AA").build());
                        UI.drawCenteredString("Minimum (width: $i"+minWidth+"$r), (height: $i"+minHeight+"$r)", Curses.width()/2, Curses.height()/2+2, ColorBuilder.create().defineBackground("#222233").defineForeground("#66DD66").build());
                    }else {
                        windows.forEach((id, window) -> {
                            Curses.clearBox(window.x(),window.y(),window.width(),window.height(), window.color());
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
                        kill();
                    } catch(Exception _) {
                    }
                    break;
                }
                if(addQueue.isEmpty()) {
                    if(in != Keys.KEY_MOUSE) {
                        for(Window window : windows.values()) {
                            window.handleKey((char)in);
                        }
                    }else {
                        Mouse event = Curses.instance().getMouseEvent();
                        for(Integer id : windows.keySet()) {
                            Window window = windows.get(id);
//                            if(event.x == window.x()+ window.width() && event.y == window.y() && window.isCloseable()) {
//                                removeWindow(id);
//                                Curses.instance().cls();
//                            }
                            window.handleClick(event);
                        }
                    }
                }
                for(BiConsumer<Character, Integer> cons : this.keyHandlers) {
                    cons.accept((char)in, in);
                }
            }
            Curses.clear();
            Curses.kill();
        }).start();
    }
    public void addKeyHandler(BiConsumer<Character, Integer> handler) {
        this.keyHandlers.add(handler);
    }


    public void kill() {
        running = false;
        new Thread(() -> {
            try {
                Thread.sleep((long) (1000/(fps*0.75)));
                Curses.clear();
                Curses.instance().destroy();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }


    public Window addWindow(int id, Window window) {
        if (this.windows.containsKey(id))
            throw new IllegalArgumentException("Window with ID: " + id + " already registered!");
        this.addQueue.put(id, window);
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

    public boolean roundColors() {
        return roundColors;
    }
}
