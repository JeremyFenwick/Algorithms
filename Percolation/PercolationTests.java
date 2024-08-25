import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PercolationTest {
    @Test
    void percolationCreationTest() {
        var percolation = new Percolation(3);
        var count = percolation.numberOfOpenSites();
        assertEquals(0, count);
    }

    @Test
    void simplePercolationTest() {
        var percolation = new Percolation(3);
        percolation.open(1, 2);
        assertFalse(percolation.percolates());
        percolation.open(2, 2);
        assertFalse(percolation.percolates());
        percolation.open(3, 2);
        assertTrue(percolation.percolates());
        var count = percolation.numberOfOpenSites();
        assertEquals(3, count);
    }

    @Test
    void openTest() {
        var percolation = new Percolation(3);
        assertFalse(percolation.isFull(1, 1));
        percolation.open(1, 1);
        assertTrue(percolation.isOpen(1, 1));
    }

    @Test
    void singleton() {
        var percolation = new Percolation(1);
        assertFalse(percolation.percolates());
        percolation.open(1,1);
        assertTrue(percolation.percolates());
    }

    @Test
    void twofer() {
        var percolation = new Percolation(2);
        assertFalse(percolation.percolates());
    }

    @Test
    void monteCarlo() {
        var sim = new PercolationStats(2, 10000);
        assertTrue(sim.mean() > 0.5);
    }
}