package de.johannes.curses.ui.components;

import de.johannes.curses.Mouse;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.ui.Component;

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
    public void init() {

    }

    public void open() {
        try {
            Desktop.getDesktop().browse(new URI(this.url));
        } catch(Exception ex) {
            color = new ColorBuilder().defineForeground("#FF0000").build();
        }
    }

    @Override
    public void draw() {
        drawString(1,1,"$u"+this.display+"$r", color);
    }

    @Override
    public boolean handleKey(char ch) {return false;}

    @Override
    public boolean handleClick(Mouse mouse) {return false;}
}
