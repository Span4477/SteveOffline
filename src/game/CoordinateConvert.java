package game;

import readers.GameProperties;

public class CoordinateConvert {
    
    private long windowWidth;
    private long windowHeight;
    private long shipWidth;
    private long shipHeight;
    private long planetWidth;
    private long planetHeight;
    private long shipOffsetX;
    private long shipOffsetY;
    private long planetOffsetX;
    private long planetOffsetY;
    private GameProperties gameProperties = new GameProperties();

    public CoordinateConvert(
        long windowWidth, 
        long windowHeight, 
        long shipX,
        long shipY,
        long planetX,
        long planetY,
        double shipMeters,
        double planetMeters
    ) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.shipWidth = (long) shipMeters;
        this.shipHeight = (long) (windowHeight * shipMeters / windowWidth);
        this.planetWidth = (long) planetMeters;
        this.planetHeight = (long) (windowHeight * planetMeters / windowWidth);
        this.shipOffsetX = this.shipWidth / 2;
        this.shipOffsetY = this.shipHeight / 2;
    }

    public Coordinate windowToShip(long windowX, long windowY) {
        
        long shipX = (long)((double) windowX / (double) windowWidth * (double) shipWidth + (double) shipOffsetX);
        long shipY = (long)((double) windowY / (double) windowHeight * (double) shipHeight + (double) shipOffsetY);

        return new Coordinate(shipX, shipY);
        
    }

    public Coordinate shipToWindow(long shipX, long shipY) {
        
        long windowX = (long) ((double) (shipX - shipOffsetX) / (double) shipWidth * (double) windowWidth);
        long windowY = (long) ((double) (shipY - shipOffsetY) / (double) shipHeight * (double) windowHeight);
        return new Coordinate(windowX, windowY);
        
    }
    
    public Coordinate windowToPlanet(long windowX, long windowY) {
        
        long planetX = (long)((double) windowX / (double) windowWidth * (double) planetWidth + (double) planetOffsetX);
        long planetY = (long)((double) windowY / (double) windowHeight * (double) planetHeight + (double) planetOffsetY);

        return new Coordinate(planetX, planetY);
        
    }

    public Coordinate planetToWindow(long planetX, long planetY) {
        
        long windowX = (long) ((double) (planetX - planetOffsetX) / (double) planetWidth * (double) windowWidth);
        long windowY = (long) ((double) (planetY - planetOffsetY) / (double) planetHeight * (double) windowHeight);
        return new Coordinate(windowX, windowY);
        
    }

    public Coordinate shipToPlanet(long shipX, long shipY) {
        Coordinate window = shipToWindow(shipX, shipY);
        return windowToPlanet(window.getX(), window.getY());
    }

    public Coordinate planetToShip(long planetX, long planetY) {
        Coordinate window = planetToWindow(planetX, planetY);
        return windowToShip(window.getX(), window.getY());
    }

    
    public void addZoom(double zoom) {
        // ensure that the proposed zoom does not exceed those specified by GameProperties
        long maxZoom = gameProperties.getMaxZoom();
        long minZoom = gameProperties.getMinZoom();

        double scalingFactor = getScaleFactor();
        long oldCenterX = (long) (shipOffsetX + windowWidth * scalingFactor / 2);
        long oldCenterY = (long) (shipOffsetY + windowHeight * scalingFactor / 2);

        long newWidth = (long) (shipWidth * zoom);
        double aspectRatio = (double) windowWidth / (double) windowHeight;
        if (newWidth > maxZoom) {
            newWidth = maxZoom;
        } else if (newWidth < minZoom) {
            newWidth = minZoom;
        }
        shipWidth = newWidth;
        shipHeight = (long) (shipWidth / aspectRatio);

        // adjust the offset to keep the center of the screen in the same place
        scalingFactor = getScaleFactor();
        shipOffsetX = (long) (oldCenterX - windowWidth * scalingFactor / 2);
        shipOffsetY = (long) (oldCenterY - windowHeight * scalingFactor / 2);
        

    }

    public double getScaleFactor() {
        return (double) shipWidth / (double) windowWidth;
    }
    
    public void addShipOffsetFromWindow(long windowDeltaX, long windowDeltaY) {
        // Convert the window pixels to ship meters
        double scalingFactor = getScaleFactor();
        long shipDeltaX = (long) (windowDeltaX * scalingFactor);
        long shipDeltaY = (long) (windowDeltaY * scalingFactor);
        this.shipOffsetX += shipDeltaX;
        this.shipOffsetY += shipDeltaY;
        this.planetOffsetX += shipDeltaX;
        this.planetOffsetY += shipDeltaY;
    }

    public void setShipOffset(long x, long y) {
        this.shipOffsetX = x;
        this.shipOffsetY = y;
        Coordinate planet = shipToPlanet(x, y);
        this.planetOffsetX = planet.getX();
        this.planetOffsetY = planet.getY(); 
        
    }

    public long getShipOffsetX() {
        return shipOffsetX;
    }

    public long getShipOffsetY() {
        return shipOffsetY;
    }

    public long getShipWidth() {
        return shipWidth;
    }
    
    public long getPlanetOffsetX() {
        return planetOffsetX;
    }

    public long getPlanetOffsetY() {
        return planetOffsetY;
    }

    public long getPlanetWidth() {
        return planetWidth;
    }

}
