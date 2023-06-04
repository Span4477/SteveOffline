package ui;

import readers.*;
import game.*;

import javax.swing.table.AbstractTableModel;

public class OverviewModel extends AbstractTableModel{
    
    private OverviewData data;
    private static final double AU_TO_M = 149597870700.0;

    public OverviewModel(Universe universe, Ship[] ships, Ship player) {
        setData(universe, ships, player);
    }

    public void setData(Universe universe, Ship[] ships, Ship player) {
        this.data = new OverviewData(universe, ships, player);
    }

    @Override
    public int getRowCount() {
        return data.len();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int col){
        switch(col){
            case 0: return "Name";
            case 1: return "Distance";
            case 2: return "Type";
            case 3: return "Velocity";
            case 4: return "Angular Velocity";
            default: return "";
        }
        
    }

    public String getTypeAt(int row) {
        return data.getItem(row).getType();
    }

    @Override
    public Object getValueAt(int row, int col) {
        OverviewItem item = data.getItem(row);
        if (item == null) {
            System.out.println("item is null at (" + row + ", " + col + ")");
            return "";
        }
        switch(col){
            case 0: return item.getName();
            case 1: return formatDistance(item.getDistance());
            case 2: return item.getType();
            case 3: return formatVelocity(item.getVelocity());
            case 4: return formatVelocity(item.getAngularVelocity());
            default: return "";
        }
    }

    private String formatDistance(double distance) {
        if (distance < 1000) {
            return String.format("%.2f m", distance);
        } else if (distance < 1000000) {
            return String.format("%.2f km", distance / 1000);
        } else if (distance < AU_TO_M * 0.1) {
            return String.format("%.2f Mm", distance / 1000000);
        } else {
            return String.format("%.2f AU", distance / AU_TO_M);
        }
    }

    private String formatVelocity(double v) {
        if (v < 1000) {
            return String.format("%.2f m/s", v);
        } else if (v < 1000000) {
            return String.format("%.2f km/s", v / 1000);
        } else if (v < AU_TO_M * 0.1) {
            return String.format("%.2f Mm/s", v / 1000000);
        } else {
            return String.format("%.2f AU/s", v / AU_TO_M);
        }
    }


}
