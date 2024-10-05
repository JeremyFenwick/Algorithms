import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver {
    private Picture picture;

    public SeamCarver(Picture image) {
        if (image == null) {
            throw new IllegalArgumentException();
        }
        loadPicture(image);
    }

    public Picture picture() {
        return new Picture(picture);
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public int[] findVerticalSeam() {
        var energies = loadEnergies();
        var memo = memoBuilder(energies, width(), height());
        var seam = seamFinder(memo, width(), height());
        var result = new int[seam.length];
        for (int i = 0; i < seam.length; i++) {
            result[i] = seam[i].column;
        }
        return result;
    }

    public int[] findHorizontalSeam() {
        var tEnergies = loadTransposedEnergies();
        var memo = memoBuilder(tEnergies, height(), width());
        var tSeam = seamFinder(memo, height(), width());
        var seam = transposeSeam(tSeam, height(), width());
        var result = new int[seam.length];
        for (int i = 0; i < seam.length; i++) {
            result[i] = seam[i].row;
        }
        return result;
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam == null || seam.length != height() || width() <= 1) {
            throw new IllegalArgumentException();
        }
        validateSeam(seam);
        var newPicture = new Picture(width() - 1, height());
        var nextColumn = 0;
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                var location = new Coordinate(col, row, width(), height());
                var pixel = picture.get(col, row);
                if (seam[row] != location.column) {
                    newPicture.set(nextColumn, row, pixel);
                    nextColumn++;
                }
            }
            // Reset the column index for the next row
            nextColumn = 0;
        }
        loadPicture(newPicture);
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam == null || seam.length != width()  || height() <= 1) {
            throw new IllegalArgumentException();
        }
        validateSeam(seam);
        var newPicture = new Picture(width(), height() - 1);
        var nextRow = 0;
        for (int col = 0; col < width(); col++) {
            for (int row = 0; row < height(); row++) {
                var location = new Coordinate(col, row, width(), height());
                var pixel = picture.get(col, row);
                if (seam[col] != location.row) {
                    newPicture.set(col, nextRow, pixel);
                    nextRow++;
                }
            }
            // Reset the row index for the next column
            nextRow = 0;
        }
        loadPicture(newPicture);
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

    private void loadPicture(Picture image) {
        this.picture = new Picture(image);
    }

    private double gradient(Color first, Color second) {
        var redSum = second.getRed() - first.getRed();
        var greenSum = second.getGreen() - first.getGreen();
        var blueSum = second.getBlue() - first.getBlue();
        return Math.pow(redSum, 2) + Math.pow(greenSum, 2) + Math.pow(blueSum, 2);
    }

    private double[] loadEnergies() {
        var energies = new double[width() * height()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                var location = new Coordinate(col, row, width(), height());
                energies[location.index] = energy(col, row);
            }
        }
        return energies;
    }

    private double[] loadTransposedEnergies() {
        var tEnergies = new double[width() * height()];
        for (int row = 0; row < height(); row++) {
            for (int col = 0; col < width(); col++) {
                var location = new Coordinate(col, row, width(), height()).transpose();
                tEnergies[location.index] = energy(col, row);
            }
        }
        return tEnergies;
    }

    private double minBelow(int col, int row, double[] memo, int width, int height) {
        var result = Double.POSITIVE_INFINITY;
        var bottomLeft = new Coordinate(col - 1, row + 1, width, height);
        var bottom = new Coordinate(col, row + 1, width, height);
        var bottomRight = new Coordinate(col + 1, row + 1, width, height);

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
                var minEnergyBelow = minBelow(col, row, memo, width, height);
                memo[location.index] = seamEnergies[location.index] + minEnergyBelow;
            }
        }
        return memo;
    }

    private Coordinate[] seamFinder(double[] memo, int width, int height) {
        var seam = new Coordinate[height];
        seam[0] = findLowestValueTopIndex(memo, width, height);
        for (int row = 1; row < height; row++) {
            var column = new Coordinate(seam[row - 1].column, width, height).column;
            var bottomLeft = new Coordinate(column - 1, row, width, height);
            var bottom = new Coordinate(column, row, width, height);
            var bottomRight = new Coordinate(column + 1, row, width, height);

            if (bottomLeft.isInBounds() && (seam[row] == null || memo[bottomLeft.index] < memo[seam[row].index])) {
                seam[row] = bottomLeft;
            }
            if (bottom.isInBounds() && (seam[row] == null || memo[bottom.index] < memo[seam[row].index])) {
                seam[row] = bottom;
            }
            if (bottomRight.isInBounds() && (seam[row] == null || memo[bottomRight.index] < memo[seam[row].index])) {
                seam[row] = bottomRight;
            }
        }
        return seam;
    }

    private Coordinate findLowestValueTopIndex(double[] memo, int width, int height) {
        Coordinate candidate = null;
        for (int col = 0; col < width; col++) {
            var location = new Coordinate(col, 0, width, height);
            if (candidate == null || memo[location.index] < memo[candidate.index]) {
                candidate = location;
            }
        }
        return candidate;
    }

    private Coordinate[] transposeSeam(Coordinate[] seam, int width, int height) {
        var tSeam = new Coordinate[seam.length];
        for (int i = 0; i < seam.length; i++) {
            tSeam[i] = new Coordinate(seam[i].index, width, height).transpose();
        }
        return tSeam;
    }

    private void validateSeam(int[] seam) {
        var lastSeam = seam[0];
        for (var integer : seam) {
            if (integer - lastSeam < -1 || integer - lastSeam > 1) {
                throw new IllegalArgumentException();
            }
            lastSeam = integer;
        }
    }
}
