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
        nullCheck(points);
        var sortedData = points.clone();
        Arrays.sort(sortedData);
        resultData = new ArrayList<LineSegment>();
        BeginSearchRoutine(sortedData);
    }

    private void nullCheck(Point[] data) {
        for (Point datum : data) {
            if (datum == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void BeginSearchRoutine(Point[] searchData) {
        if (searchData.length <= 3) {
            for (int i = 0; i < searchData.length; i++) {

                if (i > 0 && searchData[i].compareTo(searchData[i - 1]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
            return;
        }

        for (int i = 0; i < searchData.length; i++) {

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
            if (currentSlope != priorSlope && candidates.size() <= 3) {
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
            else if (i == searchData.length - 1 && candidates.size() >= 3 && currentSlope == priorSlope) {
                candidates.add(searchData[i]);
                AddSegment(origin, candidates);
                break;
            }
            else {
                candidates.add(searchData[i]);
            }
        }
    }

    private void AddSegment(Point origin, List<Point> candidates) {
        // The origin must be the smaller than all candidates else we have a duplication issue
        for (var candidate : candidates) {
            if (origin.compareTo(candidate) > 0) {
                return;
            }
        }
        var firstPoint = candidates.get(0);
        var lastPoint = candidates.get(candidates.size() - 1);
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
