import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoordinateTests {
    @Test
    void firstConstructor() {
        var coordinate1 = new Coordinate(2, 3, 3, 4);
        var coordinate2 = new Coordinate(10, 3, 4);
        assertEquals(coordinate1.index, 11);
        assertEquals(coordinate2.column, 1);
        assertEquals(coordinate2.row, 3);
    }
}