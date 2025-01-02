package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.Component;

public class Selector extends Component {

    private String[] values;
    private int index;

    public Selector(Window parent, int x, int y, int width, int color, boolean rounded, String... values) {
        super(parent, x, y, width, 2, color, rounded);
        this.values = values;
    }

    @Override
    public void init() {}

    @Override
    public void draw() {
        drawBox(-1);
        Curses.instance().drawArrow(x+1, y+1, 0, color);
        Curses.instance().drawArrow(x+width-1, y+1, 1, color);
        drawCenteredString((width/2), 1, current().substring(0, Math.min(current().length(), width-5)), color);
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
}
