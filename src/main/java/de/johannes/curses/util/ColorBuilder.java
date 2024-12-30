package de.johannes.curses.util;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;

import java.awt.*;
import java.util.HashMap;

public class ColorBuilder {

    private static final HashMap<Integer, Integer> colors = new HashMap<>();
    private static final HashMap<Pair<Integer, Integer>, Integer> pairs = new HashMap<>();

    private static int pairIndex, colorIndex;
    private int foreground, background;

    public ColorBuilder() {
        this.foreground= CursesConstants.WHITE;
        this.background= 0;

    }

    public static ColorBuilder create() {
        return new ColorBuilder();
    }

    public ColorBuilder defineForeground(Color color) {
        if(colors.containsKey(color.getRGB())) {
            this.foreground = colors.get(color.getRGB());
        }else {
            this.foreground = colors.getOrDefault(color.getRGB(), Curses.instance().defineColor(20+ colorIndex,
                    color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F));
            colors.put(color.getRGB(), this.foreground);
            colorIndex++;
        }
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
        if(colors.containsKey(color.getRGB())) {
            this.background = colors.get(color.getRGB());
        }else {
            this.background = Curses.instance().defineColor(20+ colorIndex,
                    color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F);
            colors.put(color.getRGB(), this.background);
            colorIndex++;
        }
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
        for(Pair<Integer, Integer> pair : pairs.keySet()) {
            if(pair.value1() == this.foreground && pair.value2() == this.background) return pairs.get(pair);
        }
        pairIndex++;
        pairs.put(new Pair<>(this.foreground, this.background), 20+ pairIndex -1);
        return Curses.instance().defineColorPair(20+ pairIndex -1, this.foreground, this.background);
    }

    public int rebuild(int i) {
        pairs.put(new Pair<>(this.foreground, this.background),i);
        return Curses.instance().defineColorPair(i, this.foreground, this.background);
    }

    public static int size() {
        return pairs.size();
    }

}
