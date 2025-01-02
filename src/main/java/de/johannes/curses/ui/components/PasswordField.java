package de.johannes.curses.ui.components;

public class PasswordField extends TextField {

    public PasswordField(Window parent, int x, int y, int width, boolean rounded) {
        super(parent, x, y, width, rounded);
    }

    public PasswordField(Window parent, int x, int y, int width, String placeholder, boolean rounded) {
        super(parent, x, y, width, placeholder, rounded);
    }

    public PasswordField(Window parent, int x, int y, int width, String placeholder, int toggleKey, boolean rounded) {
        super(parent, x, y, width, placeholder, toggleKey, rounded);
    }

    @Override
    public void draw() {
        this.drawBox(-1);
        if(input != null) {
            String text = !input.isEmpty() ? "*".repeat(input.length()) : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, color);
            if(blinker.check(1000)) blinker.reset();
        }
    }

}
