package de.johannes.curses.ui.base;

import de.johannes.curses.ui.components.Window;

import java.util.function.Supplier;

public class TextComponentBuilder<T extends TextComponent> extends ComponentBuilder<T> {

    protected String text;

    public TextComponentBuilder<T> parent(Window parent) {
        TextComponentBuilder<T> builder = createBuilder();
        builder.parent = parent;
        return builder;
    }

    public TextComponentBuilder<T> text(Object text) {
        TextComponentBuilder<T> builder = createBuilder();
        builder.text = text.toString();
        return builder;
    }

    public TextComponentBuilder<T> at(int x, int y) {
        TextComponentBuilder<T> builder = createBuilder();
        builder.x = x;
        builder.y = y;
        return builder;
    }

    public TextComponentBuilder<T> color(int color) {
        TextComponentBuilder<T> builder = createBuilder();
        builder.color = color;
        return builder;
    }

    public TextComponentBuilder<T> hoverColor(int color) {
        TextComponentBuilder<T> builder = createBuilder();
        builder.hoverColor = color;
        return builder;
    }

    public T build(Supplier<T> supp) {
        T obj = supp.get();
        obj.parent = parent;
        obj.x = x;
        obj.y = y;
        obj.text = text;
        obj.width = text != null ? text.length() : 0;
        obj.height = 1;
        if(color == -1) {
            obj.color = parent.color;
            obj.originalColor = parent.color;
        }else {
            obj.color = color;
            obj.originalColor = color;
        }
        if(hoverColor == -1) {
            obj.hoverColor = parent.hoverColor;
        }else {
            obj.hoverColor = hoverColor;
        }
        obj.init();
        return obj;
    }

    @Override
    protected TextComponentBuilder<T> createBuilder() {
        TextComponentBuilder<T> builder = new TextComponentBuilder<T>();
        builder.x = x;
        builder.y = y;
        builder.text = text;
        builder.parent = parent;
        builder.color = color;
        builder.hoverColor = hoverColor;
        return builder;
    }
}
