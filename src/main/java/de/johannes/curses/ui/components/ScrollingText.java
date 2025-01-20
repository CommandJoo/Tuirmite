package de.johannes.curses.ui.components;

import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.TextComponent;
import de.johannes.curses.util.Timer;

public class ScrollingText extends TextComponent {

    private int speed = 100;

    @Override
    public void init() {
        this.scrollTimer = new Timer();
    }

    public ScrollingText scrollSpeedMillis(int millis) {
        this.speed = millis;
        return this;
    }

    private Timer scrollTimer;
    private int offset = 0;

    @Override
    public void draw() {
        if(offset+width < text.length() && scrollTimer.check(speed)) {
            offset++;
            scrollTimer.reset();
        }
        else if(offset+width >= text.length() && scrollTimer.check(15*speed)) {
            offset = -15;
            scrollTimer.reset();
        }
        drawString(0,0, text.substring(Math.max(0,offset), Math.max(0,offset)+width), color);
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }

    public ScrollingText width(int width) {
        this.width = width;
        return this;
    }
}
