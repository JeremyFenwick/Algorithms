import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class Board {
    private final int[] data;
    private int blankIndex, hammingDistance, manhattanDistance;
    private final int size;
    private final List<Board> neighbours;

    public Board(int[][] tiles) {
        size = tiles.length;
        data = new int[size * size];
        hammingDistance = 0;
        neighbours = new ArrayList<Board>();
        initialize(tiles);
    }

    private void initialize(int[][] tiles) {
        var number = 1;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                var index = map(row, col);
                var value = tiles[row][col];
                data[index] = value;
                if (value != 0) {
                    computeHammingDistance(index, value, number);
                    computeManhattanDistance(row, col, value - 1);
                }
                else {
                    blankIndex = index;
                }
                number++;
            }
        }
    }

    private void computeHammingDistance(int index, int value, int expectedValue) {
        var emptySlot = index == (size * size - 1);
        // Compute the hamming distance
        if (!emptySlot && value != expectedValue) {
            hammingDistance++;
        }
        else if (emptySlot) {
            hammingDistance++;
        }
    }

    private void computeManhattanDistance(int row, int col, int value) {
        var expectedRow = value / size;
        var expectedCol = value % size;
        var rowDelta = Math.abs(row - expectedRow);
        var colDelta = Math.abs(col - expectedCol);
        var total = rowDelta + colDelta;

        manhattanDistance += total;
    }

    // Turns 2d coordinates into a 1d index. (0, 1) -> 1
    private int map(int row, int col) {
        return ((row) * size) + (col);
    }

    private void generateNeighbours(int[] baseBoard) {
        var blankRow = blankIndex / size;
        var blankCol = blankIndex % size;
        // top
        if (blankRow - 1 >= 0) {
            neighbours.add(cloneBoardWithSwap(blankRow, blankCol, blankRow - 1, blankCol));
        }
        // bottom
        if (blankRow + 1 < size) {
            neighbours.add(cloneBoardWithSwap(blankRow, blankCol, blankRow + 1, blankCol));
        }
        // left
        if (blankCol - 1 >= 0) {
            neighbours.add(cloneBoardWithSwap(blankRow, blankCol, blankRow, blankCol - 1));
        }
        // right
        if (blankCol + 1 < size) {
            neighbours.add(cloneBoardWithSwap(blankRow, blankCol, blankRow, blankCol + 1));
        }
    }

    private Board cloneBoardWithSwap(int firstRow, int firstCol, int secondRow, int secondCol) {
        // Make a data copy
        var newData = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                var index = map(row, col);
                newData[row][col] = data[index];
            }
        }

        var temp = newData[firstRow][firstCol];
        newData[firstRow][firstCol] = newData[secondRow][secondCol];
        newData[secondRow][secondCol] = temp;

        return new Board(newData);
    }

    public int dimension() {
        return size;
    }

    public boolean equals(Object otherBoard) {
        if (this == otherBoard) {
            return true;
        }
        if (otherBoard == null) {
            return false;
        }
        if (otherBoard.getClass() != this.getClass()) {
            return false;
        }
        var compareBoard = (Board) otherBoard;
        if (size != compareBoard.size) {
            return false;
        }
        var otherBoardString = compareBoard.toString();
        return toString().equals(otherBoardString);
    }

    public String toString() {
        var resultString = new StringBuilder();
        resultString.append(size);
        resultString.append("\n");

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                var index = map(row, col);
                var value = data[index];
                resultString.append(" ");
                resultString.append(value);
                resultString.append(" ");
            }
            resultString.append("\n");
        }

        return resultString.toString();
    }

    public boolean isGoal() {
        return hammingDistance == 0;
    }

    public int hamming() {
        return hammingDistance;
    }

    public int manhattan() {
        return manhattanDistance;
    }

    public Board twin() {
        // now swap two values depending on the blankIndex location
        var blankRow = blankIndex / size;
        if (blankRow == 0) {
            return cloneBoardWithSwap(1, 0, 1, 1);
        }
        else {
            return cloneBoardWithSwap(0, 0, 0, 1);
        }
    }

    public Iterable<Board> neighbors() {
        generateNeighbours(data);
        return neighbours;
    }
}
