package de.johannes.curses.window.components;

import de.johannes.curses.Curses;
import de.johannes.curses.util.Timer;
import de.johannes.curses.window.Component;
import de.johannes.curses.window.Keys;

public class TextInput extends Component {
    protected final String placeholder;
    protected final Window parent;

    public TextInput(Window parent, int x, int y, int width) {
        this(parent, x, y, width, "");
    }

    public TextInput(Window parent, int x, int y, int width, String placeholder) {
        super(parent, x, y, width, 2, false);
        this.input = new StringBuilder();
        this.blinker = new Timer();
        this.placeholder = placeholder;
        this.parent = parent;
        this.color = parent.color;
    }

    protected final StringBuilder input;
    protected final Timer blinker;

    @Override
    public void draw() {
        if (input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1, 1, text + cursor, color);
            if (blinker.check(1000)) blinker.reset();

            if (false) {
                if (!input.isEmpty())
                    drawString(1, 2, String.valueOf((int) input.charAt(input.length() - 1)), color);
            }
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

    public String input() {
        return input.toString();
    }

    public void clear() {
        this.input.setLength(0);
    }

}
