package de.johannes.curses.window.components;

import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.window.Component;

import java.awt.*;
import java.net.URI;

public class Link extends Component {

    private final String display, url;

    public Link(Window parent, int x, int y, String display, String url, int color) {
        super(parent, x, y, display.length(), 1, color, false);
        this.display = display;
        this.url = url;
    }

    @Override
    public void draw() {
        drawString(1,1,"$u"+this.display+"$r", color);
    }

    @Override
    public boolean handleKey(char ch) {
        if((int) ch == Keys.ENTER) {
            try {
                Desktop.getDesktop().browse(new URI(this.url));
                return true;
            } catch(Exception ex) {
                color = new ColorBuilder().defineForeground("#FF0000").build();
            }
        }
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        if(mouse.check(Mouse.BUTTON3_PRESSED)) {
            return handleKey('\n');
        }
        return false;
    }
}
