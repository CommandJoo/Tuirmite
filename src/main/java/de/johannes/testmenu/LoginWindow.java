package de.johannes.testmenu;

import de.johannes.curses.Curses;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.window.components.Button;
import de.johannes.curses.window.components.TextField;
import de.johannes.curses.window.components.Window;
import de.johannes.Main;

import java.awt.*;

public class LoginWindow extends Window {
    public LoginWindow() {
        super(null,
                Curses.instance().getWidth() / 2 - 30,
                Curses.instance().getHeight() / 2 - 6,
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
        this.enter = (Button) this.addComponent(1, new Button(this, width / 4 * 3, height - 3, width / 4 - 1, 2, color, "Enter"));
    }

    @Override
    public void draw() {
        this.drawCenteredString(width / 2, 3, "To Login to your Account", color);
        this.drawCenteredString(width / 2, 4, "Please enter", color);
        this.drawCenteredString(width / 2, 5, "Your accounts username", color);
        this.drawCenteredString(width / 2, 5, "Into the Text field down below:", color);
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
//            WindowManager.instance().changeWindow(WindowManager.instance().getWindow(1));
            return true;
        }
        return this.handleKeyForSub(this.username, ch);
    }
}
