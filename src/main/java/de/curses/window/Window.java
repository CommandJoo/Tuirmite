package de.curses.window;

import de.curses.NativeCurses;

public class Window {

    private int x, y, width, height;
    private boolean touched = true;

    public Window(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        draw();
    }

    public void draw() {
        if(touched) {
            NativeCurses.instance().setColor(NativeCurses.DARK_CYAN);
            NativeCurses.instance().drawBox(x,y,width,height);
            this.touched = false;
        }
    }

    public void drawString(int x, int y, String s, int color) {
        NativeCurses.instance().setColor(color);
        NativeCurses.instance().drawString(s, x, y);
        touch();
    }

    private void touch() {
        touched = true;
    }

    public boolean touched() {
        return touched;
    }
}
