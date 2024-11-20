package de.curses.window;

import de.curses.NativeCurses;

import java.util.Random;

public abstract class Window {

    protected final int x, y, width, height;
    protected int color;
    protected final String title;
    protected boolean touched = true;

    public Window(int x, int y, int width, int height) {
        this(x,y,width,height, NativeCurses.WHITE);
    }

    public Window(int x, int y, int width, int height, String title) {
        this(x,y,width,height, NativeCurses.WHITE, title);
    }

    public Window(int x, int y, int width, int height, int color) {
        this(x,y,width,height, color, "");
    }

    public Window(int x, int y, int width, int height, int color, String title) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.title = title;
    }

    public void drawWindow(char ch) {
        if(this.touched()) {
            this.touched = false;
            this.drawBox();
            this.draw(ch);
        }
    }

    protected abstract void draw(char ch);

    private void drawBox() {
        NativeCurses.instance().setColor(color);
        NativeCurses.instance().drawBox(x,y,width,height);

        if(!this.title.isEmpty()) {
            drawCenteredString(x+width/2-1, y, title, title.length()+2, color);
        }
    }

    public void drawString(int x, int y, String s, int width, int color) {
        if(x+width > this.x+this.width) {
            width-=(x+width)-(this.x+this.width);
        }
        if(s.length() > width) s = s.substring(0, width);
        NativeCurses.instance().drawString(s, x, y, color);
        NativeCurses.instance().drawString(" ".repeat(width-s.length()),
                x+s.length(), y, 0);
        this.touch();
    }

    public void drawCenteredString(int x, int y, String s, int width, int color) {
        if(s.length() > width) {
            int overlap = s.length()-width;
            s = s.substring(overlap/2, width-(overlap/2));
        }
        NativeCurses.instance().drawString(s, x-(s.length()/2), y, color);
        int free = width-s.length();
        NativeCurses.instance().drawString(" ".repeat(free/2),
                x-(s.length()/2)-(free/2), y,0);
        NativeCurses.instance().drawString(" ".repeat(free/2),
                x+(s.length()/2)+(free/2), y,0);
        this.touch();
    }

    public void drawSubWindow(Window window, char ch) {
        if(window != null) {
            window.drawWindow(ch);
            this.touch();
        }
    }

    protected void touch() {
        this.touched = true;
    }

    public boolean touched() {
        return this.touched;
    }
}
