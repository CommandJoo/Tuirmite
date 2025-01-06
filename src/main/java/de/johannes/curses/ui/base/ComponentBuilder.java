package de.johannes.curses.ui.base;

import de.johannes.curses.ui.components.Window;

import java.util.function.Supplier;

public class ComponentBuilder<T extends Component> {

    protected Window parent;
    protected int x,y, color = -1;

    public static <T extends Component> ComponentBuilder<T> create(Class<T> clazz) {
        return new ComponentBuilder<T>();
    }

    public static <T extends Component> ComponentBuilder<T> child(Class<T> clazz, Window parent) {
        ComponentBuilder<T> builder = new ComponentBuilder<T>();
        builder.parent = parent;
        return builder;
    }

    public ComponentBuilder<T> parent(Window parent) {
        ComponentBuilder<T> builder = createBuilder();
        builder.parent = parent;
        return builder;
    }

    public ComponentBuilder<T> at(int x, int y) {
        ComponentBuilder<T> builder = createBuilder();
        builder.x = x;
        builder.y = y;
        return builder;
    }

    public ComponentBuilder<T> color(int color) {
        ComponentBuilder<T> builder = createBuilder();
        builder.color = color;
        return builder;
    }

    protected ComponentBuilder<T> createBuilder() {
        ComponentBuilder<T> builder = new ComponentBuilder<T>();
        builder.x = x;
        builder.y = y;
        builder.parent = parent;
        builder.color = color;
        return builder;
    }

    public T build(Supplier<T> supp) {
        T obj = supp.get();
        obj.parent = parent;
        obj.x = x;
        obj.y = y;
        if(color == -1) {
            obj.color = parent.color;
        }else {
            obj.color = color;
        }
        obj.init();
        return obj;
    }

}
