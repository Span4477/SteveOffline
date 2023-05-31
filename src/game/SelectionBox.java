package game;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Color;

public class SelectionBox {
    public static void drawSelectionBox(Graphics2D g2d, InputHandler inputHandler) {
        
        if (inputHandler.getIsDragging() && inputHandler.getDragStart() != null && inputHandler.getDragEnd() != null) {
            
            Coordinate dragStart = inputHandler.getDragStart();
            Coordinate dragEnd = inputHandler.getDragEnd();

            double x0 = dragStart.getX();
            double y0 = dragStart.getY();
            double x1 = dragEnd.getX();
            double y1 = dragEnd.getY();

            double x = Math.min(x0, x1);
            double y = Math.min(y0, y1);
            double width = Math.abs(x1 - x0);
            double height = Math.abs(y1 - y0);

            // Draw green rectangle with width of 1
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(1));
            g2d.drawRect((int) x, (int) y, (int) width, (int) height);

        }
    }
}
