package monitor;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

public class TimerTableDialog extends JDialog {
    private TimerTableModel model;
    private JTable table;

    public TimerTableDialog(JFrame parent, TimerDict dict) {
        super(parent, "Timers", false);

        model = new TimerTableModel(dict);
        table = new JTable(model);
        
        // sorting based on the numeric value TimerItem time descending
        TableRowSorter<TimerTableModel> sorter = new TableRowSorter<>(model);
        sorter.setComparator(1, (Double d1, Double d2) -> d2.compareTo(d1));
        table.setRowSorter(sorter);
        // apply sorting
        sorter.toggleSortOrder(1);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(parent);
    }

    public void update() {
        model.fireTableDataChanged();
    }
    
}
