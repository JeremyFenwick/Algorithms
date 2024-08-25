import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int size;
    private final WeightedQuickUnionUF data;
    private final boolean[] opened;
    private int opened_count;
    private final int virtualStart;
    private final int virtualEnd;

    public Percolation(int n) {
        size = n;
        data = new WeightedQuickUnionUF((n * n) + 2);
        opened = new boolean[n * n];
        virtualStart = (n * n) ;
        virtualEnd = (n * n) + 1;
        opened_count = 0;
        for (var i = 1; i <= size; i++) {
            var bottomIndex = coordinatesToInteger(1, i);
            var topIndex = coordinatesToInteger(size, i);
            data.union(virtualStart, bottomIndex);
            data.union(virtualEnd, topIndex);
        }
    }

    private int coordinatesToInteger(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IndexOutOfBoundsException("Invalid coordinate entry - out of bounds");
        }
        return ((row - 1) * size) + (col - 1);
    }

    private boolean inBounds(int row, int col) {
        return row >= 1 && row <= size && col >= 1 && col <= size;
    }

    public void open(int row, int col) {
        var index = coordinatesToInteger(row, col);
        opened[index] = true;
        opened_count++;
        // If the site above is open, perform a union
        if (inBounds(row - 1, col) && isOpen(row - 1, col)) {
            var siteIndex = coordinatesToInteger(row - 1, col);
            data.union(index, siteIndex);
        }
        // Now the site below
        if (inBounds(row + 1, col) && isOpen(row + 1, col)) {
            var siteIndex = coordinatesToInteger(row + 1, col);
            data.union(index, siteIndex);
        }
        // Now the site to the left
        if (inBounds(row, col - 1) && isOpen(row, col - 1)) {
            var siteIndex = coordinatesToInteger(row, col - 1);
            data.union(index, siteIndex);
        }
        // Now the site to the right
        if (inBounds(row, col + 1) && isOpen(row, col + 1)) {
            var siteIndex = coordinatesToInteger(row, col + 1);
            data.union(index, siteIndex);
        }
    }

    public boolean isOpen(int row, int col) {
        var index = coordinatesToInteger(row, col);
        return opened[index];
    }

    public boolean isFull(int row, int col) {
        var index = coordinatesToInteger(row, col);
        var firstRoot = data.find(virtualStart);
        var secondRoot = data.find(index);
        return firstRoot == secondRoot;
    }

    public int numberOfOpenSites() {
        return opened_count;
    }

    public boolean percolates() {
        var firstRoot = data.find(virtualStart);
        var secondRoot = data.find(virtualEnd);
        return firstRoot == secondRoot;
    }

    public static void main(String[] args) {
    }
}
