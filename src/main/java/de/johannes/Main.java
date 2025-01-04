package de.johannes;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.ui.WindowManager;
import de.johannes.curses.ui.base.BoxComponentBuilder;
import de.johannes.curses.ui.base.WindowBuilder;
import de.johannes.example.Example;
import de.johannes.snake.SnakeWindow;

public class Main {

    public static String username = "";
    public static final int FPS = 60;

    public static void main(String[] args) throws InterruptedException {
        WindowManager winman = new WindowManager(FPS, 50, 20, false);
        winman.render();
        winman.handleKey();

        Example example = new WindowBuilder<Example>().color(CursesConstants.DARK_CYAN).at(Curses.width()/2-Curses.width()/6,Curses.height()/2-Curses.height()/4).bounds(Curses.width()/3, Curses.height()/2).title("Example").build(Example::new);
        winman.addWindow(0, example);
        winman.addKeyHandler((ch, key) -> {
            if(ch=='q') {
                winman.removeWindow(0);
                winman.addWindow(1, new SnakeWindow(Curses.instance()));
                Curses.clear();
            }
        });
    }
}