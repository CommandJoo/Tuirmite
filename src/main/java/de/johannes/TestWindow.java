package de.johannes;

import de.curses.NativeCurses;
import de.curses.window.PasswordField;
import de.curses.window.TextField;
import de.curses.window.Window;

public class TestWindow extends Window {

    public TestWindow(int x, int y, int width, int height) {
        super(x, y, width, height, NativeCurses.instance().defineColor(100, 1, 0.1F, 0.6F), "Hello World");
        this.tf = new PasswordField(x+1, y+height-3, width-2, "Enter Password:");
    }

    TextField tf;

    @Override
    protected void draw(char ch) {
        this.drawString(3,3, "Input: "+tf.input(), width, 100);
        this.drawSubWindow(tf, ch);
    }
}
