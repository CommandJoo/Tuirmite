package de.johannes.curses.ui.components;

import de.johannes.curses.Curses;
import de.johannes.curses.CursesConstants;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.BoxComponent;

public class Dropdown extends BoxComponent {

    private String[] values;
    private int index;
    private boolean open;

    public Dropdown values(String... values) {
        this.values = values;
        this.open = false;
        return this;
    }

    @Override
    public void init() {}

    @Override
    public void draw() {
        if(isOpen()) {
            this.height = 1+values.length;
        }else {
            this.height = 2;
        }
        drawBox();
        if(!isOpen()) {
            drawString(1, 1, current().substring(0, Math.min(current().length(), width()-5)), color);
            Curses.instance().drawArrow(this.x()+this.width()-2, this.y()+1, 3, color);
        }else {
            for(int i = 0; i < values.length; i++) {
                String line = values[i];
                if(line == null) continue;
                if(i == index) {
                    int remainder = (width-1)-(line.length());
                    String render = line.substring(0, Math.min(line.length(), width-1));
                    if(remainder > 0) {
                        render += " ".repeat(remainder);
                    }
                    Text.of(render).attrib(CursesConstants.ATTRIB_REVERSE).format(color()).at(x()+1,y()+1+i).draw();
                }else{
                    Text.of(line.substring(0, Math.min(line.length(), width-1))).format(color()).at(x()+1,y()+1+i).draw();
                }
            }
        }
    }

    public String current() {
        return values[index%values.length];
    }

    @Override
    public boolean handleKey(char ch) {
        if(isOpen()) {
            if(ch == Keys.KEY_UP) {
                if(index-1==-1) index = values.length;
                index--;
            }else if(ch == Keys.KEY_DOWN) {
                if(index+1==values.length) index = -1;
                index++;
            }else if(ch == Keys.ENTER) {
                close();
            }
        }
        return false;
    }

    public void open() {
        this.open = true;
    }

    public void close() {
        this.open = false;
    }

    public boolean isOpen() {
        return open;
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        if(!open && mouse.check(Mouse.BUTTON1_CLICKED)) {
            open();
        }else if(mouse.check(Mouse.BUTTON1_CLICKED)) {
            index = mouse.y - (this.y()+1);
            close();
        }
        return false;
    }
}
