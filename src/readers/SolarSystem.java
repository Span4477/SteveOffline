package readers;

public class SolarSystem {
    private String name;
    private String image;
    private Planet[] planets;
    private Gate[] gates;
    private int x;
    private int y;

    public SolarSystem(String name, String image, Planet[] planets, Gate[] gates, int x, int y) {
        this.name = name;
        this.image = image;
        this.planets = planets;
        this.x = x;
        this.y = y;
        this.gates = gates;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public Planet[] getPlanets() {
        return planets;
    }
    public Gate[] getGates() {
        return gates;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    

}
