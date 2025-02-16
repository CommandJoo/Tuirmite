package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.TextComponent;
import de.johannes.curses.util.ColorBuilder;

import java.util.ArrayList;
import java.util.List;

public class Text extends TextComponent {

    private String text;
    private List<Integer> attributes;
    private List<Text> chain;

    public Text() {
        this.attributes = new ArrayList<>();
        this.chain = new ArrayList<>();
    }

    public static Text of(String text) {
        Text txt = new Text();
        txt.text = text;
        return txt;
    }

    public Text format(int color) {
        this.color = color;
        return this;
    }

    public Text format(String hex) {
        return this.format(new ColorBuilder().defineForeground(hex).build());
    }

    public Text format(ColorBuilder color) {
        this.color = color.build();
        return this;
    }

    public Text at(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Text attrib(int attribute) {
        this.attributes.add(attribute);
        return this;
    }

    public Text parent(Window parent) {
        this.parent = parent;
        return this;
    }

    public Text append(Text text) {
        this.chain.add(text);
        return this;
    }


    @Override
    public void init() {
    }

    public void draw() {
        drawText();
    }

    private int drawText() {
        int result = text.length();

        if (x() != Integer.MIN_VALUE && y() != Integer.MIN_VALUE) {
            Curses.instance().moveCursor(x(), y());
        }
        if (color != -1) {
            Curses.instance().setColor(color);
        }

        for (int i : attributes) {
            Curses.instance().attron(i);
        }
        Curses.instance().printstr(text);

        alloff();

        for(Text txt : this.chain) {
            txt.at(x()+result, y());
            txt.drawText();
            result += txt.text.length();
        }

        return result;
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }

    @Override
    public boolean handleHover(int x, int y) {
        return false;
    }

    private static void alloff() {
        Curses curses = Curses.instance();
        curses.attroff(CursesConstants.ATTRIB_REVERSE);
        curses.attroff(CursesConstants.ATTRIB_DIM);
        curses.attroff(CursesConstants.ATTRIB_ITALIC);
        curses.attroff(CursesConstants.ATTRIB_INVIS);
        curses.attroff(CursesConstants.ATTRIB_BLINK);
        curses.attroff(CursesConstants.ATTRIB_UNDERLINE);
    }

}
