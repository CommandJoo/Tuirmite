package de.johannes.example;

import de.johannes.PrivateVariables;
import de.johannes.curses.Curses;
import de.johannes.curses.util.ColorBuilder;
import de.johannes.curses.window.components.*;

import java.io.File;

public class Example extends Window {
    public Example() {
        super(null,
                Curses.width()/2-Curses.width()/6,
                Curses.height()/2-Curses.height()/4,
                2*Curses.width()/6,
                Curses.height()/2,
                ColorBuilder.create().defineForeground("#DDBB66").build(),
                "Example");
    }

    @Override
    public void init() {
        addComponent(0, new Button(this, 1, 1, width/2,3,"Button"));
//        addComponent(1, new Image(this, 1, 1, 30, new File(PrivateVariables.PROJECT_HOME +"/build/libs/image.png")));
        addComponent(2, new PasswordField(this, 1, 5, width/2, "Password: "));
        addComponent(3, new Selector(this, 1, 8, width/2, color, "Selector", "Apple", "Banana", "Orange", "Cherry", "Melon"));
        addComponent(4, new TextField(this, 1, 11, width/2, "Text: "));
        addComponent(5, new Window(this, width/2+2, 1, width/2-3, height-2, color, "Sub Window") {
            @Override
            public void draw() {
                drawString(1,1,  "Example String", color);
                drawCenteredString(width/2,2,  "Centered Example String", color);
                drawString(1,3,  "$uFormatted$r $hString$r", color);
                drawCenteredString(width/2,4,  "Centered $iFormatted$r String", color);
                drawString(1,5,  "Colored Example String", ColorBuilder.create().defineForeground("#FAAAFF").build());//add color formatting
                drawString(1,6,  "Colored $cf{#FFAAAA}Formatted String", ColorBuilder.create().defineForeground("#FAAAFF").build());
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
        return false;
    }
}
