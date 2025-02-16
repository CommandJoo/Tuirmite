package de.johannes.curses.ui.base;

import de.johannes.curses.Curses;
import de.johannes.curses.ui.UI;
import de.johannes.curses.util.Pair;

public abstract class BoxComponent extends Component {

    protected int width, height;
    protected boolean rounded;

    public BoxComponent() {
        super();
        this.width = 0;
        this.height = 0;
        this.rounded = false;
    }

    public int width() {
        return width;
    }
    public int height() {
        return height;
    }

    public void drawString(int x, int y, String s, int color) {
        UI.drawString(s, this.x() + x, this.y() + y, color);
    }
    public void drawCenteredString(int x, int y, String s, int color) {
        UI.drawCenteredString(s, this.x() + x, this.y() + y, color);
    }

    public void drawBox() {
        Curses.instance().drawHorizontalLine(y(),x(),x()+width(), color);
        Curses.instance().drawHorizontalLine(y()+height(),x(),x()+width(), color);

        Curses.instance().drawVerticalLine(x(), y(), y()+height(), color);
        Curses.instance().drawVerticalLine(x()+width(), y(), y()+height(), color);

        Curses.instance().drawCorner(x(), y(), 2, color, rounded);
        Curses.instance().drawCorner(x()+width(), y(), 1, color, rounded);
        Curses.instance().drawCorner(x(), y()+height(), 3, color, rounded);
        Curses.instance().drawCorner(x()+width(), y()+height(), 0, color, rounded);
    }
    public void drawDecoration(int x, boolean bottom, boolean parens, String deco, int color) {
        int width = deco.length() + 4;//+4 because of two space characters and two tees
        int correction = deco.length() % 2 == 0 ? 1 : 0;
        if(!parens) {
            Curses.instance().drawTee(this.x() + x + correction, bottom ? this.y() + this.height() : this.y(), 1, color);
            drawString(x + 1 + correction, bottom ? this.height() : 0, " " + deco + " ", color);
            Curses.instance().drawTee(this.x() + x + width - 1 + correction, bottom ? this.y() + this.height() : this.y(), 0, color);
        }else {
            drawString(x + 1 + correction, bottom ? this.height() : 0, "( " + deco + " )", color);
        }
    }

    public boolean inBounds(int x, int y) {
        return x >= this.x() && y >= this.y() && x <= this.x()+this.width && y <= this.y()+this.height;
    }

    public Pair<Integer, Integer> insideCoordinates(int realX, int realY) {
        return new Pair<>(realX-this.x, realY-this.y);
    }

    @Override
    public boolean handleHover(int x, int y) {
        if(inBounds(x,y)) {
            setColor(hoverColor);
        }else {
            setColor(originalColor);
        }
        return false;
    }
}

