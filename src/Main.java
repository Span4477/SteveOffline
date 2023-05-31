import menu.MainMenuScreen;
import readers.GameProperties;

import javax.swing.*;

public class Main {

    private JFrame frame;
    private GameProperties gameProperties = new GameProperties();
    
    public void start() {
        

        frame = new JFrame("Steve Online");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(gameProperties.getWindowWidth(), gameProperties.getWindowHeight());
        frame.setLocationRelativeTo(null);
        
        // Load the game configuration properties
        MainMenuScreen mainMenu = new MainMenuScreen();
        mainMenu.show(frame);

    }

    public static void main(String[] args) {
        
        Main main = new Main();

        main.start();

        
    }
}