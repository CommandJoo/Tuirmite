package de.johannes.example;

import de.johannes.curses.Curses;
import de.johannes.curses.nerdfont.NFMaterialDesign;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.window.Component;
import de.johannes.curses.window.components.*;

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
            public void draw() {
                drawString(1,1,  "Example String", color);
                drawCenteredString(width/2,2,  "Centered Example String", color);
                drawString(1,3,  "$uFormatted$r $hString$r", color);
                drawCenteredString(width/2,4,  "Centered $iFormatted$r String", color);
                drawString(1,5,  "Colored Example String", ColorBuilder.create().defineForeground("#FAAAFF").build());//add color formatting
                drawString(1,6,  "Colored $cf{#FFAAAA}Formatted String", ColorBuilder.create().defineForeground("#FAAAFF").build());
                drawString(1,7,  "Nerdfont-Icon: "+ NFMaterialDesign.NF_WEATHER_MOON_ALT_WAXING_CRESCENT_1, ColorBuilder.create().defineForeground("#FAAAFF").build());
            }

            @Override
            public boolean handleKey(char ch) {
                return false;
            }
        });
    }

    @Override
    public void draw() {

    }

    @Override
    public boolean handleKey(char ch) {
        for(Component comp : getComponents()) {
            this.handleKeyForSub(comp, ch);
        }
        return false;
    }

}
