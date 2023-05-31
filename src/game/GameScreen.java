package game;

import javax.swing.*;
import java.awt.*;


public class GameScreen {
    private JFrame frame;
    private GameWorld gameWorld;
    

    public void show(JFrame frame) {

        this.frame = frame;
        // Clear the frame
        this.frame.getContentPane().removeAll();
        
        this.frame.setLayout(new GridBagLayout());

        gameWorld = new GameWorld(frame);
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.BOTH;
        frame.add(gameWorld, c);

        frame.setVisible(true);

        gameWorld.startGame();
    }
    
}
