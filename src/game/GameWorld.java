package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;


import monitor.TimerTableDialog;
import monitor.TimerDict;
import readers.GameProperties;
import readers.ShipProperties;
import readers.Universe;

import java.awt.Graphics2D;
import java.awt.Dimension;


public class GameWorld extends JPanel implements KeyListener, Runnable, MouseWheelListener {

    private boolean isGameRunning;
    private Ship playerSpaceship;
    
    private CoordinateConvert convert;
    private CoordinateConvert convertPlanet;
    private Thread gameThread;
    private InputHandler inputHandler;
    private SystemMap systemMap;
    private GameProperties gameProperties;
    private JFrame frame;
    private Universe universe;
    private double deltaTime = 0;

    private boolean isWarpingCamera = false;
    private long initialWarpOffsetX;
    private long initialWarpOffsetY;
    

    private TimerTableDialog timerTableDialog;
    private TimerDict timerDict = new TimerDict();

    // List of ships
    private Ship[] ships;

    public GameWorld(JFrame frame) {
        this.frame = frame;
        
        gameProperties = new GameProperties();
        


        // Set JPanel size to be the same as the frame size
        setPreferredSize(new Dimension(gameProperties.getWindowWidth(), gameProperties.getWindowHeight()));


        // Make JPanel focusable to receive key events
        setFocusable(true);
        
        
        // Add key listener to the game world
        addKeyListener(this);
        addMouseWheelListener(this);

        // Initialize game objects and set initial game state
        initializeGame();

        // Add mouse listener to the game world
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                inputHandler.clickEvent(e, playerSpaceship);
            }
            @Override
            public void mousePressed(MouseEvent e) {
                inputHandler.mousePressed(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                inputHandler.mouseReleased(e, ships);
            }
            
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                inputHandler.mouseDragged(e);
            }
        });

    }

    private void initializeGame() {
        // Create and position game entities (player spaceship, enemies, obstacles, etc.)
        // Set initial game state variables
        timerTableDialog = new TimerTableDialog(frame, timerDict);
        timerTableDialog.setVisible(true);

        long startTime = timerDict.time();
        universe = new Universe();
        universe.setCurrentSystem("Spatium");
        universe.setCurrentPlanet("Spatium I");
        long planetX = universe.getCurrentPlanet().getX();
        long planetY = universe.getCurrentPlanet().getY();
        // Planet coords are:
        System.out.println("Planet coords are: " + planetX + ", " + planetY);
        
        ShipProperties shipProperties = new ShipProperties();

        // Create a PlayerSpaceship object
        playerSpaceship = new Ship(
            shipProperties.shipName("playerStartingShip"),
            shipProperties.armorCapacity("playerStartingShip"),
            shipProperties.armorEmResistance("playerStartingShip"),
            shipProperties.armorThermalResistance("playerStartingShip"),
            shipProperties.armorKineticResistance("playerStartingShip"),
            shipProperties.armorExplosiveResistance("playerStartingShip"),
            shipProperties.armorCurrent("playerStartingShip"),
            shipProperties.shieldCapacity("playerStartingShip"),
            shipProperties.shieldEmResistance("playerStartingShip"),
            shipProperties.shieldThermalResistance("playerStartingShip"),
            shipProperties.shieldKineticResistance("playerStartingShip"),
            shipProperties.shieldExplosiveResistance("playerStartingShip"),
            shipProperties.shieldCurrent("playerStartingShip"),
            shipProperties.hullCapacity("playerStartingShip"),
            shipProperties.hullEmResistance("playerStartingShip"),
            shipProperties.hullThermalResistance("playerStartingShip"),
            shipProperties.hullKineticResistance("playerStartingShip"),
            shipProperties.hullExplosiveResistance("playerStartingShip"),
            shipProperties.hullCurrent("playerStartingShip"),
            planetX,
            planetY,
            0,
            0,
            shipProperties.velocityMax("playerStartingShip"),
            shipProperties.inertiaModifier("playerStartingShip"),
            shipProperties.shipMovementState("playerStartingShip"),
            shipProperties.orbitRadius("playerStartingShip"),
            0,
            0,
            shipProperties.cargoCapacity("playerStartingShip"),
            shipProperties.droneBayCapacity("playerStartingShip"),
            shipProperties.mass("playerStartingShip"),
            shipProperties.shipDiameter("playerStartingShip"),
            shipProperties.highSlots("playerStartingShip"),
            shipProperties.mediumSlots("playerStartingShip"),
            shipProperties.lowSlots("playerStartingShip"),
            shipProperties.rigSlots("playerStartingShip"),
            shipProperties.rigCalibration("playerStartingShip"),
            shipProperties.turretHardpoints("playerStartingShip"),
            shipProperties.launcherHardpoints("playerStartingShip"),
            shipProperties.powerGrid("playerStartingShip"),
            shipProperties.cpu("playerStartingShip"),
            shipProperties.capacitorCapacity("playerStartingShip"),
            shipProperties.capacitorRechargeTime("playerStartingShip"),
            shipProperties.capacitorCurrent("playerStartingShip"),
            shipProperties.imageFileName("playerStartingShip"),
            shipProperties.warpVelocity("playerStartingShip"),
            shipProperties.warpAcceleration("playerStartingShip")
            );
        playerSpaceship.setDisposition(Disposition.PLAYER);


        timerDict.add("initializeGame", startTime);
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Perform rendering of the game world (background, entities, etc.)

        long startTime = timerDict.time();
        universe.draw(g, this, convertPlanet);
        timerDict.add("setBackgroundImage", startTime);

        Graphics2D g2d = (Graphics2D) g;
        
        // Draw grid lines
        
        startTime = timerDict.time();
        GridLines.drawGridLines(g2d, this, convert);
        timerDict.add("drawGridLines", startTime);

        // Draw each ship
        for (Ship ship : ships) {
            ship.draw(g2d, convert, deltaTime);
        }

        
        startTime = timerDict.time();
        SelectionBox.drawSelectionBox(g2d, inputHandler);
        timerDict.add("drawSelectionBox", startTime);

        
        timerTableDialog.update();
    }

    
    

    public void startGame() {
        

        requestFocusInWindow();

        isGameRunning = true;
        gameThread = new Thread(this);
        gameThread.start();


    }

    public void stopGame() {
        isGameRunning = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {


        System.out.println("scalingFactor = " + gameProperties.getDefaultZoom() + " / " + getWidth());
        double scalingFactor = gameProperties.getDefaultZoom() / getWidth();
        long planetX = universe.getCurrentPlanet().getX();
        long planetY = universe.getCurrentPlanet().getY();

        // Create a CoordinateConvert object
        
        convert = new CoordinateConvert(
            (long) (getWidth()),
            (long) (getHeight()),
            (long) (getWidth() * scalingFactor),
            (long) (getHeight() * scalingFactor),
            planetX - (long) (getWidth() * scalingFactor / 2),
            planetY - (long) (getHeight() * scalingFactor / 2)
        );
        scalingFactor = 384400000.0 / getWidth();
        convertPlanet = new CoordinateConvert(
            (long) (getWidth()),
            (long) (getHeight()),
            (long) (getWidth() * scalingFactor),
            (long) (getHeight() * scalingFactor),
            planetX - (long) (getWidth() * scalingFactor / 2),
            planetY - (long) (getHeight() * scalingFactor / 2)
        );

        inputHandler = new InputHandler(frame, convert, convertPlanet, systemMap, universe);
        

        // Add the playerSpaceship to the list of ships
        ships = new Ship[1];
        ships[0] = playerSpaceship;

        long lastUpdateTime = System.nanoTime();
        
        System.out.println("Game loop started");

        

        while (isGameRunning) {
            long now = System.nanoTime();
            deltaTime = (now - lastUpdateTime);
            // convert deltaTime to seconds
            deltaTime /= 1000000000.0;
            lastUpdateTime = now;

            updateGameLogic(deltaTime);
            // Render the game world
            // SwingUtilities.invokeLater(() -> repaint());
            repaint();
            try {
                // Adjust the sleep duration based on your desired frame rate
                Thread.sleep(10); // 16 = 60 FPS (approx.)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGameLogic(double t) {
        long startTime = timerDict.time();
        // Update game entities, handle collisions, check win/lose conditions, etc.
        inputHandler.handleInput(t);
        playerSpaceship.update(t);

        warpCamera();

        timerDict.add("updateGameLogic", startTime);

    }

    private void warpCamera() {
        if (playerSpaceship.getShipMovementState() != ShipMovementState.INWARP) {
            isWarpingCamera = false;
            return;
        }
        
        if (!isWarpingCamera) {
            isWarpingCamera = true;
            Coordinate c = convert.gameToWindow(playerSpaceship.getPositionX(), playerSpaceship.getPositionY());
            initialWarpOffsetX = c.getX();
            initialWarpOffsetY = c.getY();
        } 

        long newX = playerSpaceship.getPositionX() - (long) (initialWarpOffsetX * convert.getScaleFactor());
        long newY = playerSpaceship.getPositionY() - (long) (initialWarpOffsetY * convert.getScaleFactor());

        
        convertPlanet.addGameOffset(
            newX - convert.getOffsetX(),
            newY - convert.getOffsetY());
        convert.setOffset(newX, newY);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Handle key typed events
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle key pressed events
        long startTime = timerDict.time();
        

        inputHandler.keyPressed(String.valueOf(e.getKeyChar()).toUpperCase());
        timerDict.add("keyPressed", startTime);
        

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Handle key released events
        
        long startTime = timerDict.time();
        
        inputHandler.keyReleased(String.valueOf(e.getKeyChar()).toUpperCase());
        timerDict.add("keyReleased", startTime);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Handle mouse wheel moved events
        
        long startTime = timerDict.time();
        if (e.getWheelRotation() < 0) {
            inputHandler.wheelEvent("mouseWheelUp");
        } else {
            inputHandler.wheelEvent("mouseWheelDown");
        }
        timerDict.add("mouseWheelMoved", startTime);
    }


    
}
