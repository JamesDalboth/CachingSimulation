package caches;

import measurements.Measurement;

public class RANDCache implements Cache {
    private final Integer[] entries;

    public RANDCache(int size) {
        this.entries = new Integer[size];

        for (int i = 1; i <= size; i++) {
            entries[i - 1] = i;
        }
    }

    public void query(int item) {
        for (Integer entry : entries) {
            if (entry == item) {
                Measurement.hit();
                return;
            }
        }

        int pos = evict();
        entries[pos] = item;
    }

    private int evict() {
        int pos = (int) Math.floor(Math.random() * entries.length);
        entries[pos] = 0;

        Measurement.miss();

        return pos;
    }

    @Override
    public String toString() {
        return "caches.RANDCache(" + entries.length + ")";
    }
}
