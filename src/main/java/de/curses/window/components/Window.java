package de.curses.window.components;

import de.curses.NativeCurses;
import de.curses.window.Component;

public abstract class Window extends Component {

    protected final String title;
    public boolean touched = true;

    public Window(Window parent, int x, int y, int width, int height) {
        this(parent, x, y, width, height, NativeCurses.WHITE);
    }
    public Window(Window parent, int x, int y, int width, int height, String title) {
        this(parent, x, y, width, height, NativeCurses.WHITE, title);
    }
    public Window(Window parent, int x, int y, int width, int height, int color) {
        this(parent, x, y, width, height, color, "");
    }
    public Window(Window parent, int x, int y, int width, int height, int color, String title) {
        super(parent, x, y, width, height, color);
        this.title = title;
        this.init();
    }

    public void drawWindow() {
        if (this.touched()) {
            this.touched = false;
            this.drawBox(-1);
            this.draw();
        }
    }

    @Override
    protected void drawBox(int color) {
        int rendercolor = color == -1 ? this.color : color;
        NativeCurses.instance().setColor(rendercolor);

        NativeCurses.instance().drawCorner(x, y, 2);
        NativeCurses.instance().drawCorner(x + width, y, 1);
        NativeCurses.instance().drawCorner(x, y + height, 3);
        NativeCurses.instance().drawCorner(x + width, y + height, 0);

        if (title.isEmpty()) {
            NativeCurses.instance().drawHorizontalLine(y, x + 1, x + width);
        } else {
            int halfTitle = (title.length() + 3) / 2;
            int correction = title.length() % 2 == 0 ? 1 : 0;
            NativeCurses.instance().drawHorizontalLine(y, x + 1, x + ((width / 2) - halfTitle - 1 - correction));
            NativeCurses.instance().drawHorizontalLine(y, x + ((width / 2) + halfTitle), x + width);
            NativeCurses.instance().drawTee(x + (width / 2) - halfTitle - 1 - correction, y, 1);
            NativeCurses.instance().drawTee(x + (width / 2) + halfTitle - 1, y, 0);
            drawCenteredString(width / 2 - 1, 0, title, title.length(), rendercolor);
        }
        NativeCurses.instance().drawHorizontalLine(y + height, x + 1, x + width);
        NativeCurses.instance().drawVerticalLine(x, y + 1, y + height);
        NativeCurses.instance().drawVerticalLine(x + width, y + 1, y + height);
    }

    @Override
    public void drawCenteredString(int x, int y, String s, int width, int color) {
        super.drawCenteredString(x, y, s, width, color);
        this.touch();
    }
    @Override
    public void drawCenteredStringIndependent(int x, int y, String s, int width, int color) {
        super.drawCenteredStringIndependent(x, y, s, width, color);
        this.touch();
    }
    @Override
    public void drawString(int x, int y, String s, int width, int color) {
        super.drawString(x, y, s, width, color);
        this.touch();
    }
    @Override
    public void drawStringIndependent(int x, int y, String s, int width, int color) {
        super.drawStringIndependent(x, y, s, width, color);
        this.touch();
    }

    public void drawComponent(Component comp) {
        if (comp != null) {
            if(comp instanceof Window window) {
                window.drawWindow();
            }else {
                comp.draw();
            }
            this.touch();
        }
    }

    public boolean handleKeyForSub(Component component, char ch) {
        return component.handleKey(ch);
    }

    protected void touch() {
        this.touched = true;
    }

    public boolean touched() {
        return this.touched;
    }


}
