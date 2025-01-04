package de.johannes.curses.ui.base;

import de.johannes.curses.ui.components.Window;

import java.util.function.Supplier;

public class BoxComponentBuilder<T extends BoxComponent> extends ComponentBuilder<T> {

    protected int width, height;

    public BoxComponentBuilder<T> parent(Window parent) {
        BoxComponentBuilder<T> builder = createBuilder();
        builder.parent = parent;
        return builder;
    }

    public BoxComponentBuilder<T> at(int x, int y) {
        BoxComponentBuilder<T> builder = createBuilder();
        builder.x = x;
        builder.y = y;
        return builder;
    }

    public BoxComponentBuilder<T> color(int color) {
        BoxComponentBuilder<T> builder = createBuilder();
        builder.color = color;
        return builder;
    }

    public BoxComponentBuilder<T> bounds(int width, int height) {
        BoxComponentBuilder<T> builder = createBuilder();
        builder.width  = width;
        builder.height = height;
        return builder;
    }

    public T build(Supplier<T> supp) {
        T obj = supp.get();
        obj.parent = parent;
        obj.x = x;
        obj.y = y;
        obj.width = width;
        obj.height = height;
        obj.color = color;
        obj.init();
        return obj;
    }

    @Override
    protected BoxComponentBuilder<T> createBuilder() {
        BoxComponentBuilder<T> builder = new BoxComponentBuilder<T>();
        builder.x = x;
        builder.y = y;
        builder.width = width;
        builder.height = height;
        builder.parent = parent;
        builder.color = color;
        return builder;
    }
}
