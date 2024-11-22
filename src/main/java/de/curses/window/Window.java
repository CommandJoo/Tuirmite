package de.curses.window;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;

import java.io.IOException;
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
        this.init();
    }

    public void init() {}

    public void drawWindow() throws IOException {
        if(this.touched()) {
            this.touched = false;
            this.drawBox();
            this.draw();
        }
    }

    protected abstract void draw() throws IOException;

    public void handleKey(char ch) {

    }


    private void drawBox() {
        NativeCurses.instance().setColor(color);

        NativeCurses.instance().drawCorner(x,y,2);
        NativeCurses.instance().drawCorner(x+width,y,1);
        NativeCurses.instance().drawCorner(x,y+height,3);
        NativeCurses.instance().drawCorner(x+width,y+height,0);

        if(title.isEmpty()) {
            NativeCurses.instance().drawHorizontalLine(y, x+1, x+width);
        }else {
            int halfTitle = (title.length()+3)/2;
            int correction = title.length()%2==0 ? 1 : 0;
            NativeCurses.instance().drawHorizontalLine(y, x+1, x+((width/2)-halfTitle-1-correction));
            NativeCurses.instance().drawHorizontalLine(y, x+((width/2)+halfTitle), x+width);
            NativeCurses.instance().drawTee(x+(width/2)-halfTitle-1-correction, y, 1);
            NativeCurses.instance().drawTee(x+(width/2)+halfTitle-1, y, 0);
            drawCenteredString(width/2-1, 0, title, title.length(), color);
        }
        NativeCurses.instance().drawHorizontalLine(y+height, x+1, x+width);
        NativeCurses.instance().drawVerticalLine(x, y+1, y+height);
        NativeCurses.instance().drawVerticalLine(x+width, y+1, y+height);
    }

    public void drawString(int x, int y, String s, int width, int color) {
        if(width < 0) return;
        if(this.x+x+width > this.x+this.width) {
            width-=(this.width-x);
        }
        if(s.length() > width) s = s.substring(0, width);
        NativeCurses.instance().drawString(s, this.x+x, this.y+y, color);
        NativeCurses.instance().drawString(" ".repeat(width-s.length()),
                this.x+x+s.length(), this.y+y, 0);
        this.touch();
    }

    public void drawCenteredString(int x, int y, String s, int width, int color) {
        if(width==-1) {
            NativeCurses.instance().drawString(s, this.x+x-(s.length()/2), this.y+y, color);
            return;
        }
        if(s.length() > width) {
            int overlap = s.length()-width;
            s = s.substring(overlap/2, width-(overlap/2));
        }
        NativeCurses.instance().drawString(s, this.x+x-(s.length()/2), this.y+y, color);
        int free = width-s.length();
        NativeCurses.instance().drawString(" ".repeat(free/2),
                this.x+x-(s.length()/2)-(free/2), this.y+y,0);
        NativeCurses.instance().drawString(" ".repeat(free/2),
                this.x+x+(s.length()/2)+(free/2), this.y+y,0);
        this.touch();
    }

    public void drawSubWindow(Window window) throws IOException {
        if(window != null) {
            window.drawWindow();
            this.touch();
        }
    }

    public void handleKeyForSub(Window window, char ch) {
        window.handleKey(ch);
    }

    protected void touch() {
        this.touched = true;
    }

    public boolean touched() {
        return this.touched;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(String hex) {
        this.color = new ColorBuilder().defineForeground(hex).build();
    }

}
