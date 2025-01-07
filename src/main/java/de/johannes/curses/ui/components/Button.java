package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.BoxComponent;
import java.util.function.Consumer;

public class Button extends BoxComponent {

    private String text;
    private boolean selected;
    private Consumer<Mouse> executor;
    public Button() {}

    @Override
    public void init() {}

    @Override
    public void draw() {
        this.drawBox();
        if(selected) {
            Curses.instance().attron(CursesConstants.ATTRIB_REVERSE);
            this.drawCenteredString(width/2, height/2, text, color);
            Curses.instance().attroff(CursesConstants.ATTRIB_REVERSE);
        }else {
            this.drawCenteredString(width/2, height/2, text, color);
        }

    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        if(executor() != null) {
            executor().accept(mouse);
            return true;
        }
        return false;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public Button setExecutor(Consumer<Mouse> executor) {
        this.executor = executor;
        return this;
    }

    public Consumer<Mouse> executor() {
        return executor;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean selected() {
        return selected;
    }
}
