package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.BoxComponent;

public class Selector extends BoxComponent {

    private String[] values;
    private int index;

    public Selector values(String... values) {
        this.values = values;
        return this;
    }

    @Override
    public void init() {}

    @Override
    public void draw() {
        drawBox();
        Curses.instance().drawArrow(x()+1, y()+1, 0, color);
        Curses.instance().drawArrow(x()+width()-1, y()+1, 1, color);
        drawCenteredString((width()/2), 1, current().substring(0, Math.min(current().length(), width()-5)), color);
    }

    public String current() {
        return values[index%values.length];
    }

    @Override
    public boolean handleKey(char ch) {
        if(ch == Keys.KEY_LEFT) {
            if(index-1 <= 0) index=values.length;
            index--;
            return true;
        }else if(ch==Keys.KEY_RIGHT) {
            index++;
            return true;
        }
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }

    @Override
    public boolean handleHover(int x, int y) {
        return false;
    }
}
