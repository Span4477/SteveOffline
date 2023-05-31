package game;
import java.awt.*;
import javax.swing.*;


public class GridLines {

    public static void drawGridLines(
        Graphics2D g2d, 
        JPanel panel, 
        CoordinateConvert convert
        ) {
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Get dimensions of panel
        int w = panel.getWidth();
        int h = panel.getHeight();

        // Draw X and Y axes
        g2d.setColor(Color.ORANGE);
        g2d.setStroke(new BasicStroke(2));
        int x0 = (int) (w * 0.05);
        int y0 = (int) (h * 0.05);
        int x1 = (int) (w * 0.95);
        int y1 = (int) (h * 0.05);
        g2d.drawLine(x0, y0, x1, y1);
        x0 = (int) (w * 0.05);
        y0 = (int) (h * 0.05);
        x1 = (int) (w * 0.05);
        y1 = (int) (h * 0.95);
        g2d.drawLine(x0, y0, x1, y1);

        // Draw grid lines with labels
        g2d.setStroke(new BasicStroke(1));
        g2d.setFont(new Font("Arial", Font.PLAIN, 10));

        // Get scale factor to convert from game coordinates to window coordinates
        double scaleFactor = convert.getScaleFactor();
        int gridLength = (int) (w * 0.1);

        // Draw grid lines and labels for X axis
        x0 = (int) (w * 0.05);
        x1 = (int) (w * 0.95);
        y0 = (int) (h * 0.05);
        y1 = (int) (h * 0.95);
        for (int i = x0 + gridLength; i <= x1; i += gridLength) {
            g2d.drawLine(i, y0, i, y1);
            
            String label = String.format("%.0f", (i - x0) * scaleFactor / 1000);
            label = label + " km";
            g2d.drawString(label, i, y0 - 5);
        }

        // Draw grid lines and labels for Y axis
        x0 = (int) (w * 0.05);
        x1 = (int) (w * 0.95);
        y0 = (int) (h * 0.05);
        y1 = (int) (h * 0.95);
        for (int i = y0 + gridLength; i <= y1; i += gridLength) {
            g2d.drawLine(x0, i, x1, i);
            
            String label = String.format("%.0f", (i - y0) * scaleFactor / 1000);
            label = label + " km";
            // Ensure that the right side of the label is not cut off
            int labelWidth = g2d.getFontMetrics().stringWidth(label);
            g2d.drawString(label, x0 - labelWidth - 5, i);
        }
        

    }
}
