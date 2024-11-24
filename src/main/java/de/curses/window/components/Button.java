package de.curses.window.components;

import de.curses.window.Component;

public class Button extends Component {

    private final String text;

    public Button(Window parent, int x, int y, int width, int height, String text) {
        super(parent, x, y, width, height, parent.color);
        this.text = text;
    }

    public Button(Window parent, int x, int y, int width, int height, int color, String text) {
        super(parent, x, y, width, height);
        this.text = text;
    }

    @Override
    public void draw() {
        this.drawBox(-1);
        this.drawCenteredString(width/2, 1, text, text.length(), color);
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }
}
