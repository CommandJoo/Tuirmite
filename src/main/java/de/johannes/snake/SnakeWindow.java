package de.johannes.snake;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.util.Timer;
import de.curses.window.components.Window;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class SnakeWindow extends Window {

    Timer gameTimer;
    List<Point> player;
    List<Point> food;
    int direction;
    int foodColor;
    boolean dead = false;


    private static final int FOOD_COUNT = 4;
    private static final int START_SIZE = 4;

    public SnakeWindow(NativeCurses curses) {
        super(null, curses.getWidth() / 2 - (curses.getWidth() / 3), curses.getHeight() / 2 - (curses.getHeight() / 3), 2 * curses.getWidth() / 3, 2 * curses.getHeight() / 3, ColorBuilder.create().defineForeground("#7731AF").build(), "Snake");
        this.foodColor = ColorBuilder.create().defineForeground("#67EB44").build();
    }

    @Override
    public void init() {
        this.gameTimer = new Timer();

        this.player = new LinkedList<>();
        for(int i = 0; i < START_SIZE; i++) {
            this.player.add(new Point(width / 2-i, height / 2));
        }

        this.food = new ArrayList<>();
        for(int i = 0; i < FOOD_COUNT; i++) {
            this.food.add(new Point(new Random().nextInt(width-1)+1, new Random().nextInt(height-1)+1));
        }
    }

    @Override
    public void draw() {
        if(!dead) {
            for (int i = 0; i < player.size(); i++) {
                drawString(player.get(i).x, player.get(i).y, i == 0 ? "O" : "o", color);
            }
            for(Point food : this.food) {
                drawString(food.x, food.y, "@", foodColor);
            }

            if(this.player.getFirst().x <= 0) {
                this.dead = true;
                return;
            }
            if(this.player.getFirst().x >= width) {
                this.dead = true;
                return;
            }
            if(this.player.getFirst().y <= 0) {
                this.dead = true;
                return;
            }
            if(this.player.getFirst().y >= height) {
                this.dead = true;
                return;
            }

            for(int i = 1; i < this.player.size(); i++) {
                Point p = this.player.get(i);
                if(p.x == player.getFirst().x && p.y == player.getFirst().y) dead = true;
            }
            for(Point point : this.player) {
                for(Point food : this.food) {
                    if(point.equals(food)) {
                        this.player.add(new Point(this.player.getLast().x, this.player.getLast().y));
                        food.x = new Random().nextInt(width-1)+1;
                        food.y = new Random().nextInt(height-1)+1;
                        break;
                    }
                }
                break;
            }
            if (gameTimer.check(100)) {
                movePlayer();
                gameTimer.reset();
            }
            drawDecoration(width/8, true, true, "Score: "+(this.player.size()-START_SIZE), color);
        }else {
            setColor(color);
            drawCenteredString(width/2, height/2-3, "Game Over!", color);
            drawCenteredString(width/2, height/2-1, "You died.", color);
            drawCenteredString(width/2, height/2+1, "Press <Space> to restart", color);
        }
    }

    public void movePlayer() {
        Point prev = new Point(player.getFirst().x, player.getFirst().y);
        if (direction == 0) player.getFirst().x++;
        if (direction == 1) player.getFirst().x--;
        if (direction == 2) player.getFirst().y++;
        if (direction == 3) player.getFirst().y--;
        if(player.size() > 1) {
            for (int i = 1; i < player.size(); i++) {
                Point p = new Point(player.get(i).x, player.get(i).y);
                player.get(i).x = prev.x;
                player.get(i).y = prev.y;
                prev = p;
            }
        }

    }

    @Override
    public boolean handleKey(char ch) {
        if (ch == 'a') {
            direction = 1;
            return true;
        }
        if (ch == 'd') {
            direction = 0;
            return true;
        }
        if (ch == 's') {
            direction = 2;
            return true;
        }
        if (ch == 'w') {
            direction = 3;
            return true;
        }
        if(dead && ch == ' ') {
            dead = false;
            init();
        }
        return false;
    }
}
