package de.johannes.testmenu;

import de.curses.NativeCurses;
import de.curses.WindowManager;
import de.curses.util.ColorBuilder;
import de.curses.window.components.TextField;
import de.curses.window.components.Window;
import de.johannes.Main;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class TestWindow extends Window {

    public TestWindow() {
        super(null,
                NativeCurses.instance().getWidth() / 2 - 30,
                NativeCurses.instance().getHeight() / 2 - 15,
                60,
                30,
                ColorBuilder.create().defineForeground(Color.red).build(),
                "Hello World");
        this.tf = (TextField) this.addComponent(0, new TextField(this, 1, height - 3, width - 2, "Enter Message:", (int)'c'));
        this.lines = new LinkedList<>();
    }

    TextField tf;
    LinkedList<String> lines;

    @Override
    public void draw() {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            drawString(1, 1 + i, line, color);
        }
        this.drawComponent(tf);
    }

    @Override
    public boolean handleKey(char ch) {
        boolean b = this.handleKeyForSub(tf, ch);
        if(!b) {
            if(ch == 27 && !this.tf.isFocused()) {
                Main.username = "";
                WindowManager.instance().changeWindow(WindowManager.instance().getWindow(0));
            }
            if (ch == 10) {
                LocalTime ldt = LocalDateTime.now().toLocalTime();
                String time = ldt.getHour() + ":" + ldt.getMinute() + " " + ldt.getSecond();
                lines.add(time + " (" + Main.username + "): " + this.tf.input());
                this.tf.clear();
            }
        }
        return false;
    }
}
