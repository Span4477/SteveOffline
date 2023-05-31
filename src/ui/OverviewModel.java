package ui;

import javax.swing.table.AbstractTableModel;

public class OverviewModel extends AbstractTableModel{
    
    private OverviewData data;

    public OverviewModel(OverviewData data) {
        this.data = data;
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

    @Override
    public Object getValueAt(int row, int col) {
        OverviewItem item = data.getItem(row);
        switch(col){
            case 0: return item.getName();
            case 1: return item.getDistance();
            case 2: return item.getType();
            case 3: return item.getVelocity();
            case 4: return item.getAngularVelocity();
            default: return "";
        }
    }

    


}
