package de.johannes.curses.ui.base;

import de.johannes.curses.Curses;

public abstract class BoxComponent extends Component {

    protected int width, height;

    public BoxComponent() {
        super();
        this.width = 0;
        this.height = 0;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void drawBox() {
        Curses.instance().drawHorizontalLine(y(),x(),x()+width(), color);
        Curses.instance().drawHorizontalLine(y()+height(),x(),x()+width(), color);

        Curses.instance().drawVerticalLine(x(), y(), y()+height(), color);
        Curses.instance().drawVerticalLine(x()+width(), y(), y()+height(), color);

        Curses.instance().drawCorner(x(), y(), 2, color);
        Curses.instance().drawCorner(x()+width(), y(), 1, color);
        Curses.instance().drawCorner(x(), y()+height(), 3, color);
        Curses.instance().drawCorner(x()+width(), y()+height(), 0, color);
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
}

