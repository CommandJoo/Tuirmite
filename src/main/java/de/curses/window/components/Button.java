package de.curses.window.components;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.window.Component;

public class Button extends Component {

    private final String text;
    private boolean selected;

    public Button(Window parent, int x, int y, int width, int height, String text) {
        super(parent, x, y, width, height, parent.color);
        this.text = text;
        this.color = parent.color;
    }

    public Button(Window parent, int x, int y, int width, int height, int color, String text) {
        super(parent, x, y, width, height);
        this.text = text;
        this.color = color;
    }

    @Override
    public void draw() {
        this.drawBox(-1);
        if(selected) {
            NativeCurses.instance().attron(0);
            this.drawCenteredString(width/2, 1, text, color);
            NativeCurses.instance().attroff(0);
        }else {
            this.drawCenteredString(width/2, 1, text, color);
        }

    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean selected() {
        return selected;
    }
}
