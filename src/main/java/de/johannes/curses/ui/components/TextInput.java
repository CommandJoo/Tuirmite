package de.johannes.curses.ui.components;

import de.johannes.curses.Mouse;
import de.johannes.curses.util.Timer;
import de.johannes.curses.ui.Component;
import de.johannes.curses.Keys;

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
    public void init() {}

    @Override
    public void draw() {
        if (input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1, 1, text + cursor, color);
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
