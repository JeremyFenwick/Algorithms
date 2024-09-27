import edu.princeton.cs.algs4.Digraph;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SAPTests {

    Digraph generateSimpleGraph() {
        var graph = new Digraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        return graph;
    }

    Digraph generateExampleGraph() {
        var graph = new Digraph(25);
        graph.addEdge(13, 7);
        graph.addEdge(14, 7);
        graph.addEdge(7, 3);
        graph.addEdge(8, 3);
        graph.addEdge(9, 3);
        graph.addEdge(21, 16);
        graph.addEdge(22, 16);
        graph.addEdge(16, 9);
        graph.addEdge(15, 9);
        graph.addEdge(3, 1);
        graph.addEdge(4, 1);
        graph.addEdge(1, 0);
        return graph;
    }

    @Test
    void simpleGraphTest() {
        var graph = generateSimpleGraph();
        System.out.print(graph.toString());
    }

    @Test
    void exampleGraphTest() {
        var graph = generateExampleGraph();
        System.out.print(graph.toString());
    }


    @Test
    void simpleBfsTest() {
        var graph = generateExampleGraph();
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(13, 16);
        assertEquals(3, bfs.resultVertex);
        assertEquals(4, bfs.resultDistance);
    }

    @Test
    void bfsIterableTest() {
        var graph = generateExampleGraph();
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(List.of(13), List.of(21, 9));
        assertEquals(3, bfs.resultVertex);
        assertEquals(3, bfs.resultDistance);
    }
}
