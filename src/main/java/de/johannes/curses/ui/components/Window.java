package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.Mouse;
import de.johannes.curses.nerdfont.NFFontAwesome;
import de.johannes.curses.nerdfont.NFFontAwesomeExtension;
import de.johannes.curses.nerdfont.NFMaterialDesign;
import de.johannes.curses.ui.base.BoxComponent;
import de.johannes.curses.ui.base.Component;
import de.johannes.curses.util.ColorBuilder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public abstract class Window extends BoxComponent {

    public String title = "";
    private final HashMap<Integer, Component> components;
    private boolean closeable;

    public Window(Window parent, String title, int x, int y, int width, int height, int color) {
        this();
        this.parent = parent;
        this.title = title;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.init();
    }

    public Window() {
        this.components = new HashMap<>();
    }

    public void drawWindow() {
        drawBox();
        for (Component component : this.getComponents()) {
            drawComponent(component);
        }
        this.draw();
    }

    @Override
    public void drawBox() {
        Curses.instance().setColor(color());
        Curses.instance().drawCorner(x(), y(), 2, color(), rounded);
        if(isCloseable()) {
            Curses.instance().drawString(NFFontAwesomeExtension.THIN_CLOSE, x()+width(), y(), color());
        }else {
            Curses.instance().drawCorner(x() + width(), y(), 1, color(), rounded);
        }
        Curses.instance().drawCorner(x(), y() + height(), 3, color(), rounded);
        Curses.instance().drawCorner(x() + width(), y() + height(), 0, color(), rounded);


        Curses.instance().drawHorizontalLine(y(), x() + 1, x() + width(), color());
        Curses.instance().drawHorizontalLine(y() + height(), x() + 1, x() + width(), color());
        Curses.instance().drawVerticalLine(x(), y() + 1, y() + height(), color());
        Curses.instance().drawVerticalLine(x() + width(), y() + 1, y() + height(), color());

        if (!title.isEmpty()) {
            drawDecoration(width() / 2 - ((title.length() + 4) / 2), false, false, title, color());
        }
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        for (Component comp : this.components.values()) {
            if ((comp instanceof BoxComponent)) {
                if (mouse.x >= comp.x() && mouse.x <= comp.x() + ((BoxComponent) comp).width() &&
                        mouse.y >= comp.y() && mouse.y <= comp.y() + ((BoxComponent) comp).height()) {
                    return comp.handleClick(mouse);
                }
            }
        }
        return false;
    }

    public void drawComponent(Component comp) {
        if (comp != null) {
            if (comp instanceof Window window) {
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
        if (!this.components.containsKey(id))
            throw new IllegalArgumentException("Component with ID: " + id + " doesn't exist!");
        this.components.remove(id);
    }

    public List<Component> getComponents() {
        return List.copyOf(this.components.values());
    }

    public Component getComponent(int id) {
        return components.getOrDefault(id, null);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> List<T> getComponents(Class<T> clazz) {
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
                if (o1.y() - o2.y() == 0) {
                    return o1.x() - o2.x();
                }
                return o1.y() - o2.y();
            }
        });
        return buttons;
    }

    public Window closeable(boolean b) {
        this.closeable = b;
        return this;
    }

    public boolean isCloseable() {
        return this.closeable;
    }

}
