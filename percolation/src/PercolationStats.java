import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CI = 1.96;
    private double[] openPercentages;
    private final int mcSimSize;
    private final int trials;

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
        return (this.mean() - ((PercolationStats.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    public double confidenceHi() {
        return (this.mean() + ((PercolationStats.CI * this.stddev())/(Math.sqrt(this.trials))));
    }

    private void runSimulations() {
        for (int i = 0; i < this.trials; i++) {
            Percolation percolation = new Percolation(this.mcSimSize);
            this.openPercentages[i] =  ((double) percolation.numberOfOpenSites()) / (this.mcSimSize * mcSimSize);
        }
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(size, trials);
        System.out.println("Mean = " + percolationStats.mean());
        System.out.println("Stddev = " + percolationStats.stddev());
        System.out.println("95% Confidence Interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
    }
}
