import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;
import java.util.TreeSet;

public class PointSET {
    private final TreeSet<Point2D> data;

    public PointSET() {
        data = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int size() {
        return data.size();
    }

    public void insert(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        data.add(point);
    }

    public boolean contains(Point2D point) {
        if (point == null) {
            throw new IllegalArgumentException();
        }
        return data.contains(point);
    }

    public void draw() {
        for (var point : data) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rectangle) {
        if (rectangle == null) {
            throw new IllegalArgumentException();
        }
        var array = new ArrayList<Point2D>();
        for (var point : data) {
            if (rectangle.contains(point)) {
                array.add(point);
            }
        }
        return array;
    }

    public Point2D nearest(Point2D target) {
        if (target == null) {
            throw new IllegalArgumentException();
        }
        Point2D candidate = null;
        var candidateDistance = Double.POSITIVE_INFINITY;
        for (var point : data) {
            var newDistance = target.distanceTo(point);
            if (newDistance < candidateDistance) {
                candidate = point;
                candidateDistance = newDistance;
            }
        }
        return candidate;
    }
}
