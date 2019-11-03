package distributions;

public class Discrete {
    private final Double[] probabilities;

    public Discrete(Double[] probabilities) {
        this.probabilities = probabilities;
    }

    public Integer next() {
        double rand = Math.random();

        Double cumulativeProb = 0.0;
        for (Integer pos = 0; pos < probabilities.length; pos++) {
            cumulativeProb += probabilities[pos];
            if (rand <= cumulativeProb) {
                return pos;
            }
        }

        return -1;
    }
}
