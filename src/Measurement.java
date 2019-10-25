import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Measurement {
    private static Integer MISSES;
    private static Integer QUERIES;

    private static List<Pair<Double, Double>> hitRatios;
    private static List<Pair<Double, Double>> missRates;

    public static void start() {
        MISSES = 0;
        QUERIES = 0;

        hitRatios = new ArrayList<>();
        missRates = new ArrayList<>();
    }

    public static void hit() {
        QUERIES++;

        calcStats();
    }

    public static void miss() {
        QUERIES++;
        MISSES++;

        calcStats();
    }

    public static void print() {
        printHitRatios();
        printMissRates();
    }

    public static void csv() {
        writeToCSV(missRates, "missRates.csv");
        writeToCSV(hitRatios, "hitRatios.csv");
    }

    private static void calcStats() {
        hitRatios.add(new Pair<>(Simulation.TIME, (QUERIES - MISSES) / (double) QUERIES));
        missRates.add(new Pair<>(Simulation.TIME, (MISSES / Simulation.TIME)));
    }


    private static void printHitRatios() {
        System.out.println("Hit Ratios");
        System.out.println("==========");

        for (Pair<Double, Double> dataPoint : hitRatios) {
            System.out.println("(Time, HitRatio) -> (" + dataPoint.getKey() + ", " + dataPoint.getValue() + ")");
        }
    }

    private static void printMissRates() {
        System.out.println("Miss Rate");
        System.out.println("==========");

        for (Pair<Double, Double> dataPoint : missRates) {
            System.out.println("(Time, MissRate) -> (" + dataPoint.getKey() + ", " + dataPoint.getValue() + ")");
        }
    }

    private static void writeToCSV(List<Pair<Double, Double>> data, String filename) {
        File outputFile = new File(filename);

        try (PrintWriter pw = new PrintWriter(outputFile)) {
            for (Pair<Double, Double> dataPoint : data) {
                pw.println(dataPoint.getKey() + ", " + dataPoint.getValue());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
