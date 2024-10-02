public class Coordinate {
    public final int index, row, column;
    private final int width, height;

    // Coordinate by column and row
    public Coordinate(int col, int row, int width, int height) {
        this.row = row;
        this.column = col;
        this.index = width * row + col;
        this.width = width;
        this.height = height;
    }

    // Coordinate by index
    public Coordinate(int index, int width, int height) {
        row = Math.floorDiv(index, width);
        column = index % width;
        this.index = index;
        this.width = width;
        this.height = height;
    }

    public Coordinate transpose() {
        return new Coordinate(row, column, height, width);
    }

    public boolean isInBounds() {
        return row >= 0 && column >= 0 && row < height && column < width;
    }
}
