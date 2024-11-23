package de.curses.window.components;

import de.curses.util.Timer;
import de.curses.window.Keys;
import de.curses.window.Window;

public class TextField extends Window {
    protected final String placeholder;
    protected final Window parent;

    public TextField(Window parent, int x, int y, int width) {
        this(parent, x,y,width, "");
    }

    public TextField(Window parent, int x, int y, int width, String placeholder) {
        super(parent.x+x, parent.y+y, width, 2);
        this.input = new StringBuilder();
        this.blinker = new Timer();
        this.placeholder = placeholder;
        this.parent = parent;
        this.color = parent.color;
    }

    protected final StringBuilder input;
    protected final Timer blinker;
    @Override
    protected void draw() {
        if(input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, color);
            if(blinker.check(1000)) blinker.reset();

            if(true) {
                if(!input.isEmpty()) drawString(1, 2, String.valueOf((int)input.charAt(input.length()-1)), 2, color);
            }
        }
    }

    @Override
    public void handleKey(char ch) {
        if(ch != 0) {
            if(ch == Keys.BACK_SPACE) {
                input.setLength(Math.max(input.length() - 1, 0));
            } else if(ch != Keys.ENTER && ch != Keys.DOUBLE_ESCAPE){
                input.append(ch);
            }
        }
    }

    public String input() {
        return input.toString();
    }

    public void clear() {
        this.input.setLength(0);
    }
}
