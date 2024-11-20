package de.curses.window;

public class Textfield extends Window{

    public Textfield(int x, int y, int width) {
        super(x, y, width, 2);
        input = new StringBuilder();
    }

    private final StringBuilder input;

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
            drawString(x+1,y+1, input.toString()+"█", width-2, color);
            if(true) {
                if(!input.isEmpty()) drawString(x+1, y+2, String.valueOf((int)input.charAt(input.length()-1)), 2, color);
            }
        }
    }

    public String input() {
        return input.toString();
    }
}
