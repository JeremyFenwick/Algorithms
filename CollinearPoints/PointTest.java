import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    @Test
    void createComparePoint() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 2);
        var result = point1.compareTo(point2);
        assertTrue(result < 0);
    }

    @Test
    void slopeTest() {
        var origin = new Point(0 ,0);
        var point2 = new Point(2, 2);
        var slope = origin.slopeTo(point2);
        assertEquals(1, slope);

        var vertical = new Point(0, 5);
        var vertSlope = origin.slopeTo(vertical);
        assertEquals(vertSlope, Double.POSITIVE_INFINITY);

        var horizontal = new Point(5, 0);
        var horSlope = origin.slopeTo(horizontal);
        assertEquals(horSlope, 0);
    }

    @Test
    void sortTest() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 2);
        var point3 = new Point(3, 3);
        var array = new Point[3];
        array[0] = point3;
        array[1] = point2;
        array[2] = point1;
        Arrays.sort(array);
        assertEquals(3, array.length);
    }

    @Test
    void alternateSortTest() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 3);
        var point3 = new Point(0, 0);
        var point4 = new Point(3, 4);
        var array = new Point[4];
        array[3] = point4;
        array[0] = point3;
        array[1] = point2;
        array[2] = point1;
        Arrays.sort(array, point3.slopeOrder());
        assertEquals(4, array.length);
    }

    @Test
    void bruteSearchTest() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 2);
        var point3 = new Point(3, 3);
        var point4 = new Point(4, 4);
        var array = new Point[4];
        array[0] = point1;
        array[1] = point2;
        array[2] = point3;
        array[3] = point4;
        var search = new BruteCollinearPoints(array);
        assertEquals(1, search.numberOfSegments());
    }

    @Test
    void bruteSearchReversedTest() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 2);
        var point3 = new Point(3, 3);
        var point4 = new Point(4, 4);
        var array = new Point[4];
        array[0] = point4;
        array[1] = point3;
        array[2] = point2;
        array[3] = point1;
        var search = new BruteCollinearPoints(array);
        assertEquals(1, search.numberOfSegments());
    }

    @Test
    void fastSearchTest() {
        var point1 = new Point(1, 1);
        var point2 = new Point(2, 2);
        var point3 = new Point(3, 3);
        var point4 = new Point(4, 4);
        var array = new Point[4];
        array[0] = point1;
        array[1] = point2;
        array[2] = point3;
        array[3] = point4;
        var search = new FastCollinearPoints(array);
        assertEquals(1, search.numberOfSegments());
    }
}
