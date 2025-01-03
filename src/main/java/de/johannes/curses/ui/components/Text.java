package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.Component;
import de.johannes.curses.util.ColorBuilder;

import java.util.ArrayList;
import java.util.List;

public class Text extends Component {

    private String text;
    private List<Integer> attributes;
    private List<Text> chain;

    public Text(String text) {
        super(null,Integer.MIN_VALUE, Integer.MIN_VALUE, 0, 0, -1, false);
        this.text = text;
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

    public Text parent(Window parent) {
        this.parent = parent;
        return this;
    }

    public Text append(Text text) {
        this.chain.add(text);
        return this;
    }


    @Override
    public void init() {}

    public void draw() {
        int x = this.x;
        int y = this.y;
        if(this.parent != null) {
            x = this.x + this.parent.x;
            y = this.y + this.parent.y;
        }
        if(x != Integer.MIN_VALUE && y != Integer.MIN_VALUE) {
            Curses.instance().moveCursor(x,y);
        }
        if(color != -1) {
            Curses.instance().setColor(color);
        }

        for(int i : attributes) {
            Curses.instance().attron(i);
        }
        Curses.instance().printstr(text);

        int xPos = text.length();
        for(Text txt : this.chain) {
            if(x != Integer.MIN_VALUE && y != Integer.MIN_VALUE) {
                Curses.instance().moveCursor(x+xPos,y);
            }
            if(txt.color != -1) {
                Curses.instance().setColor(txt.color);
            }

            for(int i : txt.attributes) {
                Curses.instance().attron(i);
            }
            Curses.instance().printstr(txt.text);
            xPos+=txt.text.length();
        }

    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }

}
