package ui.overview;

import readers.*;
import game.*;

import javax.swing.*;
import javax.swing.table.TableRowSorter;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class OverviewDialog extends JDialog {
    private OverviewModel model;
    private JTable table;
    private Ship player;
    

    public OverviewDialog(JFrame parent, Universe universe, Ship[] ships, Ship player) {
        super(parent, "Overview", false);

        model = new OverviewModel(universe, ships, player);
        this.player = player;
        
        
        table = new JTable(model);
        // Make the table selectable
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(false);
        table.setColumnSelectionAllowed(false);
        table.setCellSelectionEnabled(false);
        table.setDragEnabled(false);


        

        JScrollPane scrollPane = new JScrollPane(table);
        
        setSize(400, 400);
        setVisible(true);
        setFocusable(true);
        
        setLocationRelativeTo(parent);

        // put it in the bottom right corner
        setLocation(parent.getWidth() - getWidth(), parent.getHeight() - getHeight());

        
        // Add the scroll pane to this panel
        add(scrollPane);

        // Add a mouse listener to the table
        table.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            @Override
            public void mousePressed(MouseEvent e) {
                handleInput(e);
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            }
            @Override
            public void mouseEntered(MouseEvent e) {
            }
            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        
        
        // Make the table sortable
        TableRowSorter<OverviewModel> sorter = new TableRowSorter<OverviewModel>(model);
        // When the table sorts by distance (column 1), it should set by the numeric value, not the formatted string
        sorter.setComparator(1, new DistanceComparator());
        // When the table sorts by velocity (column 3), it should set by the numeric value, not the formatted string
        sorter.setComparator(3, new VelocityComparator());
        // When the table sorts by angular velocity (column 4), it should set by the numeric value, not the formatted string
        sorter.setComparator(4, new VelocityComparator());
        table.setRowSorter(sorter);


        
        

    }

    

    public void setData(Universe universe, Ship[] ships, Ship player) {
        model.setData(universe, ships, player);
    }

    public void update() {
        model.fireTableDataChanged();
    }

    private void handleInput(MouseEvent e) {

        // Get the row and column of the selected cell
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        System.out.println("Overview row: " + row + " col: " + col);

        // Get the type of the selected object
        String type = model.getTypeAt(row);
        System.out.println("Overview type: " + type);
        
        if (e.getButton() == MouseEvent.BUTTON3) {
            
            // Make the playerSpaceship approach the table item
            OverviewItem i = model.getItem(row);
            player.approach(i.getX(), i.getY());

        } 
        if (e.getButton() == 5) {

            // Warp to the item
            OverviewItem i = model.getItem(row);
            player.startWarp(i.getX(), i.getY());
            
        }

    }
    
}
