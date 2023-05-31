package readers;

import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

import game.CoordinateConvert;
import game.Coordinate;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class Gate {
    private String name;
    private String image;
    private long x;
    private long y;
    private Image imageForeground;
    private BufferedImage foregroundImage;
    private int windowWidthForeground;
    private int windowHeightForeground;

    public Gate(String name, String image, long x, long y) {
        this.name = name;
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public long getX() {
        return x;
    }
    public long getY() {
        return y;
    }

    
    public void draw(Graphics g, JPanel p, CoordinateConvert convert) {
        if (foregroundImage == null) {
            loadForegroundImage(getImage());
        } 

        final double SCALE = 0.4;

        if (windowHeightForeground != p.getHeight() || windowWidthForeground != p.getWidth()) {
            windowHeightForeground = p.getHeight();
            windowWidthForeground = p.getWidth();
            
            double widthRatio = (double) p.getWidth() / foregroundImage.getWidth();
            double heightRatio = (double) p.getHeight() / foregroundImage.getHeight();
            double ratio = Math.max(widthRatio, heightRatio);
            int newWidth = (int) (foregroundImage.getWidth() * ratio * SCALE);
            int newHeight = (int) (foregroundImage.getHeight() * ratio * SCALE);
            imageForeground = foregroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        // Convert the game coordinates to window coordinates
        Coordinate windowCoords = convert.gameToWindow(getX(), getY());
        int x = (int) windowCoords.getX() - imageForeground.getWidth(p) / 2;
        int y = (int) windowCoords.getY() - imageForeground.getHeight(p) / 2;

        g.drawImage(imageForeground, x, y, p);
    }
    
    private void loadForegroundImage(String imagePath) {
        

        // Load the image
        try {
            URL resourceUrl = getClass().getResource(imagePath);
            

            foregroundImage = ImageIO.read(resourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
