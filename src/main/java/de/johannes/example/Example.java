package de.johannes.example;

import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.*;
import de.johannes.curses.ui.components.*;

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

        TextInput textInput = new ComponentBuilder<TextInput>()
                .at(1,10)
                .parent(this)
                .color(color())
                .build(TextInput::new)
                .placeholder("Input: ");
        addComponent(3, textInput);

        Selector selector = new BoxComponentBuilder<Selector>()
                .at(1,11)
                .bounds(20, 2)
                .parent(this)
                .color(color())
                .build(Selector::new)
                .values("Apple", "Banana", "Mango", "Strawberry", "Cherry", "Blueberry");
        addComponent(4, selector);

        Link link = new ComponentBuilder<Link>()
                .at(1,14)
                .parent(this)
                .color(color())
                .build(Link::new)
                .display("GitHub", "https://github.com/CommandJoo/Java-Native-NCurses");
        addComponent(5, link);

        Text text = Text.of("Example Text")
                .at(1,15)
                .parent(this)
                .format(color);
        addComponent(6, text);

        Window sub = new Window(this, "Sub Window", width/2, 1, width/2-1,height-2 ,color) {
            @Override
            public void init() {
                Button btn = new BoxComponentBuilder<Button>()
                        .at(1,1)
                        .bounds(20, 2)
                        .parent(this)
                        .color(color())
                        .build(Button::new)
                        .setText("Another One");
                addComponent(0, btn);
            }

            @Override
            public void draw() {
            }

            @Override
            public boolean handleKey(char ch) {
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
                    if(comp instanceof Button) {
                        ((Button) comp).setSelected(!((Button) comp).selected());
                    }
                }
            }
        }
        return false;
    }
}
