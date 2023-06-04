package game;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import readers.Controls;
import readers.Universe;
import ui.SystemMap;

public class InputHandler {

    private JFrame frame;
    private boolean panUpPressed = false;
    private boolean panDownPressed = false;
    private boolean panLeftPressed = false;
    private boolean panRightPressed = false;

    private Universe universe;
    private SystemMap map;

    private boolean isDragging = false;
    
    private Coordinate dragStart;
    private Coordinate dragEnd;

    private CoordinateConvert convert;
    private CoordinateConvert convertPlanet;

    private Controls controls = new Controls();

    public InputHandler(
        JFrame frame, 
        CoordinateConvert convert, 
        CoordinateConvert convertPlanet, 
        SystemMap map, 
        Universe universe) {
        this.convert = convert;
        this.convertPlanet = convertPlanet;
        this.map = map;
        this.frame = frame;
        this.universe = universe;
    }

    public void handleInput(double seconds) {
        long offset = 0;
        long offsetGame = 0;
        if (
            panUpPressed || 
            panDownPressed || 
            panLeftPressed || 
            panRightPressed
        ) {
            offset = (long) (100.0 * seconds);
            offsetGame = (long) (offset * convert.getScaleFactor());
        }
        if (panUpPressed) {
            convert.addOffset(0, - offset);
            convertPlanet.addGameOffset(0, - offsetGame);
        }
        if (panDownPressed) {
            convert.addOffset(0, offset);
            convertPlanet.addGameOffset(0, offsetGame);
        }
        if (panLeftPressed) {
            convert.addOffset(- offset, 0);
            convertPlanet.addGameOffset(- offsetGame, 0);
        }
        if (panRightPressed) {
            convert.addOffset(offset, 0);
            convertPlanet.addGameOffset(offsetGame, 0);
        }
    }

    public void wheelEvent(String k) {

        if (controls == null) {
            System.out.println("Controls is null");
            // controls = new Controls();
        }

        String control = controls.getControl(k);
        
        if (control != null) {
            switch (control) {
                case "zoomIn":
                    convert.addZoom(0.9);;
                    break;
                case "zoomOut":
                    convert.addZoom(1.1);
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }


    public void keyPressed(String k) {
        
        if (controls == null) {
            controls = new Controls();
        }

        String control = controls.getControl(k);
        
        if (control != null) {
            switch (control) {
                case "panUp":
                    panUpPressed = true;
                    break;
                case "panDown":
                    panDownPressed = true;
                    break;
                case "panLeft":
                    panLeftPressed = true;
                    break;
                case "panRight":
                    panRightPressed = true;
                    break;
                case "systemMap":
                    openSystemMap();
                    break;
                default:
                    break;
            }
        }
    }
    public void keyReleased(String k) {
        
        if (controls == null) {
            controls = new Controls();
        }

        String control = controls.getControl(k);
        if (control != null) {
            switch (control) {
                case "panUp":
                    panUpPressed = false;
                    break;
                case "panDown":
                    panDownPressed = false;
                    break;
                case "panLeft":
                    panLeftPressed = false;
                    break;
                case "panRight":
                    panRightPressed = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void clickEvent(MouseEvent e, Ship s) {

        
        isDragging = false;

    }

    public void mousePressed(MouseEvent e, Ship s) {
        System.out.println("Mouse pressed: " + e.getButton() + " at (" + e.getX() + ", " + e.getY() + ")");
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            
            isDragging = true;
            dragStart = new Coordinate(e.getX(), e.getY());
        } else {
            isDragging = false;
            dragStart = null;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            // Make the playerSpaceship approach the point
            Coordinate c = convert.windowToGame(e.getX(), e.getY());
            long x = c.getX();
            long y = c.getY();
            s.approach(x, y);

            System.out.println("Clicked at game coordinates (" + x + ", " + y + ")");
            System.out.println("Clicked at window coordinates (" + e.getX() + ", " + e.getY() + ")");
        } 
        if (e.getButton() == 5) {
            Coordinate c = convert.windowToGame(e.getX(), e.getY());
            long x = c.getX();
            long y = c.getY();
            s.startWarp(x, y);
            
        }
    }
    public void mouseReleased(MouseEvent e, Ship[] ships) {
        
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (isDragging && dragStart != null && dragEnd != null) {
                Coordinate a = convert.windowToGame(dragStart.getX(), dragStart.getY());
                Coordinate b = convert.windowToGame(dragEnd.getX(), dragEnd.getY());
                for (Ship s : ships) {
                    //If the ship is within the box created by dragStart and dragEnd, select it
                    if (s.getPositionX() > Math.min(a.getX(), b.getX()) &&
                        s.getPositionX() < Math.max(a.getX(), b.getX()) &&
                        s.getPositionY() > Math.min(a.getY(), b.getY()) &&
                        s.getPositionY() < Math.max(a.getY(), b.getY())) {
                        s.setSelected(true);
                    } else {
                        s.setSelected(false);
                    }
                }
                isDragging = false;
                dragEnd = null;
            } else {
                // If the user clicked on a ship, select it
                Coordinate c = new Coordinate(e.getX(), e.getY());
                for (Ship s : ships) {
                    // location of ship on screen
                    Coordinate shipLocation = convert.gameToWindow(s.getPositionX(), s.getPositionY());
                    // distance from click to ship
                    double distance = Math.sqrt(Math.pow(shipLocation.getX() - c.getX(), 2) + Math.pow(shipLocation.getY() - c.getY(), 2));
                    if (distance < 10) {
                        s.setSelected(true);
                    } else {
                        s.setSelected(false);
                    }
                }
            }
            
        }
    }

    public void mouseDragged(MouseEvent e) {
        
        if (isDragging){
            dragEnd = new Coordinate(e.getX(), e.getY());
        }

    }
    

    public boolean getIsDragging() {
        return isDragging;
    }

    public Coordinate getDragStart() {
        return dragStart;
    }

    public Coordinate getDragEnd() {
        return dragEnd;
    }

    public void openSystemMap() {
        if (map == null) {
            System.out.println("Opening system map");
            map = new SystemMap(frame, universe);
        }
        map.setVisible(true);
    }


}
