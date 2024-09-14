import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KdTreeTest {
    KdTree createBasicTree() {
        var tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.2));
        tree.insert(new Point2D(0.5, 0.4));
        tree.insert(new Point2D(0.2, 0.3));
        tree.insert(new Point2D(0.4, 0.7));
        tree.insert(new Point2D(0.9, 0.6));
        return tree;
    }

    KdTree createUnitTree() {
        var tree = new KdTree();
        tree.insert(new Point2D(.5, .5));
        return tree;
    }

    @Test
    void treeSize() {
        var tree = createBasicTree();
        tree.draw();
        assertEquals(tree.size(), 5);
    }

    @Test
    void treeDraw() {
        var tree = createUnitTree();
        tree.draw();
        assertEquals(tree.size(), 1);
    }

    @Test
    void containsTest() {
        var tree = createBasicTree();
        var result = tree.contains(new Point2D(0.2, 0.3));
        assertTrue(result);
    }

    @Test
    void secondContainsTest() {
        var tree = createBasicTree();
        var result = tree.contains(new Point2D(0.1, 0.3));
        assertFalse(result);
    }

    @Test
    void rangeTest() {
        var tree = createBasicTree();
        var rangeTree = new RectHV(0, 0, .5, 1);
        var result = tree.range(rangeTree);
        for (var point : result) {
            System.out.println(point);
        }
    }

    @Test
    void nearestTest() {
        var tree = createBasicTree();
        var result = tree.nearest(new Point2D(0.21, 0.3));
        assertEquals(result, new Point2D(0.2, 0.3));
    }
}