package de.johannes.curses.ui;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.ui.components.Window;

public abstract class Component {

    public Window parent;
    public int x;
    public int y;
    public int width;
    public int height;
    public int color;
    public boolean rounded;

    public Component() {

    }

    public Component(Window parent, int x, int y, int width, int height, boolean rounded) {
        this(parent, x, y, width, height, CursesConstants.WHITE, rounded);
    }

    public Component(Window parent, int x, int y, int width, int height, int color, boolean rounded) {
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
        this.rounded = rounded;
    }

    public abstract void init();
    public abstract void draw();
    public abstract boolean handleKey(char ch);
    public abstract boolean handleClick(Mouse mouse);

    public void drawBox(int color) {
        int drawColor = color == -1 ? this.color : color;

        Curses.instance().drawCorner(x, y, 2, drawColor, rounded);
        Curses.instance().drawCorner(x + width, y, 1, drawColor, rounded);
        Curses.instance().drawCorner(x, y + height, 3, drawColor, rounded);
        Curses.instance().drawCorner(x + width, y + height, 0, drawColor, rounded);

        Curses.instance().setColor(drawColor);
        Curses.instance().drawHorizontalLine(y, x + 1, x + width);
        Curses.instance().drawHorizontalLine(y + height, x + 1, x + width);
        Curses.instance().drawVerticalLine(x, y + 1, y + height);
        Curses.instance().drawVerticalLine(x + width, y + 1, y + height);
    }

    public void drawString(int x, int y, String s, int color) {
        UI.drawString(s, this.x + x, this.y + y, color);
    }
    public void drawStringIndependent(int x, int y, String s, int color) {
        UI.drawString(s, x, y, color);
    }
    public void drawCenteredString(int x, int y, String s, int color) {
        UI.drawCenteredString(s, this.x + x, this.y + y, color);
    }
    public void drawCenteredStringIndependent(int x, int y, String s, int color) {
        UI.drawCenteredString(s, x, y, color);
    }

    public void drawDecoration(int x, boolean bottom, boolean parens, String deco, int color) {
        int width = deco.length() + 4;//+4 because of two space characters and two tees
        int correction = deco.length() % 2 == 0 ? 1 : 0;
        if(!parens) {
            Curses.instance().drawTee(this.x + x + correction, bottom ? this.y + this.height : this.y, 1, color);
            drawString(x + 1 + correction, bottom ? this.height : 0, " " + deco + " ", color);
            Curses.instance().drawTee(this.x + x + width - 1 + correction, bottom ? this.y + this.height : this.y, 0, color);
        }else {
            drawString(x + 1 + correction, bottom ? this.height : 0, "( " + deco + " )", color);
        }
    }

    public Window parent() {
        return parent;
    }

    public int x() {
        return x;
    }

    public void setX(int x) {
        this.x = this.parent == null ? x : this.parent.x+x;
    }

    public int y() {
        return y;
    }

    public void setY(int y) {
        this.y = this.parent == null ? y : this.parent.y+y;
    }

    public int width() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public void setColor(String hex) {
        this.color = new ColorBuilder().defineForeground(hex).build();
    }
}
