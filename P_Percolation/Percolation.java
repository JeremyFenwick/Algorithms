import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size, virtualStart, virtualEnd;
    private final WeightedQuickUnionUF fullData;
    private final WeightedQuickUnionUF topData;
    private final boolean[] opened;
    private int openedCount;

    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("Size must be at least one!");
        }

        size = n;
        fullData = new WeightedQuickUnionUF((n * n) + 2);
        topData = new WeightedQuickUnionUF((n * n) + 1);
        opened = new boolean[n * n];
        virtualStart = (n * n);
        virtualEnd = (n * n) + 1;
        openedCount = 0;
    }

    private int coordinatesToInteger(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException("Invalid coordinate entry - out of bounds");
        }
        return ((row - 1) * size) + (col - 1);
    }

    private boolean inBounds(int row, int col) {
        return row >= 1 && row <= size && col >= 1 && col <= size;
    }

    private void loadData(int firstIndex, int secondIndex) {
        fullData.union(firstIndex, secondIndex);
        topData.union(firstIndex, secondIndex);
    }

    public void open(int row, int col) {
        var index = coordinatesToInteger(row, col);
        if (opened[index]) {
            return;
        }
        opened[index] = true;
        openedCount++;
        // If we are on the top row, connect to the virtual start
        if (row == 1) {
            loadData(index, virtualStart);
        }
        // If we are on the bottom row, connect to the virtual end
        if (row == size) {
            fullData.union(index, virtualEnd);
        }
        // If the site above is open, perform a union
        if (inBounds(row - 1, col) && isOpen(row - 1, col)) {
            var siteIndex = coordinatesToInteger(row - 1, col);
            loadData(index, siteIndex);
        }
        // Now the site below
        if (inBounds(row + 1, col) && isOpen(row + 1, col)) {
            var siteIndex = coordinatesToInteger(row + 1, col);
            loadData(index, siteIndex);
        }
        // Now the site to the left
        if (inBounds(row, col - 1) && isOpen(row, col - 1)) {
            var siteIndex = coordinatesToInteger(row, col - 1);
            loadData(index, siteIndex);
        }
        // Now the site to the right
        if (inBounds(row, col + 1) && isOpen(row, col + 1)) {
            var siteIndex = coordinatesToInteger(row, col + 1);
            loadData(index, siteIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        var index = coordinatesToInteger(row, col);
        return opened[index];
    }

    public boolean isFull(int row, int col) {
        var index = coordinatesToInteger(row, col);
        if (!opened[index]) {
            return false;
        }
        var firstRoot = topData.find(virtualStart);
        var secondRoot = topData.find(index);
        return firstRoot == secondRoot;
    }

    public int numberOfOpenSites() {
        return openedCount;
    }

    public boolean percolates() {
        var firstRoot = fullData.find(virtualStart);
        var secondRoot = fullData.find(virtualEnd);
        return firstRoot == secondRoot;
    }

    public static void main(String[] args) {
    }
}
