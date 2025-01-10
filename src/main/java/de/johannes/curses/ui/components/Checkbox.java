package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.nerdfont.NFCodicons;
import de.johannes.curses.nerdfont.NFOcticons;
import de.johannes.curses.ui.base.BoxComponent;
import de.johannes.curses.ui.base.TextComponent;

public class Checkbox extends BoxComponent {

    private boolean selected;

    public Checkbox() {}

    @Override
    public void init() {}

    @Override
    public void draw() {
        this.drawBox();
        if(selected()) {
            Curses.reverseClearBox(x()+2, y()+1, width()-3, height()-1, color());
        }
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        if(!mouse.check(Mouse.BUTTON1_RELEASED)) {
            setSelected(!selected);
        }
        return true;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean selected() {
        return selected;
    }
}
