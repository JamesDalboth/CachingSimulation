package caches;

import measurements.Measurement;

import java.util.LinkedList;
import java.util.Queue;

public class FIFOCache implements Cache {
    private final Queue<Integer> entries;

    public FIFOCache(int size) {
        entries = new LinkedList<>();
        for (int i = 1; i <= size; i++) {
            entries.add(i);
        }
    }

    public void query(int item) {
        for (Integer entry : entries) {
            if (entry == item) {
                Measurement.hit();
                return;
            }
        }

        evict();
        entries.add(item);
    }

    private void evict() {
        Measurement.miss();
        entries.remove();
    }

    @Override
    public String toString() {
        return "caches.FIFOCache(" + entries.size() + ")";
    }
}
