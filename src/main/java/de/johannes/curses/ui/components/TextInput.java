package de.johannes.curses.ui.components;

import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.Component;
import de.johannes.curses.ui.base.TextComponent;
import de.johannes.curses.util.Timer;
import de.johannes.curses.Keys;

public class TextInput extends TextComponent {
    protected String placeholder;

    protected StringBuilder input;
    protected Timer blinker;

    @Override
    public void init() {
        input = new StringBuilder();
        blinker = new Timer();
    }

    public TextInput placeholder(String placeholder) {
        this.placeholder = placeholder;
        return this;
    }

    @Override
    public void draw() {
        if (input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(0, 0, text + cursor, color);
            if (blinker.check(1000)) blinker.reset();
        }
    }

    @Override
    public boolean handleKey(char ch) {
        if (ch != 0) {
            if (ch == Keys.BACK_SPACE) {
                input.setLength(Math.max(input.length() - 1, 0));
                return true;
            } else if (ch != Keys.ENTER && ch != Keys.ESCAPE && ((int) ch < 258 || (int) ch > 261)) {
                input.append(ch);
                return true;
            } else if (ch == Keys.ESCAPE) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {return false;}

    public String input() {
        return input.toString();
    }
    public void clear() {
        this.input.setLength(0);
    }

}
