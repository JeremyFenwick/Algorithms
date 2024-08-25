import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE95 = 1.96;

    private final double mean;
    private final double stddev;
    private final double count;

    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Arguments must be greater than 1!");
        }
        count = trials;
        double[] data = new double[trials];
        for (var i = 0; i < trials; i++) {
            var result = monteCarloSim(n);
            data[i] = result;
        }
        mean = StdStats.mean(data);
        stddev = StdStats.stddev(data);
    }

    private double monteCarloSim(int n) {
        var percolation = new Percolation(n);
        while (!percolation.percolates()) {
            var randomRow = StdRandom.uniformInt(1, n + 1);
            var randomCol = StdRandom.uniformInt(1, n + 1);
            percolation.open(randomRow, randomCol);
        }
        var numberOfOpenings = (double) percolation.numberOfOpenSites();
        return numberOfOpenings / (n * n);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - ((CONFIDENCE95 * stddev) / Math.sqrt(count));
    }

    public double confidenceHi() {
        return mean + ((CONFIDENCE95 * stddev) / Math.sqrt(count));
    }

    public static void main(String[] args) {
        var n = Integer.parseInt(args[0]);
        var trials = Integer.parseInt(args[1]);
        var stats = new PercolationStats(n, trials);
        System.out.printf("Mean                      = %f \n", stats.mean());
        System.out.printf("stddev                    = %f \n", stats.stddev());
        System.out.printf("95p confidence interval   = [%f, %f]", stats.confidenceHi(), stats.confidenceHi());
    }
}
