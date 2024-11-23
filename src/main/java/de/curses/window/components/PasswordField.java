package de.curses.window.components;

import de.curses.window.Window;

public class PasswordField extends TextField {

    public PasswordField(Window parent, int x, int y, int width) {
        super(parent, x, y, width);
    }

    public PasswordField(Window parent, int x, int y, int width, String placeholder) {
        super(parent, x, y, width, placeholder);
    }

    @Override
    protected void draw() {
        if(input != null) {
            String text = !input.isEmpty() ? "*".repeat(input.length()) : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, color);
            if(blinker.check(1000)) blinker.reset();
        }
    }

}
