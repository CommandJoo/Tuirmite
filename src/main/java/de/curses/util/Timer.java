package de.curses.util;

public class Timer {

    private long last;
    
    public Timer() {
        this.reset();
    }

    public void reset() {
        this.last = System.currentTimeMillis();
    }

    public int check() {
        return (int) (System.currentTimeMillis()-this.last);
    }

    public boolean check(int time) {
        return this.check() > time;
    }


}
