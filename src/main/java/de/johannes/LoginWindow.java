package de.johannes;

import de.curses.NativeCurses;
import de.curses.WindowManager;
import de.curses.util.ColorBuilder;
import de.curses.util.Files;
import de.curses.window.components.Button;
import de.curses.window.components.TextField;
import de.curses.window.components.Window;

import java.awt.*;
import java.io.File;

public class LoginWindow extends Window {
    public LoginWindow() {
        super(null,
                NativeCurses.instance().getWidth() / 2 - 30,
                NativeCurses.instance().getHeight() / 2 - 6,
                60,
                12,
                ColorBuilder.create().defineForeground(new Color(70, 200, 150)).build(),
                "Login");
    }

    private TextField username;
    private Button enter;

    @Override
    public void init() {
        super.init();
        this.username = (TextField) this.addComponent(0, new TextField(this, 1, height - 3, (width / 4) * 3 - 2, "Username"));
        this.enter = (Button) this.addComponent(1, new Button(this, width / 4 * 3, height - 3, width / 4 - 1, 2, color, ColorBuilder.create().defineForeground(new Color(70, 200, 150)).buildReverse(), "Enter"));
    }

    @Override
    public void draw() {
        this.drawCenteredString(width / 2, 3, "To Login to your Account", -1, color);
        this.drawCenteredString(width / 2, 4, "Please enter", -1, color);
        this.drawCenteredString(width / 2, 5, "Your accounts username", -1, color);
        this.drawCenteredString(width / 2, 6, "Into the Text field down below:",  -1, color);
        if(selected() != null) this.selected().setSelected(true);
    }

    @Override
    public boolean handleKey(char ch) {
        boolean b = this.handleButtonKeys(ch);
        if(b) return true;
        if (ch == 10 && selected() != null) {
            this.setSelected(null);
            Main.username = this.username.input();
            this.username.clear();
            WindowManager.instance().changeWindow(WindowManager.instance().getWindow(1));
            return true;
        }
        return this.handleKeyForSub(this.username, ch);
    }
}
