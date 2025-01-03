package de.johannes.example;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Mouse;
import de.johannes.curses.nerdfont.NFWeatherIcons;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.ui.Component;
import de.johannes.curses.ui.components.*;

public class Example extends Window {
    public Example() {
        super(null,
                Curses.width()/2-Curses.width()/6,
                Curses.height()/2-Curses.height()/4,
                2*Curses.width()/6,
                Curses.height()/2,
                ColorBuilder.create().defineForeground("#DDBB66").build(),
                "Example",
                true);
    }

    @Override
    public void init() {
        addComponent(0, new Button(this, 1, 1, width/2,3,"Button", true));
        addComponent(2, new PasswordField(this, 1, 5, width/2, "Password: ", false));
        addComponent(3, new Selector(this, 1, 8, width/2, color, true,"Selector", "Apple", "Banana", "Orange", "Cherry", "Melon"));
        addComponent(4, new TextField(this, 1, 11, width/2, "Textfield: ", true));
        addComponent(6, new TextInput(this, 1, 13, width/2, "Textinput: "));
        addComponent(5, new Window(this, width/2+2, 1, width/2-3, height-2, color, "Sub Window") {
            @Override
            public void init() {

            }

            @Override
            public void draw() {
                drawString(1,1,  "Example String", color);
                drawCenteredString(width/2,2,  "Centered Example String", color);
                drawString(1,3,  "$uFormatted$r $hString$r", color);
                drawCenteredString(width/2,4,  "Centered $iFormatted$r String", color);
                drawString(1,5,  "Colored Example String", ColorBuilder.create().defineForeground("#FAAAFF").build());//add color formatting
                drawString(1,6,  "Colored $cf{#FFAAAA}Formatted String", ColorBuilder.create().defineForeground("#FAAAFF").build());
                drawString(1,7,  "Nerdfont-Icon: "+ NFWeatherIcons.MOON_ALT_WAXING_CRESCENT_1, ColorBuilder.create().defineForeground("#FAAAFF").build());
                Text.of("This is Text.of()").format(color)
                        .append(Text.of("And this is appended").format(ColorBuilder.create().defineForeground("#55FA99").build()).attrib(CursesConstants.ATTRIB_BLINK))
                        .at(1,8)
                        .parent(this)
                        .draw();
            }

            @Override
            public boolean handleKey(char ch) {
                return false;
            }
        });
//        addComponent(7, new Image(this, -50, 1, 30, new File(Files.getJarPath().getParent(), "image.png")));
        addComponent(8, new Link(this, 1, 16, "GitHub", "https://github.com/CommandJoo/Java-Native-NCurses", color));
    }

    @Override
    public void draw() {
        Curses.instance().inch();
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
        return super.handleClick(mouse);
    }
}
