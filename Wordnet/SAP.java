import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
    private final Digraph graph;
    private int lastLength, lastAncestor, lastV, lastW, lastIterableLength, lastIterableAncestor;
    private Iterable<Integer> lastIterableV, lastIterableW;

    public SAP (Digraph graph) {
        if (graph == null) {
            throw new IllegalArgumentException();
        }
        this.graph = new Digraph(graph);
        lastV = -1;
        lastW = -1;
        lastIterableV = null;
        lastIterableW = null;
    }

    private void cache(int v, int w, DoubleBFS bfs) {
        lastV = v;
        lastW = w;
        lastLength = bfs.getResultDistance();
        lastAncestor = bfs.getResultVertex();
    }

    private void cache(Iterable<Integer> v, Iterable<Integer> w, DoubleBFS bfs) {
        lastIterableV = v;
        lastIterableW = w;
        lastIterableLength = bfs.getResultDistance();
        lastIterableAncestor = bfs.getResultVertex();
    }

    public int length(int v, int w) {
        checkArgument(v);
        checkArgument(w);
        if (v == lastV && w == lastW) {
            return lastLength;
        }
        var bfs = new DoubleBFS(graph);
        bfs.bfsSearch(v, w);
        cache(v, w, bfs);
        return bfs.getResultDistance();
    }

    public int ancestor(int v, int w) {
        checkArgument(v);
        checkArgument(w);
        if (v == lastV && w == lastW) {
            return lastAncestor;
        }
        var bfs = new DoubleBFS(graph);
        bfs.bfsSearch(v, w);
        cache(v, w, bfs);
        return bfs.getResultVertex();
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkArgument(v);
        checkArgument(w);
        if (v.equals(lastIterableV) && w.equals(lastIterableW)) {
            return lastIterableLength;
        }
        var bfs = new DoubleBFS(graph);
        bfs.bfsSearch(v, w);
        cache(v, w, bfs);
        return bfs.getResultDistance();
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkArgument(v);
        checkArgument(w);
        if (v.equals(lastIterableV) && w.equals(lastIterableW)) {
            return lastIterableAncestor;
        }
        var bfs = new DoubleBFS(graph);
        bfs.bfsSearch(v, w);
        cache(v, w, bfs);
        return bfs.getResultVertex();
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private void checkArgument(int argument) {
        if (argument < 0 || argument >= graph.V()) {
            throw new IllegalArgumentException();
        }
    }

    private void checkArgument(Iterable<Integer> arguments) {
        if (arguments == null) {
            throw new IllegalArgumentException();
        }
        for (var argument : arguments) {
            if (argument == null) {
                throw new IllegalArgumentException();
            }
            checkArgument(argument);
        }
    }
}
