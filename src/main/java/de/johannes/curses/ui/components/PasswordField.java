package de.johannes.curses.ui.components;

public class PasswordField extends TextField {

    @Override
    public void draw() {
        this.drawBox();
        if(input != null) {
            String text = !input.isEmpty() ? "*".repeat(input.length()) : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, color);
            if(blinker.check(1000)) blinker.reset();
        }
    }

}
