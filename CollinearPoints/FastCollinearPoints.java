import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {
    private final List<LineSegment> resultData;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        var sortedData = points.clone();
        Arrays.sort(sortedData);
        resultData = new ArrayList<LineSegment>();
        if (sortedData.length > 3) {
            BeginSearchRoutine(sortedData);
        }
    }

    private void BeginSearchRoutine(Point[] searchData) {
        for (int i = 0; i < searchData.length; i++) {
            if (searchData[i] == null) {
                throw new IllegalArgumentException();
            }
            if (i > 0 && searchData[i].compareTo(searchData[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }

            var tempData = searchData.clone();
            Arrays.sort(tempData, searchData[i].slopeOrder());
            PointSearch(searchData[i], tempData);
        }
    }

    private void PointSearch(Point origin, Point[] searchData) {
        var candidates = new ArrayList<Point>();
        candidates.add(origin);
        var priorSlope = origin.slopeTo(searchData[1]);

        for (int i = 1; i < searchData.length; i++) {
            var currentSlope = origin.slopeTo(searchData[i]);

            // Candidate list is not long enough
            if (currentSlope != priorSlope && candidates.size() < 3) {
                priorSlope = currentSlope;
                candidates.clear();
                candidates.add(origin);
                candidates.add(searchData[i]);
            }
            // We have a list of candidates long enough
            else if (currentSlope != priorSlope && candidates.size() >= 4) {
                priorSlope = currentSlope;
                AddSegment(origin, candidates);
                candidates.clear();
                candidates.add(origin);
                candidates.add(searchData[i]);
            }
            // End of the array
            else if (i == searchData.length - 1 && candidates.size() >= 4) {
                AddSegment(origin, candidates);
                break;
            }
            else {
                candidates.add(searchData[i]);
            }
        }
    }

    private void AddSegment(Point origin, List<Point> candidates) {
        for (var candidate : candidates) {
            if (origin.compareTo(candidate) > 0) {
                return;
            }
        }
        var firstPoint = candidates.getFirst();
        var lastPoint = candidates.getLast();
        var newSegment = new LineSegment(firstPoint, lastPoint);
        resultData.add(newSegment);
    }

    public LineSegment[] segments() {
        var result = new LineSegment[resultData.size()];
        for (int i = 0; i < resultData.size(); i++) {
            result[i] = resultData.get(i);
        }
        return result;
    }

    public int numberOfSegments() {
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
