import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] resultData;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        validator(points);
        resultData = null;
        var pointsClone = points.clone();
        Arrays.sort(pointsClone);
        bruteForce(pointsClone);
    }

    private void bruteForce(Point[] points) {
        var tempStorage = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            var first = points[i];
            for (int j = i + 1; j < points.length; j++) {
                var second = points[j];
                var firstSlope = first.slopeTo(second);
                if (first.compareTo(second) == 0) {
                    throw new IllegalArgumentException();
                }
                for (int k = j + 1; k < points.length; k++) {
                    var third = points[k];
                    var secondSlope = first.slopeTo(third);
                    for (int m = k + 1; m < points.length; m++) {
                        var fourth = points[m];
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
        for (Point point : points) {
            if (point == null) {
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
