package de.johannes.curses.window.components;

import de.johannes.curses.Curses;
import de.johannes.curses.window.Component;

public class Selector extends Component {

    private String[] values;
    private int index;

    public Selector(Window parent, int x, int y, int width, int color, String... values) {
        super(parent, x, y, width, 2, color);
        this.values = values;
    }

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
        if(ch == 260) {
            if(index-1 <= 0) index=values.length;
            index--;
            return true;
        }else if(ch==261) {
            index++;
            return true;
        }
        return false;
    }
}
