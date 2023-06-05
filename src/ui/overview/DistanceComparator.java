package ui.overview;

import java.util.Comparator;

public class DistanceComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        double aDistance = parseDistance(a);
        double bDistance = parseDistance(b);
        if (aDistance < bDistance) {
            return -1;
        } else if (aDistance > bDistance) {
            return 1;
        } else {
            return 0;
        }
        
    }

    private double parseDistance(String distance) {
        String[] parts = distance.split(" ");
        double value = Double.parseDouble(parts[0]);
        String unit = parts[1];
        switch(unit) {
            case "km": return value * 1000;
            case "Mm": return value * 1000000;
            case "AU": return value * 149597870700.0;
            default: return value;
        }
    }
}