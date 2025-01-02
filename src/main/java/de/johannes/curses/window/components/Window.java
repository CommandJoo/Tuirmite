package de.johannes.curses.window.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.window.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class Window extends Component {

    protected final String title;

    private final HashMap<Integer, Component> components;

    public Window(Window parent, int x, int y, int width, int height) {
        this(parent, x, y, width, height, CursesConstants.WHITE);
    }
    public Window(Window parent, int x, int y, int width, int height, String title) {
        this(parent, x, y, width, height, CursesConstants.WHITE, title);
    }
    public Window(Window parent, int x, int y, int width, int height, int color) {
        this(parent, x, y, width, height, color, "");
    }
    public Window(Window parent, int x, int y, int width, int height, int color, String title) {
        this(parent,x,y,width,height,color,title, false);
    }
    public Window(Window parent, int x, int y, int width, int height, int color, String title, boolean rounded) {
        super(parent, x, y, width, height, color, rounded);
        this.components = new HashMap<>();
        this.title = title;
        this.init();
    }

    public void drawWindow() {
        for (Component component : this.getComponents()) {
            drawComponent(component);
        }
        this.draw();
    }
    @Override
    public void drawBox(int color) {
        int rendercolor = color == -1 ? this.color : color;

        Curses.instance().setColor(rendercolor);
        if(!rounded) {
            Curses.instance().drawCorner(x, y, 2);
            Curses.instance().drawCorner(x + width, y, 1);
            Curses.instance().drawCorner(x, y + height, 3);
            Curses.instance().drawCorner(x + width, y + height, 0);
        }else {
            Curses.instance().drawRoundedCorner(x, y, 2);
            Curses.instance().drawRoundedCorner(x + width, y, 1);
            Curses.instance().drawRoundedCorner(x, y + height, 3);
            Curses.instance().drawRoundedCorner(x + width, y + height, 0);
        }

        Curses.instance().drawHorizontalLine(y, x + 1, x + width);
        Curses.instance().drawHorizontalLine(y + height, x + 1, x + width);
        Curses.instance().drawVerticalLine(x, y + 1, y + height);
        Curses.instance().drawVerticalLine(x + width, y + 1, y + height);

        if (!title.isEmpty()) {
            drawDecoration(width / 2 - ((title.length() + 4) / 2), false, false, title, rendercolor);
        }
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        for(Component comp : this.components.values()) {
            if(mouse.x >= comp.x && mouse.x <= comp.x+comp.width &&
               mouse.y >= comp.y && mouse.y <= comp.y+comp.height) {
                return comp.handleClick(mouse);
            }
        }
        return false;
    }

    public void drawComponent(Component comp) {
        if (comp != null) {
            if (comp instanceof Window window) {
                window.drawBox(window.color);
                window.drawWindow();
            } else {
                comp.draw();
            }
        }
    }

    public Component addComponent(int id, Component component) {
        if (this.components.containsKey(id))
            throw new IllegalArgumentException("Component with ID: " + id + " already registered!");
        this.components.put(id, component);
        return component;
    }
    public void removeComponent(int id) {
        if(!this.components.containsKey(id))
            throw new IllegalArgumentException("Component with ID: "+ id+" doesn't exist!");
        this.components.remove(id);
    }
    public List<Component> getComponents() {
        return List.copyOf(this.components.values());
    }
    public Component getComponent(int id) {
        return components.getOrDefault(id, null);
    }
    @SuppressWarnings("unchecked")
    public <T extends Component>List<T> getComponents(Class<T> clazz) {
        List<Component> components = getComponents();
        List<T> elements = new ArrayList<>();
        components.forEach(comp -> {
            if (comp.getClass().isAssignableFrom(clazz)) elements.add((T) comp);
        });
        return elements;
    }
    private List<Button> buttons() {
        List<Button> buttons = getComponents(Button.class);
        buttons.sort(new Comparator<Button>() {
            @Override
            public int compare(Button o1, Button o2) {
                if (o1.y - o2.y == 0) {
                    return o1.x - o2.x;
                }
                return o1.y - o2.y;
            }
        });
        return buttons;
    }

}
