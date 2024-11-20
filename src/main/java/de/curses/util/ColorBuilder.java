package de.curses.util;

import de.curses.NativeCurses;

import java.awt.*;
import java.util.Random;

public class ColorBuilder {

    private static int pairindex, colorindex;
    private int colorpair, foreground, background;

    public ColorBuilder() {
        this.foreground= NativeCurses.WHITE;
        this.background= 0;
        this.colorpair = 20+pairindex;
        pairindex++;
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
        return this.defineForeground(Color.getColor(hex));
    }

    public ColorBuilder defineBackground(Color color) {
        this.foreground = NativeCurses.instance().defineColor(20+colorindex,
                color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F);
        colorindex++;
        return this;
    }

    public ColorBuilder defineBackground(String hex) {
        return this.defineBackground(Color.getColor(hex));
    }

    public int build() {
        return NativeCurses.instance().defineColorPair(this.colorpair, this.foreground, this.background);
    }

}
