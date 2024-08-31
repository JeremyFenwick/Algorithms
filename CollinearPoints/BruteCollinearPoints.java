import javax.sound.sampled.Line;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private LineSegment[] resultData;
    private int resultNumber;

    public BruteCollinearPoints(Point[] points){
        if (points == null) {
            throw new IllegalArgumentException();
        }
        resultData = null;
        resultNumber = 0;
        Arrays.sort(points);
        Validator(points);
        BruteForce(points);
    }

    private void BruteForce(Point[] points) {
        var tempStorage = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        var first = points[i];
                        var second = points[j];
                        var third = points[k];
                        var fourth = points[l];
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
        resultNumber = tempStorage.size();
        resultData = new LineSegment[tempStorage.size()];
        for (int i = 0; i < tempStorage.size(); i++) {
            resultData[i] = tempStorage.get(i);
        }
    }

    private void Validator(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            {
                if (points[i] == null) {
                    throw new IllegalArgumentException();
                }
                // Short circuit to protect invalid indexing
                if (i > 0 && points[i] == points[i - 1]) {
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public LineSegment[] segments() {
        return resultData;
    }

    public int numberOfSegments(){
        return resultNumber;
    }
}
