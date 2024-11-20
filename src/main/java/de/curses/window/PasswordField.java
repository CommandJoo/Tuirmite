package de.curses.window;

public class PasswordField extends TextField{

    public PasswordField(int x, int y, int width) {
        super(x, y, width);
    }

    public PasswordField(int x, int y, int width, String placeholder) {
        super(x, y, width, placeholder);
    }

    @Override
    protected void draw(char ch) {
        if(input != null) {
            if(ch != 0) {
                if(ch == 263) {
                    input.setLength(Math.max(input.length() - 1, 0));
                } else if(ch != 10){
                    input.append(ch);
                }
            }
            String text = !input.isEmpty() ? "*".repeat(input.length()) : placeholder;

            String cursor = !input.isEmpty() ? blinker.check(500) ? " " : "█" : "";
            drawString(1,1, text+cursor, width-2, color);
            if(blinker.check(1000)) blinker.reset();
        }
    }
}
