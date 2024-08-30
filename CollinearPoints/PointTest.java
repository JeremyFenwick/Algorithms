import org.junit.jupiter.api.Test;
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
}