import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] resultData;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        resultData = null;
        var pointsClone = points.clone();
        Arrays.sort(pointsClone);
        validator(pointsClone);
        bruteForce(pointsClone);
    }

    private void bruteForce(Point[] points) {
        var tempStorage = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        var first = points[i];
                        var second = points[j];
                        var third = points[k];
                        var fourth = points[m];
                        var firstSlope = first.slopeTo(second);
                        var secondSlope = first.slopeTo(third);
                        var thirdSlope = first.slopeTo(fourth);
                        if (firstSlope == secondSlope && firstSlope == thirdSlope) {
                            var newSegment = new LineSegment(first, fourth);
                            tempStorage.add(newSegment);
                        }
                    }
                }
            }
        }
        resultData = new LineSegment[tempStorage.size()];
        for (int i = 0; i < tempStorage.size(); i++) {
            resultData[i] = tempStorage.get(i);
        }
    }

    private void validator(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            // Short circuit to protect invalid indexing
            if (i > 0 && points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public LineSegment[] segments() {
        return resultData.clone();
    }

    public int numberOfSegments() {
        return resultData.length;
    }
}
