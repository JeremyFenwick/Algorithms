import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    public Point(int xPoint, int yPoint) {
        x = xPoint;
        y = yPoint;
    }

    public void draw() {
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        // check degenerate
        if (that.y == y && that.x == x) {
            return Double.NEGATIVE_INFINITY;
        }
        // check vertical
        if (that.x == x) {
            return Double.POSITIVE_INFINITY;
        }
        // check horizontal
        if (that.y == y) {
            return 0.0;
        }
        return (that.y - y) / (double) (that.x - x);
    }


    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (y == that.y) {
            return x - that.x;
        }
        else {
            return y - that.y;
        }
    }

    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> Double.compare(slopeTo(o1), slopeTo(o2));
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
    }
}
