package ui;
import game.*;
import readers.*;
public class OverviewData {
    private Universe universe;
    private Ship[] ships;
    private OverviewItem[] items;


    public OverviewData() {
    }

    public void setItems(Universe universe, Ship[] ships, Ship player){
        this.universe = universe;
        this.ships = ships;

        items = new OverviewItem[len()];
        int i = 0;
        for (Ship s : ships) {
            items[i] = new OverviewItem(player, s);
            i++;
        }
        SolarSystem s = universe.getCurrentSystem();
        for (Planet p : s.getPlanets()) {
            items[i] = new OverviewItem(player, p);
            i++;
        }
        for (Gate g : s.getGates()) {
            items[i] = new OverviewItem(player, g);
            i++;
        }
    }


    public OverviewItem getItem(int i) {
        return items[i];
    }



    public int len() {
        SolarSystem s = universe.getCurrentSystem();

        return ships.length + s.getGates().length + s.getPlanets().length + 1;
    }


}
