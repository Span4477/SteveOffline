package ui;

import javax.swing.*;

import readers.GameProperties;
import readers.SolarSystem;
import readers.Universe;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SystemMap extends JDialog {
    private BufferedImage backgroundImage;
    private Universe universe;
    private GameProperties gameProperties;
    private int rectangleWidth = 100;
    private int rectangleHeight = 20;
    private int windowWidth;
    private int windowHeight;
    private Image imageBackground;
    

    public SystemMap(Frame owner, Universe universe) {
        super(owner, "System Map", false);

        gameProperties = new GameProperties();
        
        setSize(gameProperties.getWindowWidth() / 2, gameProperties.getWindowHeight() / 2);

        // Move to the middle of the screen
        setLocationRelativeTo(owner);
        
        setResizable(true);
        setFocusable(true);
        
        setBackgroundImage();

        this.universe = universe;

        // Add a mouse listener to the window
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Handle mouse click event

                // Check if the click was on a system
                int x = e.getX();
                int y = e.getY();
                for (SolarSystem s : universe.getSystems()) {
                    // The system x,y is offset to be centered at the middle of the screen
                    int x1 = s.getX() + (getWidth() / 2) - (rectangleWidth / 2);
                    int y1 = s.getY() + (getHeight() / 2) - (rectangleHeight / 2);
                    
                    if (x >= x1 && x <= x1 + rectangleWidth && y >= y1 && y <= y1 + rectangleHeight) {
                        // The click was on this system
                        System.out.println("Clicked on system: " + s.getName());
                    }
                }

            }
            @Override
            public void mousePressed(MouseEvent e) {
                // Handle mouse press event

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                // Handle mouse release event
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Handle mouse enter event
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Handle mouse exit event
            }

        });

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Handle key typed event
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key press event
                int keyCode = e.getKeyCode();
                System.out.println("Key pressed: " + keyCode);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Handle key release event
            }
        });

    }

    private void setBackgroundImage() {
        
        // Load the background image

        // Get the image path
        String imagePath = "/images/galaxies/g1.png";

        // Load the image
        try {
            URL resourceUrl = getClass().getResource(imagePath);

            backgroundImage = ImageIO.read(resourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawBackgroundImage(g);

        // Draw the systems
        drawSystems(g);
        // drawSystemsAsGraph(g);
    }

    private void drawBackgroundImage(Graphics g) {

        // Draw the background image, ensuring that it is centered and scaled to fit while maintaining aspect ratio
        
        if (windowHeight != getHeight() || windowWidth != getWidth()) {
            // Get the width and height of the window
            windowWidth = getWidth();
            windowHeight = getHeight();

            // Get the width and height of the image
            int imageWidth = backgroundImage.getWidth();
            int imageHeight = backgroundImage.getHeight();

            // Calculate the scale factor
            double scaleFactor = Math.max((double) windowWidth / imageWidth, (double) windowHeight / imageHeight);

            // Calculate the new width and height
            int newWidth = (int) (imageWidth * scaleFactor);
            int newHeight = (int) (imageHeight * scaleFactor);
            imageBackground = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        }
        // Calculate the x and y coordinates
        int x = (windowWidth - imageBackground.getWidth(this)) / 2;
        int y = (windowHeight - imageBackground.getHeight(this)) / 2;

        // Draw the image
        g.drawImage(imageBackground, x, y, this);

    }

    private void drawSystems(Graphics g) {
        if (universe == null) {
            universe = new Universe();
        }
        



        // If two systems are connected by a gate, draw a line between them. The name of the gate is the name of the system it connects to.
        // The line should be drawn from the center of the system to the center of the system it connects to.
        for (SolarSystem s : universe.getSystems()) {
            if (s == null) {
                continue;
            }
            for (SolarSystem s2 : universe.getSystems()) {
                if (s2 == null) {
                    continue;
                }
                for (int i = 0; i < s.getGates().length; i++) {
                    if (s.getGates()[i] == null) {
                        continue;
                    }
                    if (s.getGates()[i].getName().equals(s2.getName())) {
                        int x1 = s.getX();
                        int y1 = s.getY();
                        int x2 = s2.getX();
                        int y2 = s2.getY();
                        x1 += getWidth() / 2;
                        y1 += getHeight() / 2;
                        x2 += getWidth() / 2;
                        y2 += getHeight() / 2;
                        g.setColor(Color.WHITE);
                        g.drawLine(x1, y1, x2, y2);
                    }
                }
            }
        }

        // Loop through the systems
        for (SolarSystem s : universe.getSystems()) {
            if (s == null) {
                continue;
            }
            
            
            // Get the system's location
            int x = s.getX();
            int y = s.getY();

            // (0,0) is the center of the screen, so we need to offset the coordinates
            x += getWidth() / 2;
            y += getHeight() / 2;
            
            // Draw the system as a solid rectangle with rounded edges.
            // Draw the name of the system in the center of the rectangle
            // Ensure that the width of the rectangle is wider than the name of the system and the height is taller than the name of the system
            g.setColor(Color.WHITE);
            // Center the rectangle on the system's location
            int x1 = x - rectangleWidth / 2;
            int y1 = y - rectangleHeight / 2;
            g.fillRoundRect(x1, y1, rectangleWidth, rectangleHeight, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x1, y1, rectangleWidth, rectangleHeight, 10, 10);
            // use the width of the string to center it
            FontMetrics fm = g.getFontMetrics();
            int stringWidth = fm.stringWidth(s.getName());
            int stringHeight = fm.getHeight();
            int stringX = x - stringWidth / 2;
            int stringY = y + stringHeight / 4;
            g.drawString(s.getName(), stringX, stringY);

        }

    }
    
}
