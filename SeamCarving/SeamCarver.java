import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private double[] energies;
    private double[] tEnergies;

    public SeamCarver(Picture image) {
        picture = image;
        energies = new double[width() * height()];
        tEnergies = new double[width() * height()];
        loadEnergies();
        transposeEnergies();
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int col, int row) {
        if (col < 0 || row < 0 || col >= width() || row >= height()) {
            throw new IllegalArgumentException();
        }
        if (col == 0 || row == 0 || col == width() - 1 || row == height() - 1) {
            return 1000;
        }
        var top = picture.get(col, row - 1);
        var bottom = picture.get(col, row + 1);
        var left = picture.get(col - 1, row );
        var right = picture.get(col + 1, row);
        return Math.sqrt(gradient(top, bottom) + gradient(left, right));
    }

    private double gradient(Color first, Color second) {
        var redSum = second.getRed() - first.getRed();
        var greenSum = second.getGreen() - first.getGreen();
        var blueSum = second.getBlue() - first.getBlue();
        return Math.pow(redSum, 2) + Math.pow(greenSum, 2) + Math.pow(blueSum, 2);
    }

    private void loadEnergies() {
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                var location = new Coordinate(col, row, width(), height());
                energies[location.index] = energy(col, row);
            }
        }
    }

    private void transposeEnergies() {
        for (int i = 0; i < energies.length; i++) {
            var tLocation = new Coordinate(i, width(), height()).transpose();
            tEnergies[tLocation.index] = energies[i];
        }
    }

    private double minBelow(int col, int row, double[] memo) {
        var result = Double.POSITIVE_INFINITY;
        var bottomLeft = new Coordinate(col - 1, row + 1, width(), height());
        var bottom = new Coordinate(col, row + 1, width(), height());
        var bottomRight = new Coordinate(col + 1, row + 1, width(), height());

        if (bottomLeft.isInBounds() && memo[bottomLeft.index] < result) {
            result = memo[bottomLeft.index];
        }
        if (bottom.isInBounds() && memo[bottom.index] < result) {
            result = memo[bottom.index];
        }
        if (bottomRight.isInBounds() && memo[bottomRight.index] < result) {
            result = memo[bottomRight.index];
        }
        // If no result was lower than the dummy value, return 0
        return result != Double.POSITIVE_INFINITY ? result : 0;
    }

    private double[] memoBuilder(double[] seamEnergies, int width, int height) {
        var memo = new double[width * height];
        // Traverse the energies list, bottom to top and complete the memo
        for (int row = height - 1; row >= 0; row--) {
            for (int col = 0; col < width; col++) {
                var location = new Coordinate(col, row, width, height);
                var minEnergyBelow = minBelow(col, row, memo);
                memo[location.index] = seamEnergies[location.index] + minEnergyBelow;
            }
        }
        return memo;
    }

    private int[] seamFinder(double[] memo, int width, int height) {
        // Traverse through the memo, bottom to top and select the lowest energy path
        var seam = new int[height];
        for (int row = height - 1; row >= 0; row--) {
            // Give the seam array the first column of the row to start
            seam[row] = new Coordinate(0, row, width, height).index;
            for (int col = 1; col < width; col++) {
                var currentLocation = new Coordinate(col, row, width, height);
                var seamIndex = seam[row];
                if (memo[currentLocation.index] < memo[seamIndex]) {
                    seam[row] = currentLocation.index;
                }
            }
        }
        return seam;
    }

    public int[] findVerticalSeam() {
        var memo = memoBuilder(energies, width(), height());
        return seamFinder(memo, width(), height());
    }

    public int[] findHorizontalSeam() {
        var memo = memoBuilder(tEnergies, height(), width());
        return seamFinder(memo, width(), height());
    }
}
