package ui;

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
        table = new JTable(model);
        this.player = player;
        
        

        JScrollPane scrollPane = new JScrollPane(table);
        
        setSize(400, 400);
        setVisible(true);
        setLocationRelativeTo(parent);

        // put it in the bottom right corner
        setLocation(parent.getWidth() - getWidth(), parent.getHeight() - getHeight());

        // Create and set the content pane
        // Add mouse listener
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle mouse click event

                handleInput(e);
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Handle mouse press event
                handleInput(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Handle mouse release event
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Handle mouse enter event
                System.out.println("Mouse entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Handle mouse exit event
                System.out.println("Mouse exited");
            }
        });

        // Add the scroll pane to this panel
        add(scrollPane);

        
        
        // Make the table sortable
        TableRowSorter<OverviewModel> sorter = new TableRowSorter<OverviewModel>(model);
        // When the table sorts by distance (column 1), it should set by the numeric value, not the formatted string
        sorter.setComparator(1, new DistanceComparator());
        // When the table sorts by velocity (column 2), it should set by the numeric value, not the formatted string
        sorter.setComparator(2, new VelocityComparator());
        // When the table sorts by angular velocity (column 3), it should set by the numeric value, not the formatted string
        sorter.setComparator(3, new VelocityComparator());



        table.setRowSorter(sorter);


        // Make the table selectable
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        


        setFocusable(true);
        requestFocusInWindow();



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
            
            // Make the playerSpaceship approach the point
            // Coordinate c = convert.windowToGame(e.getX(), e.getY());
            // long x = c.getX();
            // long y = c.getY();
            // s.approach(x, y);

            
        } 
        if (e.getButton() == 5) {
            // Coordinate c = convert.windowToGame(e.getX(), e.getY());
            // long x = c.getX();
            // long y = c.getY();
            // s.startWarp(x, y);
            
        }


    }
    
}
