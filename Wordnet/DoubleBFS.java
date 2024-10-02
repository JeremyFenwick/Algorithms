import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayDeque;

public class DoubleBFS {
    private int resultDistance, resultVertex;
    private final Digraph graph;

    public DoubleBFS(Digraph graph) {
        this.graph = graph;
    }

    private static class BfsData {
        public int[] distance;
        public boolean[] marked;
        public ArrayDeque<Integer> queue;

        public BfsData(Digraph graph, int startingVertex) {
            distance = new int[graph.V()];
            marked = new boolean[graph.V()];
            distance[startingVertex] = 0;
            marked[startingVertex] = true;
            queue = new ArrayDeque<Integer>();
            queue.addFirst(startingVertex);
        }

        public BfsData(Digraph graph, Iterable<Integer> vertices) {
            distance = new int[graph.V()];
            marked = new boolean[graph.V()];
            queue = new ArrayDeque<Integer>();
            for (var vertex : vertices) {
                if (vertex == null) {
                    throw new IllegalArgumentException();
                }
                distance[vertex] = 0;
                marked[vertex] = true;
                queue.add(vertex);
            }
        }
    }

    public int getResultDistance() {
        return resultDistance;
    }

    public int getResultVertex() {
        return resultVertex;
    }

    public void bfsSearch(int first, int second) {
        resultDistance = -1;
        resultVertex = -1;
        var firstBfs = new BfsData(graph, first);
        var secondBfs = new BfsData(graph, second);
        bfsRoutine(firstBfs);
        bfsRoutine(secondBfs);
        findAncestor(firstBfs, secondBfs);
    }

    public void bfsSearch(Iterable<Integer> first, Iterable<Integer> second) {
        resultDistance = -1;
        resultVertex = -1;
        var firstBfs = new BfsData(graph, first);
        var secondBfs = new BfsData(graph, second);
        bfsRoutine(firstBfs);
        bfsRoutine(secondBfs);
        findAncestor(firstBfs, secondBfs);
    }

    private void bfsRoutine(BfsData bfsData) {
        while (!bfsData.queue.isEmpty()) {
            int vertex = bfsData.queue.removeFirst();
            for (var neighbour : graph.adj(vertex)) {
                if (!bfsData.marked[neighbour]) {
                    bfsData.distance[neighbour] = bfsData.distance[vertex] + 1;
                    bfsData.queue.addLast(neighbour);
                    bfsData.marked[neighbour] = true;
                }
            }
        }
    }

    private void findAncestor(BfsData firstBfs, BfsData secondBfs) {
        for (var i = 0; i < firstBfs.distance.length; i++) {
            if (firstBfs.marked[i] && secondBfs.marked[i]) {
                var candidateDistance = firstBfs.distance[i] + secondBfs.distance[i];
                if (candidateDistance < resultDistance || resultDistance == -1) {
                    resultDistance = candidateDistance;
                    resultVertex = i;
                }
            }
        }
    }
}

