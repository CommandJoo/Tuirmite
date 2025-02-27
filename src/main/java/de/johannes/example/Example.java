package de.johannes.example;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.WindowManager;
import de.johannes.curses.ui.base.*;
import de.johannes.curses.ui.components.*;

import java.util.List;

public class Example extends Window {
    public Example() {
    }

    @Override
    public void init() {
        Button button = new BoxComponentBuilder<Button>()
                .at(1,1)
                .bounds(20, 2)
                .parent(this)
                .color(color())
                .build(Button::new)
                    .setText("Click");
        addComponent(0, button);

        TextField textField = new BoxComponentBuilder<TextField>()
                .at(1,4)
                .bounds(20, 2)
                .parent(this)
                .color(color())
                .build(TextField::new)
                    .placeholder("Type Here: ");
        addComponent(1, textField);

        PasswordField passwordField = (PasswordField) new BoxComponentBuilder<PasswordField>()
                .at(1,7)
                .bounds(20, 2)
                .parent(this)
                .color(color())
                .build(PasswordField::new)
                .placeholder("Password Here: ");
        addComponent(2, passwordField);

        TextInput textInput = new TextComponentBuilder<TextInput>()
                .at(1,10)
                .parent(this)
                .color(color())
                .text("Input:")
                .build(TextInput::new)
                .placeholder("Input:");
        addComponent(3, textInput);

        Selector selector = new BoxComponentBuilder<Selector>()
                .at(1,11)
                .bounds(20, 2)
                .parent(this)
                .color(color())
                .build(Selector::new)
                .values("Apple", "Banana", "Mango", "Strawberry", "Cherry", "Blueberry");
        addComponent(4, selector);

        Link link = new TextComponentBuilder<Link>()
                .at(1,14)
                .parent(this)
                .color(color())
                .text("GitHub")
                .build(Link::new)
                .display("GitHub", "https://github.com/CommandJoo/Java-Native-NCurses");
        addComponent(5, link);

        Text text = Text.of("Example Text")
                .at(1,15)
                .parent(this)
                .format(color)
                .append(Text.of(" + Appended Text").format("#77AF99").attrib(CursesConstants.ATTRIB_ITALIC));
        addComponent(6, text);

        Window sub = new Window(this, "Sub Window", width/2, 1, width/2-1,height-2 ,color, hoverColor) {
            @Override
            public void init() {
                Button btn = new BoxComponentBuilder<Button>()
                        .at(1,1)
                        .bounds(20, 2)
                        .parent(this)
                        .color(color())
                        .build(Button::new)
                        .setText("Another One")
                        .setExecutor((m) -> {
                            WindowManager.instance().kill();
                        });
                addComponent(0, btn);

                Listable list = new BoxComponentBuilder<Listable>()
                        .at(1,4)
                        .bounds(10, 20)
                        .parent(this)
                        .color(color())
                        .build(Listable::new)
                        .content("1", "2", "3", "4", "5", "ABCDEFGHIJKLMNOP");

                addComponent(1, list);

                Dropdown dropdown = new BoxComponentBuilder<Dropdown>()
                        .at(12,4)
                        .bounds(9, 0)
                        .parent(this)
                        .color(color())
                        .hoverColor(hoverColor)
                        .build(Dropdown::new)
                        .values("1", "2", "3", "4", "E", "F", "G");

                addComponent(2, dropdown);

                Checkbox box = new BoxComponentBuilder<Checkbox>()
                        .at(24, 1)
                        .bounds(5, 2)
                        .rounded(true)
                        .parent(this)
                        .color(color())
                        .build(Checkbox::new);

                addComponent(3, box);

                ScrollingText scrollingText = new TextComponentBuilder<ScrollingText>()
                        .at(1, 17)
                        .parent(this)
                        .color(color)
                        .text("Hello, This is a really long sentence, however it will scroll so you can read it all!")
                        .build(ScrollingText::new)
                        .width(20)
                        .scrollSpeedMillis(100);

                addComponent(4, scrollingText);
            }

            @Override
            public void draw() {
            }

            @Override
            public boolean handleKey(char ch) {
                if(ch == Keys.KEY_RIGHT) {
                    Listable list = ((Listable) getComponent(1));
                    list.content("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K");
                }
                for(Component comp : getComponents()) {
                    comp.handleKey(ch);
                }
                return false;
            }

            @Override
            public boolean handleClick(Mouse mouse) {
                for(Component comp : this.getComponents()) {
                    if((comp instanceof BoxComponent)) {
                        if(mouse.x >= comp.x() && mouse.x <= comp.x()+((BoxComponent) comp).width() &&
                                mouse.y >= comp.y() && mouse.y <= comp.y()+((BoxComponent) comp).height()) {
                            if(comp instanceof Link) {
                                comp.setColor(comp.color() == color() ? CursesConstants.LIGHT_RED : color());
                            }else {
                                comp.handleClick(mouse);
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public boolean handleHover(int x, int y) {
                super.handleHover(x,y);
                return false;
            }
        };
        addComponent(7, sub);
    }

    @Override
    public void draw() {
    }

    @Override
    public boolean handleKey(char ch) {
        for(Component comp : getComponents()) {
            comp.handleKey(ch);
        }
        return false;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        for(Component comp : this.getComponents()) {
            if((comp instanceof BoxComponent)) {
                if(mouse.x >= comp.x() && mouse.x <= comp.x()+((BoxComponent) comp).width() &&
                        mouse.y >= comp.y() && mouse.y <= comp.y()+((BoxComponent) comp).height()) {
                    if(comp instanceof Link) {
                        comp.setColor(comp.color() == color() ? CursesConstants.LIGHT_RED : color());
                    }else {
                        comp.handleClick(mouse);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean handleHover(int x, int y) {
        super.handleHover(x,y);
        return false;
    }
}
