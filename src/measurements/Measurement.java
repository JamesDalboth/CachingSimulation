package measurements;

import distributions.StudentstTable;

import java.util.ArrayList;
import java.util.List;

public class Measurement {
    private static final double ALPHA = 99.9;
    private static Integer MISSES = 0;
    private static Integer QUERIES = 0;

    private static List<Double> missRates = new ArrayList<>();
    private static List<Double> hitRatios = new ArrayList<>();

    public static void start() {
        MISSES = 0;
        QUERIES = 0;
    }

    public static void finish(Double time) {
        double hitRatio = (QUERIES - MISSES) / (double) QUERIES;
        double missRate = MISSES / time;

        missRates.add(missRate);
        hitRatios.add(hitRatio);
    }

    public static void hit() {
        QUERIES++;
    }

    public static void miss() {
        QUERIES++;
        MISSES++;
    }

    public static void stats() {
        calcStats("Hit Ratios", hitRatios);
        calcStats("Miss Rates", missRates);
    }

    private static void calcStats(String measure, List<Double> data) {
        double sum = 0.0;
        double squareSum = 0.0;
        int n = data.size();

        for (Double value : data) {
            sum += value;
            squareSum += Math.pow(value, 2);
        }

        Double sampleVariance = (squareSum - (Math.pow(sum, 2) / n)) / (n - 1);
        Double S = Math.sqrt(sampleVariance);
        Double Z = StudentstTable.getPercentile(n - 1, ALPHA);

        Double mean = (sum / n);
        Double halfInterval = Z * S / Math.sqrt(n);

        System.out.println(measure + " Mean = " + mean + ", CI = (" + (mean - halfInterval) + "," +  (mean + halfInterval) + ")");
    }
}
