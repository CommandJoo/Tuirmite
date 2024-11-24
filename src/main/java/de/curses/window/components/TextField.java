package de.curses.window.components;

import de.curses.NativeCurses;
import de.curses.util.Timer;
import de.curses.window.Component;
import de.curses.window.Keys;
import de.curses.window.Window;

public class TextField extends Component {
    protected final String placeholder;
    protected final Window parent;
    protected boolean focused = false;

    public TextField(Window parent, int x, int y, int width) {
        this(parent, x,y,width, "");
    }
    public TextField(Window parent, int x, int y, int width, String placeholder) {
        super(parent, x, y, width, 2);
        this.input = new StringBuilder();
        this.blinker = new Timer();
        this.placeholder = placeholder;
        this.parent = parent;
        this.color = parent.color;
        this.focused = false;
    }

    protected final StringBuilder input;
    protected final Timer blinker;
    @Override
    protected void draw() {
        this.drawBox(focused ? -1 : 17);
        if(input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, focused ? color : 1);
            if(blinker.check(1000)) blinker.reset();

            if(false) {
                if(!input.isEmpty()) drawString(1, 2, String.valueOf((int)input.charAt(input.length()-1)), 2, focused ? color : 0);
            }
        }
    }

    @Override
    public void handleKey(char ch) {
        if(ch != 0 && focused) {
            if(ch == Keys.BACK_SPACE) {
                input.setLength(Math.max(input.length() - 1, 0));
            } else if(ch != Keys.ENTER && ch != Keys.ESCAPE){
                input.append(ch);
            }else if(ch == Keys.ESCAPE) {
                this.focused = false;
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
