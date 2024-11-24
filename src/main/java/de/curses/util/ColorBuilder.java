package de.curses.util;

import de.curses.NativeCurses;

import java.awt.*;
import java.util.Random;

public class ColorBuilder {

    private static int pairindex, colorindex;
    private int foreground, background;

    public ColorBuilder() {
        this.foreground= NativeCurses.WHITE;
        this.background= 0;
    }

    public static ColorBuilder create() {
        return new ColorBuilder();
    }

    public ColorBuilder defineForeground(Color color) {
        this.foreground = NativeCurses.instance().defineColor(20+colorindex,
                color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F);
        colorindex++;
        return this;
    }
    public ColorBuilder defineForeground(String hex) {
        return this.defineForeground(Color.decode(hex));
    }
    public ColorBuilder defineForeground(int color) {
        foreground = color;
        return this;
    }

    public ColorBuilder defineBackground(Color color) {
        this.foreground = NativeCurses.instance().defineColor(20+colorindex,
                color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F);
        colorindex++;
        return this;
    }
    public ColorBuilder defineBackground(String hex) {
        return this.defineBackground(Color.decode(hex));
    }
    public ColorBuilder defineBackground(int color) {
        background = color;
        return this;
    }

    public int build() {
        pairindex++;
        return NativeCurses.instance().defineColorPair(20+pairindex-1, this.foreground, this.background);
    }

    public int buildReverse() {
        pairindex++;
        return NativeCurses.instance().defineColorPair(20+pairindex-1, this.background, this.foreground);
    }

}
