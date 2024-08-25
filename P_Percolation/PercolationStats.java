import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double size;

    public PercolationStats (int n, int trials) {
        size = (double)n;
        double[] data = new double[trials];
        for (var i = 0; i < trials; i++) {
            var result = monteCarloSim(n);
            data[i] = result;
        }
        mean = StdStats.mean(data);
        stddev = StdStats.mean(data);
    }

    private double monteCarloSim(int size) {
        var percolation = new Percolation(size);

        while (!percolation.percolates()) {
            var randomRow = StdRandom.uniformInt(1, size + 1);
            var randomCol = StdRandom.uniformInt(1, size + 1);
            percolation.open(randomRow, randomCol);
        }

        return percolation.numberOfOpenSites();
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return mean - ((1.96 * stddev) / Math.sqrt(size));
    }

    public double confidenceHi() {
        return mean + ((1.96 * stddev) / Math.sqrt(size));
    }

    public static void main(String[] args) {
    }
}
