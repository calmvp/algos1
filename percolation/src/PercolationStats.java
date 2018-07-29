import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double CI = 1.96;
    private double[] openPercentages;
    private int mcSimSize;
    private int trials;
    private Percolation percolation;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || n <= trials) {
            throw new java.lang.IllegalArgumentException("Submitted number and trials must both be greater than 0.");
        }
        this.mcSimSize = n;
        this.trials = trials;
        this.openPercentages = new double[trials];
        this.runMonteCarlo();
    }

    public double mean() {
        return StdStats.mean(this.openPercentages);
    }

    public double stddev() {
        return StdStats.stddev(this.openPercentages);
    }

    public double confidenceLo() {
        double mean = this.mean();
        double std = this.stddev();
        return (this.mean() - ((this.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    public double confidenceHi() {
        double mean = this.mean();
        double std = this.stddev();
        return (this.mean() + ((this.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    private void runMonteCarlo() {
        for (int i = 0; i < this.trials; i++) {
            this.percolation = new Percolation(this.mcSimSize);
            this.openPercentages[i] =  ((double) percolation.numberOfOpenSites()) / (this.mcSimSize * mcSimSize);
        }
    }

    public static void main(String[] args) {

    }

}
