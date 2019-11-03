import caches.Cache;
import caches.FIFOCache;
import distributions.Discrete;
import distributions.Exp;
import measurements.Measurement;

public class Simulation {
    private static Double TIME = 0.0;

    private static final Integer N = 3;
    private static final Integer M = 2;
    private static final Double WARMUP = 400.0;
    private static final Double RUNTIME = 1000.0;
    private static final Integer REPLICATION = 1000;

    private static Cache cache;

    public static void main(String[] args) {
        double combinedRate = calcRate();

        Double[] probabilities = calcProbabilities(combinedRate);

        Discrete discrete = new Discrete(probabilities);

        for (int i = 0; i < REPLICATION; i++) {
            TIME = 0.0;

            cache = new FIFOCache(M);

            warmup(combinedRate, discrete);

            Measurement.start();

            run(combinedRate, discrete);

            Measurement.finish(TIME);
        }

        Measurement.stats();
    }

    private static double calcRate() {
        double combinedRate = 0;

        for (double i = 1; i <= N; i++) {
            combinedRate += 1 / i;
        }
        return combinedRate;
    }

    private static Double[] calcProbabilities(double combinedRate) {
        Double[] probabilities = new Double[N];
        for (double i = 0; i < N; i++) {
            probabilities[(int) i] = (1 / ((i + 1) * combinedRate));
        }
        return probabilities;
    }

    private static void run(double combinedRate, Discrete discrete) {
        while (TIME < WARMUP + RUNTIME) {
            double interArrivalTime = Exp.rand(combinedRate);
            TIME += interArrivalTime;

            generateArrival(discrete);
        }
    }

    private static void warmup(double combinedRate, Discrete discrete) {
        while (TIME < WARMUP) {
            double interArrivalTime = Exp.rand(combinedRate);
            TIME += interArrivalTime;

            generateArrival(discrete);
        }
    }

    private static void generateArrival(Discrete discrete) {
        Integer item;

        item = discrete.next();

        cache.query(item);
    }
}
