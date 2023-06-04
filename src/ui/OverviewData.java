package ui;
import game.*;
import readers.*;

public class OverviewData {
    private Universe universe;
    private Ship[] ships;
    private OverviewItem[] items;

    public OverviewData(Universe universe, Ship[] ships, Ship player) {
        setItems(universe, ships, player);
    }

    public void setItems(Universe universe, Ship[] ships, Ship player){
        this.universe = universe;
        this.ships = ships;

        int len = len();

        if (len == 0) {
            return;
        }

        items = new OverviewItem[len];
        int i = 0;
        if (ships != null) {
            for (Ship s : ships) {
                if (s.getDisposition() == Disposition.PLAYER) {
                    continue;
                }
                items[i] = new OverviewItem(player, s);

                i++;
            }
        }
        if (universe != null) {
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
    }

    public OverviewItem getItem(int i) {
        return items[i];
    }

    public int len() {
        SolarSystem s = universe.getCurrentSystem();
        int i = 0;
        if (ships != null) {
            i += ships.length - 1;
        }
        if (s != null) {
            i += s.getGates().length + s.getPlanets().length;
        }
        
        return i;
    }

}
