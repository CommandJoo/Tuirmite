package de.johannes.curses.ui.base;

import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.UI;
import de.johannes.curses.ui.components.Window;
import de.johannes.curses.util.ColorBuilder;

public abstract class Component {

    protected Window parent;
    protected int x, y;
    protected int color;

    public Component() {
        this.parent = null;
        this.x = Integer.MIN_VALUE;
        this.y = Integer.MIN_VALUE;
        this.color = CursesConstants.WHITE;
    }

    public Window parent() {
        return parent;
    }

    public int x() {
        return parent != null ? this.parent().x()+this.x : this.x;
    }

    public int y() {
        return parent != null ? this.parent().y()+this.y : this.y;
    }

    public int color() {
        return color;
    }

    public abstract void init();
    public abstract void draw();
    public abstract boolean handleKey(char ch);
    public abstract boolean handleClick(Mouse mouse);

    public void drawString(int x, int y, String s, int color) {
        UI.drawString(s, this.x() + x, this.y() + y, color);
    }
    public void drawStringIndependent(int x, int y, String s, int color) {
        UI.drawString(s, x, y, color);
    }
    public void drawCenteredString(int x, int y, String s, int color) {
        UI.drawCenteredString(s, this.x() + x, this.y() + y, color);
    }
    public void drawCenteredStringIndependent(int x, int y, String s, int color) {
        UI.drawCenteredString(s, x, y, color);
    }

    public Component setColor(int color) {
        this.color = color;
        return this;
    }

    public Component setColor(String hex) {
        this.color = new ColorBuilder().defineForeground(hex).build();
        return this;
    }
}
