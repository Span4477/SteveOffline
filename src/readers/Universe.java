package readers;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import com.fasterxml.jackson.databind.ObjectMapper;

import game.CoordinateConvert;

import com.fasterxml.jackson.databind.JsonNode;

public class Universe {
    private String galaxyName;
    private String galaxyImage;
    private SolarSystem[] systems;
    // 1 AU to meters
    private static final double AU_TO_M = 149597870700.0;

    private String currentSystem = "Spatium";
    private String currentPlanet = "Spatium I";
    private String currentGate;

    
    private BufferedImage backgroundImage;
    private int windowWidth;
    private int windowHeight;
    private Image imageBackground;

    public Universe() {
        ObjectMapper objectMapper = new ObjectMapper();
        
        File configFile = new File("src/config/universe.json");

        try {
            JsonNode rootNode = objectMapper.readTree(configFile);
            JsonNode galaxyNode = rootNode.get("galaxy");
            JsonNode systemsNode = galaxyNode.get("systems");
            // The systems node contains a list of systems
            // Each system has a name, location, image, and a list of planets
            // Each planet has a name, location, and image

            // Retrieve the values and set them in the properties
            galaxyName = galaxyNode.get("name").asText();
            galaxyImage = galaxyNode.get("image").asText();
            systems = new SolarSystem[systemsNode.size()];

            for (int j = 0; j < systemsNode.size(); j++) {
                JsonNode system = systemsNode.get(j);
                String systemName = system.get("name").asText();
                System.out.println(systemName);
                String systemImage = system.get("image").asText();
                // The system's location is a list of two integers
                // The first integer is the x coordinate
                // The second integer is the y coordinate
                int systemX = system.get("location").get(0).asInt();
                int systemY = system.get("location").get(1).asInt();

                
                Planet[] planets = new Planet[system.get("planets").size()];
                
                JsonNode planetsNode = system.get("planets");
                for (int i = 0; i < planetsNode.size(); i++) {

                    JsonNode planet = planetsNode.get(i);
                    String planetName = planet.get("name").asText();
                    System.out.println(planetName);
                    String planetImage = planet.get("image").asText();

                    // The planet's location is a list of two integers
                    // The first integer is the x coordinate
                    // The second integer is the y coordinate
                    long planetX = (long) (AU_TO_M * planet.get("location").get(0).asDouble());
                    long planetY = (long) (AU_TO_M * planet.get("location").get(1).asDouble());

                    

                    planets[i] = new Planet(planetName, planetImage, planetX, planetY);
                }

                // Add gates to the system
                JsonNode gatesNode = system.get("gates");
                Gate[] gates = new Gate[gatesNode.size()];
                for (int i = 0; i < gatesNode.size(); i++) {
                    JsonNode gate = gatesNode.get(i);
                    String gateName = gate.get("name").asText();
                    String gateImage = gate.get("image").asText();
                    long gateX = (long) (AU_TO_M * gate.get("location").get(0).asDouble());
                    long gateY = (long) (AU_TO_M * gate.get("location").get(1).asDouble());
                    gates[i] = new Gate(gateName, gateImage, gateX, gateY);
                }

                
                // add the system to the list of systems
                systems[j] = new SolarSystem(systemName, systemImage, planets, gates, systemX, systemY);;
                
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGalaxyName() {
        return galaxyName;
    }
    public String getGalaxyImage() {
        return galaxyImage;
    }
    public SolarSystem[] getSystems() {
        return systems;
    }
    public SolarSystem getCurrentSystem() {
        for (SolarSystem s : systems) {
            if (s.getName().equals(currentSystem)) {
                return s;
            }
        }
        return null;
    }
    public Planet getCurrentPlanet() {
        for (Planet p : getCurrentSystem().getPlanets()) {
            if (p.getName().equals(currentPlanet)) {
                return p;
            }
        }
        return null;
    }
    public Gate getCurrentGate() {
        for (Gate g : getCurrentSystem().getGates()) {
            if (g.getName().equals(currentGate)) {
                return g;
            }
        }
        return null;
    }
    public void setCurrentSystem(String currentSystem) {
        this.currentSystem = currentSystem;
        backgroundImage = null;
    }
    public void setCurrentPlanet(String currentPlanet) {
        this.currentPlanet = currentPlanet;
        
        currentGate = null;
    }
    public void setCurrentGate(String currentGate) {
        this.currentGate = currentGate;
        
        currentPlanet = null;
    }

    public void draw(Graphics g, JPanel p, CoordinateConvert convert) {
        if (currentSystem == null) {
            return;
        }
        drawBackgroundImage(g, p);
        drawLocations(g, p, convert);
    }

    
    private void loadBackgroundImage() {
        // Load the background image

        // Get the image path
        String imagePath = getCurrentSystem().getImage();

        // Load the image
        try {
            URL resourceUrl = getClass().getResource(imagePath);

            backgroundImage = ImageIO.read(resourceUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawBackgroundImage(Graphics g, JPanel p) {

        if (backgroundImage == null) {
            loadBackgroundImage();
        }

        // Make the background black
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, p.getWidth(), p.getHeight());


        if (windowHeight != p.getHeight() || windowWidth != p.getWidth()) {
            windowHeight = p.getHeight();
            windowWidth = p.getWidth();
            // adjust the size of the backgroundImage so that it fits the window while maintaining the aspect ratio
            double widthRatio = (double) p.getWidth() / backgroundImage.getWidth();
            double heightRatio = (double) p.getHeight() / backgroundImage.getHeight();
            double ratio = Math.max(widthRatio, heightRatio);
            int newWidth = (int) (backgroundImage.getWidth() * ratio);
            int newHeight = (int) (backgroundImage.getHeight() * ratio);
            imageBackground = backgroundImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        }

        // Center the background image
        int x = (p.getWidth() - imageBackground.getWidth(p)) / 2;
        int y = (p.getHeight() - imageBackground.getHeight(p)) / 2;

        g.drawImage(imageBackground, x, y, p);

    }

    
    private void drawLocations(Graphics g, JPanel p, CoordinateConvert convert) {
        if (convert == null) {
            return;
        }
        // Draw the locations
        for (Planet planet : getCurrentSystem().getPlanets()) {
            planet.draw(g, p, convert);
        }
        for (Gate gate : getCurrentSystem().getGates()) {
            gate.draw(g, p, convert);
        }
    }
    
}
