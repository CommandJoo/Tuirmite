package de.johannes;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.window.PasswordField;
import de.curses.window.TextField;
import de.curses.window.Window;

import javax.print.attribute.standard.MediaSize;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class TestWindow extends Window {

    public TestWindow() {
        super(NativeCurses.instance().getWidth() / 2 - 30, NativeCurses.instance().getHeight() / 2 - 15, 60, 30, ColorBuilder.create().defineForeground(Color.red).build(), "Hello World");
        this.tf = new TextField(this, 1, height - 3, width - 2, "Enter Password:");
        this.lines = new LinkedList<>();
    }

    TextField tf;
    LinkedList<String> lines;

    @Override
    protected void draw() {
        try {
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                drawString(x + 1, y + 1 + i, line, line.length(), color);
            }
        } catch (Exception e) {
        }
        this.drawSubWindow(tf);
    }

    @Override
    public void handleKey(char ch) {
        this.handleKeyForSub(tf, ch);
        if (ch == 10) {
            LocalTime ldt = LocalDateTime.now().toLocalTime();
            String time = ldt.getHour() + ":" + ldt.getMinute() + " " + ldt.getSecond();
            lines.add(time + " (" + Main.username + "): " + this.tf.input());
            this.tf.clear();
        }
    }
}
