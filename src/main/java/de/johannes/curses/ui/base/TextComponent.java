package de.johannes.curses.ui.base;

public abstract class TextComponent extends BoxComponent {

    protected String text;

    public TextComponent() {
        super();
        this.text = "";
        this.width = 0;
        this.height = 1;
        this.rounded = false;
    }

    @Override
    public void drawBox() {}

    @Override
    public void drawDecoration(int x, boolean bottom, boolean parens, String deco, int color) {}
}

