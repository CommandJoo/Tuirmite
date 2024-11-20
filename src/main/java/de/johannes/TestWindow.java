package de.johannes;

import de.curses.util.ColorBuilder;
import de.curses.window.PasswordField;
import de.curses.window.TextField;
import de.curses.window.Window;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

public class TestWindow extends Window {

    public TestWindow(int x, int y, int width, int height) {
        super(x, y, width, height, ColorBuilder.create().defineForeground(Color.red).build(), "Hello World");
        this.tf = new TextField(x+1, y+height-3, width-2, "Enter Password:");
        this.lines = new LinkedList<>();
    }

    TextField tf;
    LinkedList<String> lines;

    @Override
    protected void draw(char ch) {
        if(ch == 10) {
            LocalTime ldt = LocalDateTime.now().toLocalTime();
            String time = ldt.getHour()+":"+ldt.getMinute()+" "+ldt.getSecond();
            lines.add(time+": "+this.tf.input());
            this.tf.clear();
        }
        for(int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            drawString(x+1,y+1+i, line, line.length(), color);
        }
        this.drawSubWindow(tf, ch);
    }
}
