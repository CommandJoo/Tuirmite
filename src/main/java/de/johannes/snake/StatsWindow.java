package de.johannes.snake;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.window.components.Selector;
import de.curses.window.components.Window;

import javax.lang.model.element.Name;

public class StatsWindow extends Window {

    public StatsWindow(NativeCurses curses) {
        super(null, curses.getWidth() / 2 - (curses.getWidth() / 3)-20, curses.getHeight() / 2 - (curses.getHeight() / 3), 20, curses.getHeight()/3*2, ColorBuilder.create().defineForeground("#7731AF").build());
    }

    @Override
    public void init() {
        addComponent(0, new Selector(this, 1,1, width-2, color, "Value1234567890", "Value2345678901", "Value3456789", "Bacon", "Cheese", "Value4567890"));
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean handleKey(char ch) {
        this.handleKeyForSub(getComponent(0), ch);
        return false;
    }
}
