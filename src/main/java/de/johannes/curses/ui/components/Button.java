package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.Component;

public class Button extends Component {

    private final String text;
    private boolean selected;

    public Button(Window parent, int x, int y, int width, int height, String text, boolean rounded) {
        super(parent, x, y, width, height, parent.color, rounded);
        this.text = text;
        this.color = parent.color;
    }

    public Button(Window parent, int x, int y, int width, int height, int color, String text, boolean rounded) {
        super(parent, x, y, width, height, rounded);
        this.text = text;
        this.color = color;
    }

    @Override
    public void init() {}

    @Override
    public void draw() {
        this.drawBox(-1);
        if(selected) {
            Curses.instance().attron(CursesConstants.ATTRIB_REVERSE);
            this.drawCenteredString(width/2, height/2, text, color);
            Curses.instance().attroff(CursesConstants.ATTRIB_REVERSE);
        }else {
            this.drawCenteredString(width/2, height/2, text, color);
        }

    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }
    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean selected() {
        return selected;
    }
}
