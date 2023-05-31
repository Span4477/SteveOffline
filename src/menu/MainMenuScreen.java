package menu;

import javax.swing.*;

import readers.GameProperties;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.net.URL;

public class MainMenuScreen {
    private JFrame frame;
    private BufferedImage backgroundImage;
    private GameProperties gameProperties;

    public void show(JFrame frame) {
        gameProperties = new GameProperties();

        this.frame = frame;
        // Clear the frame
        this.frame.getContentPane().removeAll();
        this.frame.setLayout(new GridBagLayout());

        loadBackgroundImage();


        // Create a custom JPanel with background image
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                // Make the background black
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Center the background image
                int x = (getWidth() - backgroundImage.getWidth()) / 2;
                int y = (getHeight() - backgroundImage.getHeight()) / 2;
                g.drawImage(backgroundImage, x, y, this);

            }
        };
        contentPanel.setLayout(new GridBagLayout());

        JPanel buttonPanel = createButtonPanel();
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0,0,0,0);
        contentPanel.add(buttonPanel, c);


        this.frame.setContentPane(contentPanel);


        this.frame.setVisible(true);

    }
    private void loadBackgroundImage() {
        // Load the background image

        // Get the image path
        String imagePath = "/images/main_menu_background.png";

        // Load the image
        try {
            URL resourceUrl = getClass().getResource(imagePath);

            backgroundImage = ImageIO.read(resourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JPanel createButtonPanel() {
        JButton newGameButton = createButton("New Game");
        JButton loadGameButton = createButton("Load Game");
        JButton settingsButton = createButton("Settings");
        JButton exitButton = createButton("Exit");

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewGameScreen newGameScreen = new NewGameScreen();
                newGameScreen.show(frame);
            }
        });

        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                LoadGameScreen loadGameScreen = new LoadGameScreen();
                loadGameScreen.show();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                SettingsScreen settingsScreen = new SettingsScreen();
                settingsScreen.show(frame);
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(0,1));
        
        // Make buttonPanel semi-transparent
        buttonPanel.setOpaque(false);
        buttonPanel.setBackground(new Color(0,0,0,0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        // Set the font name and size of the buttons
        String fontName = gameProperties.getFontName();
        int fontSize = gameProperties.getFontSize();
        newGameButton.setFont(new Font(fontName, Font.PLAIN, fontSize));
        loadGameButton.setFont(new Font(fontName, Font.PLAIN, fontSize));
        settingsButton.setFont(new Font(fontName, Font.PLAIN, fontSize));
        exitButton.setFont(new Font(fontName, Font.PLAIN, fontSize));

        // Set the button size relative to the font size
        int buttonWidth = fontSize * 10;
        int buttonHeight = fontSize * 2;
        newGameButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        loadGameButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        settingsButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        exitButton.setPreferredSize(new Dimension(buttonWidth, buttonHeight));

        // Add the buttons to the buttonPanel
        buttonPanel.add(newGameButton);
        buttonPanel.add(loadGameButton);
        buttonPanel.add(settingsButton);
        buttonPanel.add(exitButton);
        return buttonPanel;
    }
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        String fontName = gameProperties.getFontName();
        int fontSize = gameProperties.getFontSize();

        button.setFont(new Font(fontName, Font.PLAIN, fontSize));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    

}
