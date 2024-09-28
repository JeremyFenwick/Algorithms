import edu.princeton.cs.algs4.Digraph;
import java.util.ArrayDeque;

public class DoubleBFS {
    public int resultDistance, resultVertex;
    private Digraph graph;

    public DoubleBFS(Digraph graph) {
        this.graph = graph;
    }

    private class BfsData {
        public int[] distance;
        public boolean[] visited;
        public ArrayDeque<Integer> queue;

        public BfsData(Digraph graph, int startingVertex) {
            distance = new int[graph.V()];
            visited = new boolean[graph.V()];
            distance[startingVertex] = 0;
            queue = new ArrayDeque<Integer>();
            queue.addFirst(startingVertex);
        }

        public BfsData(Digraph graph, Iterable<Integer> startingVertex) {
            distance = new int[graph.V()];
            visited = new boolean[graph.V()];
            queue = new ArrayDeque<Integer>();
            for (var vertex : startingVertex) {
                if (vertex == null) {
                    throw new IllegalArgumentException();
                }
                distance[vertex] = 0;
                queue.add(vertex);
            }
        }
    }

    public void lockstepBfs(int first, int second) {
        resultDistance = -1;
        resultVertex = -1;
        var firstBfs = new BfsData(graph, first);
        var secondBfs = new BfsData(graph, second);

        while (!firstBfs.queue.isEmpty() || !secondBfs.queue.isEmpty()) {
            if (runStep(firstBfs, secondBfs)) {
                return;
            };
            if (runStep(secondBfs, firstBfs)) {
                return;
            };
        }
    }

    public void lockstepBfs(Iterable<Integer> first, Iterable<Integer> second) {
        resultDistance = -1;
        resultVertex = -1;
        var firstBfs = new BfsData(graph, first);
        var secondBfs = new BfsData(graph, second);

        while (!firstBfs.queue.isEmpty() && !secondBfs.queue.isEmpty()) {
            if (runStep(firstBfs, secondBfs)) {
                return;
            };
            if (runStep(secondBfs, firstBfs)) {
                return;
            };
        }
    }

    private boolean runStep(BfsData thisData, BfsData thatData) {
        var vertex = bfsStep(thisData, thatData);
        if (vertex >= 0) {
            resultDistance = thisData.distance[vertex] + thatData.distance[vertex];
            resultVertex = vertex;
            return true;
        }
        return false;
    }

    private int bfsStep(BfsData thisData, BfsData thatData) {
        if (thisData.queue.isEmpty()) {
            return -1;
        }
        // If we have already visited this vertex, return
        int vertex = thisData.queue.removeFirst();
        if (thisData.visited[vertex]) {
            return -1;
        }
        else {
            thisData.visited[vertex] = true;
        }
        // Check if we have a candidate ancestor, if so return it
        if (thatData.visited[vertex]) {
            return vertex;
        }
        // Else add all neighbours to the queue and set the distances
        else {
            for (var neighbour : graph.adj(vertex)) {
                thisData.distance[neighbour] = thisData.distance[vertex] + 1;
                thisData.queue.addLast(neighbour);
            }
            return -1;
        }
    }
}

