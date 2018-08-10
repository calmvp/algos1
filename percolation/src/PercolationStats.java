import edu.princeton.cs.algs4.StdStats;

import static java.lang.Integer.parseInt;

public class PercolationStats {
    private final double CI = 1.96;
    private double[] openPercentages;
    private int mcSimSize;
    private int trials;
    private Percolation percolation;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("Submitted number and trials must both be greater than 0.");
        }
        this.mcSimSize = n;
        this.trials = trials;
        this.openPercentages = new double[trials];
        this.runSimulations();
    }

    public double mean() {
        return StdStats.mean(this.openPercentages);
    }

    public double stddev() {
        return StdStats.stddev(this.openPercentages);
    }

    public double confidenceLo() {
        return (this.mean() - ((this.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    public double confidenceHi() {
        return (this.mean() + ((this.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    private void runSimulations() {
        for (int i = 0; i < this.trials; i++) {
            this.percolation = new Percolation(this.mcSimSize);
            this.openPercentages[i] =  ((double) percolation.numberOfOpenSites()) / (this.mcSimSize * mcSimSize);
        }
    }

    public static void main(String[] args) {
        int size = parseInt(args[0]);
        int trials = parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(size, trials);
        System.out.println("Mean = " + String.valueOf(percolationStats.mean()));
        System.out.println("Stddev = " + String.valueOf(percolationStats.stddev()));
        System.out.println("95% Confidence Interval = [" + String.valueOf(percolationStats.confidenceLo() + ", " + String.valueOf(percolationStats.confidenceHi()) + "]"));
    }
}
