public class Simulation {
    public static Double TIME = 0.0;

    private static final Integer N = 1000;
    private static final Integer M = 100;
    private static final Cache cache = new FIFOCache(M);
    private static final Double RUNTIME = 1000.0;


    public static void main(String[] args) {
        System.out.println("Running simulation on cache strategy " + cache.toString() + " with memory size " + N);

        double combinedRate = 0;
        Double[] probabilities = new Double[N];

        for (double i = 1; i <= N; i++) {
            combinedRate += 1 / i;
        }

        for (double i = 0; i < N; i++) {
            probabilities[(int) i] = (1 / ((i + 1) * combinedRate));
        }

        Discrete discrete = new Discrete(probabilities);

        Measurement.start();

        while (TIME < RUNTIME) {
            double interArrivalTime = Exp.rand(combinedRate);
            TIME += interArrivalTime;

            generateArrival(discrete);
        }

        Measurement.print();
        Measurement.csv();
    }

    private static void generateArrival(Discrete discrete) {
        Integer item;

        item = discrete.next();

        cache.query(item);
    }
}
