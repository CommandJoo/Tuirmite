package de.curses.window.components;

import de.curses.NativeCurses;
import de.curses.util.ColorBuilder;
import de.curses.util.ColorUtil;
import de.curses.window.Component;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Image extends Component {

    private BufferedImage image;
    private boolean error;

    public Image(Window parent, int x, int y, int width, File image) {
        super(parent, x, y, width, 0);
        setImage(image);
    }

    private HashMap<Integer, List<Integer>> lines;

    @Override
    public void init() {
        this.lines = new HashMap<>();
        int height = (int) (this.width * ((float) this.image.getWidth() / this.image.getHeight()) / 2);
        int stepX = this.image.getWidth() / width;
        int stepY = this.image.getHeight() / height;

        for (int j = 0; j < height; j++) {
            List<Integer> color = new ArrayList<>();
            for (int i = 0; i < width; i++) {
                try {
                    Color background = ColorUtil.averageColor(this.image, stepX * i, (stepY / 2) + stepY * j, stepX, stepY / 2);
                    Color foreground = ColorUtil.averageColor(this.image, stepX * i, stepY * j, stepX, stepY / 2);
                    color.add(
                            ColorBuilder
                                    .create()
                                    .defineForeground(foreground)
                                    .defineBackground(background)
                                    .build()
                    );
                } catch (Exception ex) {

                }
            }
            lines.put(j, color);
        }
    }


    @Override
    public void draw() {
        if (error) {
            drawCenteredString(x + width / 2, y + height / 2, "Error: couldn't load image", color);
        } else {
            for (Integer y : lines.keySet()) {
                List<Integer> color = lines.get(y);
                for (int x = 0; x < color.size(); x++) {
                    Integer col = color.get(x);
                    drawString(x, y, "█", col);//█▄
                }
            }
        }
    }

    @Override
    public boolean handleKey(char ch) {
        return false;
    }

    public void setImage(File image) {
        BufferedImage img = ColorUtil.readImage(image);
        if (img != null) {
            this.image = img;
            this.error = false;
            this.init();
        } else {
            this.error = true;
            NativeCurses.instance().destroy();
            System.exit(0);
        }
    }
}
