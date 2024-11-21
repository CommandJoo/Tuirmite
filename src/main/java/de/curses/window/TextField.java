package de.curses.window;

import de.curses.util.Timer;

public class TextField extends Window{
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
    protected void draw(char ch) {
        if(input != null) {
            if(ch != 0) {
                if(ch == 263) {
                    input.setLength(Math.max(input.length() - 1, 0));
                } else if(ch != 10){
                    input.append(ch);
                }
            }

            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, color);
            if(blinker.check(1000)) blinker.reset();

            if(false) {
                if(!input.isEmpty()) drawString(1, 2, String.valueOf((int)input.charAt(input.length()-1)), 2, color);
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
