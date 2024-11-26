package de.curses.window;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.window.components.Window;

public abstract class Component {

    public final Window parent;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public int color;

    public Component(Window parent, int x, int y, int width, int height) {
        this(parent, x, y, width, height, NativeCurses.WHITE);
    }

    public Component(Window parent, int x, int y, int width, int height, int color) {
        this.parent = parent;
        if (this.parent == null) {
            this.x = x;
            this.y = y;
        } else {
            this.x = parent.x + x;
            this.y = parent.y + y;
        }
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void init() {
    }

    public abstract void draw();

    public abstract boolean handleKey(char ch);

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

    public void drawString(int x, int y, String s, int color) {
        NativeCurses.instance().drawString(s, this.x + x, this.y + y, color);
    }

    public void drawStringIndependent(int x, int y, String s, int color) {
        NativeCurses.instance().drawString(s, x, y, color);
    }

    public void drawCenteredString(int x, int y, String s, int color) {
        NativeCurses.instance().drawString(s, this.x + x - (s.length() / 2), this.y + y, color);
    }

    public void drawCenteredStringIndependent(int x, int y, String s, int color) {
        NativeCurses.instance().drawString(s, x - (s.length() / 2), y, color);
    }

    public void drawDecoration(int x, boolean bottom, boolean parens, String deco, int color) {
        int width = deco.length() + 4;//+4 because of two space characters and two tees
        int correction = deco.length() % 2 == 0 ? 1 : 0;
        if(!parens) {
            NativeCurses.instance().drawTee(this.x + x + correction, bottom ? this.y + this.height : this.y, 1, color);
            drawString(x + 1 + correction, bottom ? this.height : 0, " " + deco + " ", color);
            NativeCurses.instance().drawTee(this.x + x + width - 1 + correction, bottom ? this.y + this.height : this.y, 0, color);
        }else {
            drawString(x + 1 + correction, bottom ? this.height : 0, "( " + deco + " )", color);
        }
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setColor(String hex) {
        this.color = new ColorBuilder().defineForeground(hex).build();
    }
}
