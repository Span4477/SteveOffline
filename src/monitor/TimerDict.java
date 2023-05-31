package monitor;

public class TimerDict {

    private TimerItem[] items = new TimerItem[64];
    

    public TimerDict() {

    }

    public void add(String name, long startTime) {
        double time = getDuration(startTime, time());
        // if the name is already in the dict, add time to that item
        // otherwise, create a new item
        for (int i = 0; i < items.length; i++) {
            // Make the list bigger if it's full
            if (i == items.length - 1) {
                TimerItem[] newItems = new TimerItem[items.length * 2];
                for (int j = 0; j < items.length; j++) {
                    newItems[j] = items[j];
                }
                items = newItems;
                // Add the new item
                items[i] = new TimerItem(name);
                items[i].add(time);
                return;
            }

            if (items[i] == null) {
                items[i] = new TimerItem(name);
                items[i].add(time);
                return;
            }
            if (items[i].getName().equals(name)) {
                items[i].add(time);
                return;
            }
        }
    }
    public long time() {
        return System.nanoTime();
    }
    public double getDuration(long a, long b) {
        return (b - a) / 1000000000.0;
    }

    public int len() {
        int count = 0;
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                count++;
            }
        }
        return count;
    }

    public TimerItem[] getItems() {
        return items;
    }

    public TimerItem getItem(int i) {
        return items[i];
    }

}
