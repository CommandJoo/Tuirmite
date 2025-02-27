package de.johannes.curses.ui.base;

import de.johannes.curses.ui.components.Window;

import java.util.function.Supplier;

public class WindowBuilder<T extends Window> extends BoxComponentBuilder<T> {

    protected String title;

    public WindowBuilder<T> title(String title) {
        WindowBuilder<T> builder = createBuilder();
        builder.title = title;
        return builder;
    }

    public WindowBuilder<T> rounded(boolean rounded) {
        WindowBuilder<T> builder = createBuilder();
        builder.rounded = rounded;
        return builder;
    }

    public WindowBuilder<T> parent(Window parent) {
        WindowBuilder<T> builder = createBuilder();
        builder.parent = parent;
        return builder;
    }

    public WindowBuilder<T> at(int x, int y) {
        WindowBuilder<T> builder = createBuilder();
        builder.x = x;
        builder.y = y;
        return builder;
    }

    public WindowBuilder<T> color(int color) {
        WindowBuilder<T> builder = createBuilder();
        builder.color = color;
        return builder;
    }

    public WindowBuilder<T> hoverColor(int color) {
        WindowBuilder<T> builder = createBuilder();
        builder.hoverColor = color;
        return builder;
    }

    public WindowBuilder<T> bounds(int width, int height) {
        WindowBuilder<T> builder = createBuilder();
        builder.width  = width;
        builder.height = height;
        return builder;
    }

    public T build(Supplier<T> supp) {
        T obj = supp.get();
        obj.parent = parent;
        obj.x = x;
        obj.y = y;
        obj.title = title;
        obj.width = width;
        obj.height = height;
        if(color == -1) {
            obj.color = parent.color;
            obj.originalColor = parent.color;
        }else {
            obj.color = color;
            obj.originalColor = color;
        }
        if(hoverColor == -1) {
            obj.hoverColor = parent != null ? parent.hoverColor : obj.color;
        }else {
            obj.hoverColor = hoverColor;
        }
        obj.rounded = rounded;
        obj.init();
        return obj;
    }

    @Override
    protected WindowBuilder<T> createBuilder() {
        WindowBuilder<T> builder = new WindowBuilder<T>();
        builder.x = x;
        builder.y = y;
        builder.width = width;
        builder.height = height;
        builder.title = title;
        builder.rounded = rounded;
        builder.parent = parent;
        builder.color = color;
        builder.hoverColor = hoverColor;
        return builder;
    }
}
