package de.johannes.curses.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ColorUtil {

    public static Color averageColor(BufferedImage img, int x, int y, int w,
                              int h) {
        int num = w * h;
        int sumR = 0, sumG = 0, sumB = 0;

        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color cl = new Color(img.getRGB(x + i, y + j));
                sumR += cl.getRed();
                sumG += cl.getGreen();
                sumB += cl.getBlue();
            }
        }

        return new Color(sumR / num, sumG / num, sumB / num);
    }

    public static BufferedImage readImage(File image) {
        try {
            return ImageIO.read(image);
        }catch (Exception ex) {
            return null;
        }
    }
}
