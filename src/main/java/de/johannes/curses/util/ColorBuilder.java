package de.johannes.curses.util;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.ui.WindowManager;

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
        if(WindowManager.instance().roundColors()) {
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            String rounded = roundColor(hex);
            color = Color.decode(rounded);
        }
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
        return this.defineForeground(Color.decode(roundColor(hex)));
    }
    public ColorBuilder defineForeground(int color) {
        foreground = color;
        return this;
    }

    public ColorBuilder defineBackground(Color color) {
        if(WindowManager.instance().roundColors()) {
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            String rounded = roundColor(hex);
            color = Color.decode(rounded);
        }
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
        return this.defineBackground(Color.decode(roundColor(hex)));
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

    private String roundColor(String hex) {
        String hexCodes = "0123456789ABCDEF";
        for(int i = 1; i < hex.length(); i++) {
            char ch = hex.toUpperCase().charAt(i);
            int value = hexCodes.indexOf(ch);
            if(value%2!=0) {
                hex = hex.toUpperCase().replace(ch, hexCodes.charAt(value-1));
            }
        }
        return hex;
    }


    public static ColorBuilder fromForeground(String hex) {
        return ColorBuilder.create().defineForeground(hex);
    }

    public static ColorBuilder fromForeground(Color color) {
        return ColorBuilder.create().defineForeground(color);
    }

    public static ColorBuilder fromBackground(String hex) {
        return ColorBuilder.create().defineBackground(hex);
    }

    public static ColorBuilder fromBackground(Color color) {
        return ColorBuilder.create().defineBackground(color);
    }

    public static int of(String hex) {
        return ColorBuilder.create().defineForeground(hex).build();
    }

    public static int of(Color color) {
        return ColorBuilder.create().defineForeground(color).build();
    }

}
