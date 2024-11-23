package de.johannes;

import de.curses.NativeCurses;
import de.curses.WindowManager;
import de.curses.util.ColorBuilder;
import de.curses.window.components.TextField;
import de.curses.window.Window;

import java.awt.*;

public class LoginWindow extends Window {
    public LoginWindow() {
        super(NativeCurses.instance().getWidth()/2-30, NativeCurses.instance().getHeight()/2-6, 60, 12, ColorBuilder.create().defineForeground(new Color(70, 200, 150)).build(), "Login");
    }

    private TextField username;

    @Override
    public void init() {
        super.init();
        this.username = new TextField(this, 1, height-3, width-2);
    }

    @Override
    protected void draw()  {
        this.drawSubWindow(username);
        this.drawCenteredString(width/2, 3, "To Login to your Account", -1, color);
        this.drawCenteredString(width/2, 4, "Please enter", -1, color);
        this.drawCenteredString(width/2, 5, "Your accounts username", -1, color);
        this.drawCenteredString(width/2, 6, "Into the Textfield down below:", -1, color);
    }

    @Override
    public void handleKey(char ch) {
        if(ch == 10) {
            Main.username = this.username.input();
            this.username.clear();
            WindowManager.instance().changeWindow(WindowManager.instance().getWindow(1));
        }
        this.handleKeyForSub(this.username, ch);
    }
}
