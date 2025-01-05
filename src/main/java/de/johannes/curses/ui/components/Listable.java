package de.johannes.curses.ui.components;

import de.johannes.curses.CursesConstants;
import de.johannes.curses.Keys;
import de.johannes.curses.Mouse;
import de.johannes.curses.ui.base.BoxComponent;

public class Listable extends BoxComponent {

    private String[] content;
    private int index;

    public Listable content(String... content) {
        this.content = content;
        this.index = 0;
        this.height = 1+content.length;
        return this;
    }

    @Override
    public void init() {

    }

    @Override
    public void draw() {
        for(int i = 0; i < content.length; i++) {
            String line = content[i];
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
        drawBox();
    }

    @Override
    public boolean handleKey(char ch) {
        if(ch == Keys.KEY_DOWN) {
            increaseIndex();
        }else if(ch == Keys.KEY_UP) {
            decreaseIndex();
        }
        return false;
    }

    public void increaseIndex() {
        if(index+1==content.length) index=-1;
        index++;
    }

    public void decreaseIndex() {
        if(index-1 == -1) index = content.length;
        index--;
    }

    public int index() {
        return index;
    }

    public String value() {
        return content[index];
    }

    @Override
    public boolean handleClick(Mouse mouse) {
        return false;
    }
}