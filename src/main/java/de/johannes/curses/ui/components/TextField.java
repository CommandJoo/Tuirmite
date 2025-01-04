package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.BoxComponent;
import de.johannes.curses.ui.base.Component;
import de.johannes.curses.util.Timer;
import de.johannes.curses.Keys;

public class TextField extends BoxComponent {
    protected String placeholder;
    protected boolean focused = true;

    protected StringBuilder input;
    protected Timer blinker;

    @Override
    public void init() {
        input = new StringBuilder();
        blinker = new Timer();
    }

    public TextField placeholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @Override
    public void draw() {
        this.drawBox();
        if (input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1, 1, text + cursor, color);
            if (blinker.check(1000)) blinker.reset();

            if (false) {
                if (!input.isEmpty())
                    drawString(1, 2, String.valueOf((int) input.charAt(input.length() - 1)), color);
            }
        }
    }

    @Override
    public void drawBox() {
        Curses.instance().drawCorner(x(), y(), 2, color);
        Curses.instance().drawCorner(x() + width(), y(), 1, color);
        Curses.instance().drawCorner(x(), y() + height(), 3, color);
        Curses.instance().drawCorner(x() + width(), y() + height(), 0, color);

        Curses.instance().drawHorizontalLine(y(), x() + 1, x() + width(), color);
        Curses.instance().drawHorizontalLine(y()+height(), x() + 1, x() + width(), color);
        Curses.instance().drawVerticalLine(x(), y() + 1, y() + height(), color);
        Curses.instance().drawVerticalLine(x() + width(), y() + 1, y() + height(), color);
    }

    @Override
    public boolean handleKey(char ch) {
        if (ch != 0) {
            if (ch == Keys.BACK_SPACE) {
                input.setLength(Math.max(input.length() - 1, 0));
                return true;
            } else if (ch != Keys.ENTER && ch != Keys.ESCAPE && ((int) ch < 258 || (int) ch > 261)) {
                input.append(ch);
                return true;
            } else if (ch == Keys.ESCAPE) {
                this.focused = false;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {return false;}

    public String input() {
        return input.toString();
    }
    public void clear() {
        this.input.setLength(0);
    }
    public boolean isFocused() {
        return focused;
    }
    public void setFocused(boolean b) {
        this.focused = b;
    }
}
