package de.curses.window;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;

public abstract class Component {

    public final Window parent;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public int color;

    public Component(Window parent, int x, int y, int width, int height) {
        this(parent, x,y,width,height,NativeCurses.WHITE);
    }
    public Component(Window parent, int x, int y, int width, int height, int color) {
        this.parent = parent;
        if(this.parent == null) {
            this.x = x;
            this.y = y;
        }else {
            this.x = parent.x+x;
            this.y = parent.y+y;
        }
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void init() {}
    protected abstract void draw();
    public abstract void handleKey(char ch);

    protected void drawBox(int color) {
            NativeCurses.instance().setColor(color == -1 ? this.color : color);

            NativeCurses.instance().drawCorner(x, y, 2);
            NativeCurses.instance().drawCorner(x + width, y, 1);
            NativeCurses.instance().drawCorner(x, y + height, 3);
            NativeCurses.instance().drawCorner(x + width, y + height, 0);

            NativeCurses.instance().drawHorizontalLine(y, x + 1, x + width);

            NativeCurses.instance().drawHorizontalLine(y + height, x + 1, x + width);
            NativeCurses.instance().drawVerticalLine(x, y + 1, y + height);
            NativeCurses.instance().drawVerticalLine(x + width, y + 1, y + height);
    }

    public void drawString(int x, int y, String s, int width, int color) {
        if (width < 0) return;
        if (this.x + x + width > this.x + this.width) {
            width -= (this.width - x);
        }
        if (s.length() > width) s = s.substring(0, width);
        NativeCurses.instance().drawString(s, this.x + x, this.y + y, color);
        NativeCurses.instance().drawString(" ".repeat(width - s.length()),
                this.x + x + s.length(), this.y + y, 0);
    }
    public void drawStringIndependent(int x, int y, String s, int width, int color) {
        if (width < 0) return;
        if (s.length() > width) s = s.substring(0, width);
        NativeCurses.instance().drawString(s, x, y, color);
        NativeCurses.instance().drawString(" ".repeat(width - s.length()),
                x + s.length(), y, 0);
    }

    public void drawCenteredString(int x, int y, String s, int width, int color) {
        if (width == -1) {
            NativeCurses.instance().drawString(s, this.x + x - (s.length() / 2), this.y + y, color);
            return;
        }
        if (s.length() > width) {
            int overlap = s.length() - width;
            s = s.substring(overlap / 2, width - (overlap / 2));
        }
        NativeCurses.instance().drawString(s, this.x + x - (s.length() / 2), this.y + y, color);
        int free = width - s.length();
        NativeCurses.instance().drawString(" ".repeat(free / 2),
                this.x + x - (s.length() / 2) - (free / 2), this.y + y, 0);
        NativeCurses.instance().drawString(" ".repeat(free / 2),
                this.x + x + (s.length() / 2) + (free / 2), this.y + y, 0);
    }
    public void drawCenteredStringIndependent(int x, int y, String s, int width, int color) {
        if (width == -1) {
            NativeCurses.instance().drawString(s, x - (s.length() / 2), y, color);
            return;
        }
        if (s.length() > width) {
            int overlap = s.length() - width;
            s = s.substring(overlap / 2, width - (overlap / 2));
        }
        NativeCurses.instance().drawString(s, x - (s.length() / 2), y, color);
        int free = width - s.length();
        NativeCurses.instance().drawString(" ".repeat(free / 2),
                x - (s.length() / 2) - (free / 2), y, 0);
        NativeCurses.instance().drawString(" ".repeat(free / 2),
                x + (s.length() / 2) + (free / 2), y, 0);
    }


    public void setColor(int color) {
        this.color = color;
    }
    public void setColor(String hex) {
        this.color = new ColorBuilder().defineForeground(hex).build();
    }
}
