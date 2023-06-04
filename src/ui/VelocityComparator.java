package ui;


import java.util.Comparator;

public class VelocityComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        double aVelocity = parseVelocity(a);
        double bVelocity = parseVelocity(b);
        if (aVelocity < bVelocity) {
            return -1;
        } else if (aVelocity > bVelocity) {
            return 1;
        } else {
            return 0;
        }
        
    }

    private double parseVelocity(String velocity) {
        String[] parts = velocity.split(" ");
        double value = Double.parseDouble(parts[0]);
        String unit = parts[1];
        switch(unit) {
            case "km/s": return value * 1000;
            case "Mm/s": return value * 1000000;
            case "AU/s": return value * 149597870700.0;
            default: return value;
        }
    }
}