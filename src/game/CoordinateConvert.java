package game;

import readers.GameProperties;

public class CoordinateConvert {
    
    private long windowWidth;
    private long windowHeight;
    private long gameWidth;
    private long gameHeight;
    private long offsetX;
    private long offsetY;
    private GameProperties gameProperties = new GameProperties();

    public CoordinateConvert(
        long windowWidth, 
        long windowHeight, 
        long gameWidth, 
        long gameHeight,
        long offsetX,
        long offsetY
    ) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Coordinate windowToGame(long windowX, long windowY) {
        
        long gameX = (long)((double) windowX / (double) windowWidth * (double) gameWidth + (double) offsetX);
        long gameY = (long)((double) windowY / (double) windowHeight * (double) gameHeight + (double) offsetY);
        return new Coordinate(gameX, gameY);
        
    }

    public Coordinate gameToWindow(long gameX, long gameY) {
        
        long windowX = (long) ((double) (gameX - offsetX) / (double) gameWidth * (double) windowWidth);
        long windowY = (long) ((double) (gameY - offsetY) / (double) gameHeight * (double) windowHeight);
        return new Coordinate(windowX, windowY);
        
    }

    public void addOffset(long dx, long dy) {
        // convert from window coords to game coords
        double scalingFactor = getScaleFactor();
        offsetX += dx * scalingFactor;
        offsetY += dy * scalingFactor;
    }

    
    public void addGameOffset(long dx, long dy) {
        
        offsetX += dx;
        offsetY += dy;
    }

    public void addZoom(double zoom) {
        // ensure that the proposed zoom does not exceed those specified by GameProperties
        long maxZoom = gameProperties.getMaxZoom();
        long minZoom = gameProperties.getMinZoom();

        double scalingFactor = (double) gameWidth / (double) windowWidth;
        long oldCenterX = (long) (offsetX + windowWidth * scalingFactor / 2);
        long oldCenterY = (long) (offsetY + windowHeight * scalingFactor / 2);

        long newWidth = (long) (gameWidth * zoom);
        double aspectRatio = (double) windowWidth / (double) windowHeight;
        if (newWidth > maxZoom) {
            newWidth = maxZoom;
        } else if (newWidth < minZoom) {
            newWidth = minZoom;
        }
        gameWidth = newWidth;
        gameHeight = (long) (gameWidth / aspectRatio);

        // adjust the offset to keep the center of the screen in the same place
        scalingFactor = (double) gameWidth / (double) windowWidth;
        offsetX = (long) (oldCenterX - windowWidth * scalingFactor / 2);
        offsetY = (long) (oldCenterY - windowHeight * scalingFactor / 2);
        

    }

    public double getScaleFactor() {
        return gameWidth / windowWidth;
    }

    public void setOffset(long x, long y) {
        offsetX = x;
        offsetY = y;
    }

    public long getOffsetX() {
        return offsetX;
    }

    public long getOffsetY() {
        return offsetY;
    }

    public long getGameWidth() {
        return gameWidth;
    }

}
