package de.curses.window.components;

import de.curses.NativeCurses;
import de.curses.util.Timer;
import de.curses.window.Component;
import de.curses.window.Keys;

public class TextField extends Component {
    protected final String placeholder;
    protected final Window parent;
    private final int toggleKey;
    protected boolean focused;

    public TextField(Window parent, int x, int y, int width) {
        this(parent, x,y,width, "");
    }
    public TextField(Window parent, int x, int y, int width, String placeholder) {
        this(parent,x,y,width,placeholder,-1);
    }

    public TextField(Window parent, int x, int y, int width, String placeholder, int toggleKey) {
        super(parent, x, y, width, 2);
        this.input = new StringBuilder();
        this.blinker = new Timer();
        this.placeholder = placeholder;
        this.parent = parent;
        this.color = parent.color;
        this.toggleKey = toggleKey;
        this.focused = false;
    }

    protected final StringBuilder input;
    protected final Timer blinker;
    @Override
    public void draw() {
        this.drawBox((focused||toggleKey==-1) ? -1 : 17);
        if(input != null) {
            String text = !input.isEmpty() ? input.toString() : placeholder;
            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, (focused||toggleKey==-1) ? color : 17);
            if(blinker.check(1000)) blinker.reset();

            if(false) {
                if(!input.isEmpty()) drawString(1, 2, String.valueOf((int)input.charAt(input.length()-1)), 2, (focused||toggleKey==-1) ? color : 17);
            }
        }
    }

    @Override
    protected void drawBox(int color) {
        int rendercolor = color == -1 ? this.color : color;
        NativeCurses.instance().setColor(rendercolor);

        NativeCurses.instance().drawCorner(x, y, 2);
        NativeCurses.instance().drawCorner(x + width, y, 1);
        NativeCurses.instance().drawCorner(x, y + height, 3);
        NativeCurses.instance().drawCorner(x + width, y + height, 0);

        if (toggleKey < 0) {
            NativeCurses.instance().drawHorizontalLine(y+height, x + 1, x + width);
        } else {
            String render = ""+(char)toggleKey;
            int halfTitle = (render.length() + 3) / 2;
            int correction = render.length() % 2 == 0 ? 1 : 0;
            NativeCurses.instance().drawHorizontalLine(y+height, x + 1, x + ((width / 8) - halfTitle - 1 - correction));
            NativeCurses.instance().drawHorizontalLine(y+height, x + ((width / 8) + halfTitle), x + width);
            NativeCurses.instance().drawTee(x + (width / 8) - halfTitle - 1 - correction, y+height, 1);
            NativeCurses.instance().drawTee(x + (width / 8) + halfTitle - 1, y+height, 0);
            drawCenteredString(width / 8 - 1, height, render, render.length(), rendercolor);
        }
        NativeCurses.instance().drawHorizontalLine(y, x + 1, x + width);
        NativeCurses.instance().drawVerticalLine(x, y + 1, y + height);
        NativeCurses.instance().drawVerticalLine(x + width, y + 1, y + height);
    }

    @Override
    public boolean handleKey(char ch) {
        if(ch == (char)toggleKey && !focused) {
            this.setFocused(true);
            return true;
        }
        if(ch != 0 && (focused||toggleKey==-1)) {
            if(ch == Keys.BACK_SPACE) {
                input.setLength(Math.max(input.length() - 1, 0));
                return true;
            } else if(ch != Keys.ENTER && ch != Keys.ESCAPE){
                input.append(ch);
                return true;
            }else if(ch == Keys.ESCAPE) {
                this.focused = false;
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

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean b) {
        this.focused = b;
    }
}
