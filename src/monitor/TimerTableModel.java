package monitor;

import javax.swing.table.AbstractTableModel;


public class TimerTableModel extends AbstractTableModel{
    private TimerDict dict;

    public TimerTableModel(TimerDict dict) {
        this.dict = dict;
    }

    @Override
    public int getRowCount() {
        return dict.len();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int col){
        if (col == 0) {
            return "Name";
        } else {
            return "Time";
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        
        if (col == 0) {
            return dict.getItem(row).getName();
        } else {
            return dict.getItem(row).getTime();
        }
    }

    


}
