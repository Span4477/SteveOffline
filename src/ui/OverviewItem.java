package ui;
import game.Ship;
import readers.Planet;
import readers.Gate;

public class OverviewItem {
    private String name;
    private double distance;
    private String type;
    private double velocity;
    private double angularVelocity;

    public OverviewItem(Ship player, Ship ship) {
        this.name = ship.getShipName();
        this.distance = getDistance(player.getPositionX(), player.getPositionY(), ship.getPositionX(), ship.getPositionY());
        this.type = "Ship";
        this.velocity = getVelocity(ship.getVelocityX(), ship.getVelocityY());
        this.angularVelocity = getAngularVelocity(player, ship);
    }
    
    public OverviewItem(Ship player, Planet planet) {
        this.name = planet.getName();
        this.distance = getDistance(player.getPositionX(), player.getPositionY(), planet.getX(), planet.getY());
        this.type = "Ship";
        this.velocity = 0;
        this.angularVelocity = 0;
    }
    
    public OverviewItem(Ship player, Gate gate) {
        this.name = gate.getName();
        this.distance = getDistance(player.getPositionX(), player.getPositionY(), gate.getX(), gate.getY());
        this.type = "Ship";
        this.velocity = 0;
        this.angularVelocity = 0;
    }

    private double getDistance(long x0, long y0, long x1, long y1) {
        return Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
    }
    private double getDistance(double x0, double y0, double x1, double y1) {
        return Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
    }

    private double getVelocity(double vx, double vy) {
        return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
    }

    private double getAngularVelocity(Ship player, Ship ship) {
        double x0 = player.getPositionX();
        double y0 = player.getPositionY();
        double x1 = ship.getPositionX();
        double y1 = ship.getPositionY();
        double vx0 = player.getVelocityX();
        double vy0 = player.getVelocityY();
        double vx1 = ship.getVelocityX();
        double vy1 = ship.getVelocityY();

        double r = getDistance(x0, y0, x1, y1);
        double v0 = getVelocity(vx0, vy0);
        double v1 = getVelocity(vx1, vy1);

        double dot = (vx0 * vx1) + (vy0 * vy1);
        double angle = Math.acos(dot / (v0 * v1));

        return (v1 * Math.sin(angle)) / r;
    }

    public String getName() {
        return name;
    }

    public double getDistance() {
        return distance;
    }

    public String getType() {
        return type;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAngularVelocity() {
        return angularVelocity;
    }

}
