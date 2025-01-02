package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.ui.Component;
import de.johannes.curses.util.ColorBuilder;

import java.util.ArrayList;
import java.util.List;

public class Text {

    private String text;
    private int x, y;
    private int color;
    private List<Integer> attributes;
    private List<Text> chain;

    public Text(String text) {
        this.text = text;
        this.x = Integer.MIN_VALUE;
        this.y = Integer.MIN_VALUE;
        this.color = CursesConstants.WHITE;
        this.attributes = new ArrayList<>();
        this.chain = new ArrayList<>();
    }

    public static Text of(String text) {
        return new Text(text);
    }

    public Text format(int color) {
        this.color = color;
        return this;
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

    public Text append(Text text) {
        this.chain.add(text);
        return this;
    }


    public void draw() {
        if(this.x != Integer.MIN_VALUE && this.y != Integer.MIN_VALUE) {
            Curses.instance().moveCursor(this.x,this.y);
        }
        Curses.instance().setColor(color);
        for(int i : attributes) {
            Curses.instance().attron(i);
        }
        Curses.instance().printstr(text);

        int xPos = text.length();
        for(Text txt : this.chain) {
            if(this.x != Integer.MIN_VALUE && this.y != Integer.MIN_VALUE) {
                Curses.instance().moveCursor(this.x+xPos,this.y);
            }
            txt.draw();
            xPos+=txt.text.length();
        }

    }

    public void draw(int x, int y) {
        Curses.instance().moveCursor(x,y);
        for(int i : attributes) {
            Curses.instance().attron(i);
        }
        Curses.instance().drawString(text, x,y,color);

        int xPos = text.length();
        for(Text txt : this.chain) {
            txt.draw(x+xPos,y);
            xPos+=txt.text.length();
        }
    }

    public void draw(Component parent, int x, int y) {
        draw(x+parent.x,y+parent.y);
    }

}
