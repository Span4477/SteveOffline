package monitor;

public class TimerItem {
    
    private String name;
    private double time = 0;
    

    public TimerItem(String name) {
        this.name = name;
    }

    public void add(double time) {
        this.time += time;
    }

    public String getName() {
        return name;
    }

    public double getTime() {
        return time;
    }
}
