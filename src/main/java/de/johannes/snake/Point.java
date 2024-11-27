package de.johannes.snake;

public class Point {

    public int x,y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) {
            return false;
        }else{
            return ((Point) obj).x == this.x && ((Point) obj).y == this.y;
        }
    }
}
