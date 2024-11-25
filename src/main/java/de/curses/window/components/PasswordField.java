package de.curses.window.components;

public class PasswordField extends TextField {

    public PasswordField(Window parent, int x, int y, int width) {
        super(parent, x, y, width);
    }

    public PasswordField(Window parent, int x, int y, int width, String placeholder) {
        super(parent, x, y, width, placeholder);
    }

    public PasswordField(Window parent, int x, int y, int width, String placeholder, int toggleKey) {
        super(parent, x, y, width, placeholder, toggleKey);
    }

    @Override
    public void draw() {
        if(input != null) {
            String text = !input.isEmpty() ? "*".repeat(input.length()) : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, color);
            if(blinker.check(1000)) blinker.reset();
        }
    }

}
