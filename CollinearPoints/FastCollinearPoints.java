import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private Point[] sortedData;
    private Point[] rawData;
    private List<LineSegment> resultData;
    private List<Point> lastFour;

    public FastCollinearPoints (Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        sortedData = points;
        resultData = new ArrayList<LineSegment>();
        lastFour = new ArrayList<Point>();
        rawData = sortedData.clone();
        Arrays.sort(sortedData);
        BeginSearchRoutine();
    }

    private void BeginSearchRoutine() {
        for (int i = 0; i < sortedData.length; i++) {
            if (sortedData[i] == null) {
                throw new IllegalArgumentException();
            }
            if (i > 0 && sortedData[i] == sortedData[i - 1]) {
                throw new IllegalArgumentException();
            }
            PointSearch(sortedData[i]);
        }
    }

    private void PointSearch(Point origin) {
        Arrays.sort(rawData, origin.slopeOrder());
        for (int i = 0; i <= rawData.length - 3; i++) {
            var currentSlope = origin.slopeTo(rawData[i]);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
                continue;
            }
            var nextSlope = origin.slopeTo(rawData[i + 1]);
            var nextNextSlope = origin.slopeTo(rawData[i+2]);

            var current = rawData[i];
            var next = rawData[i+1];
            var nextNext = rawData[i+2];

            if (lastFour.contains(origin) || lastFour.contains(current)) {
                continue;
            }
            if (lastFour.contains(next) || lastFour.contains(nextNext)) {
                continue;
            }

            if (currentSlope == nextSlope && currentSlope == nextNextSlope) {
                resultData.add(new LineSegment(origin, rawData[i + 2]));
                lastFour.clear();
                lastFour.add(origin);
                lastFour.add(rawData[i]);
                lastFour.add(rawData[i+1]);
                lastFour.add(rawData[i+2]);
                i += 2;
            }
        }
    }

    public LineSegment[] segments() {
        var result = new LineSegment[resultData.size()];
        for (int i = 0; i < resultData.size(); i++) {
            result[i] = resultData.get(i);
        }
        return result;
    }

    public int numberOfSegments(){
        return resultData.size();
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
