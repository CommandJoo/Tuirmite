package de.johannes.example;

import de.johannes.curses.Curses;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.window.components.Window;

import java.awt.*;

public class ColorTest extends Window {
    public ColorTest() {
        super(null, 0, 0, Curses.width() - 1, Curses.height() - 1);
    }

    @Override
    public void draw() {
        for (int i = 0; i < Curses.width()-2; i++) {
            float hue = (1F / (Curses.width()-2)) * i;
                drawString(1 + i, 1, " ", new ColorBuilder().defineBackground(Color.getHSBColor(hue, 1, 1)).build());
        }
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }
}

