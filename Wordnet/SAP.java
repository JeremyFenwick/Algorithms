import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private Digraph graph;
    private int lastLength, lastAncestor;
    private int lastIterableLength, lastIterableAncestor;
    private int lastV, lastW;
    private Iterable<Integer> lastIterableV, lastIterableW;

    public SAP (Digraph graph) {
        this.graph = new Digraph(graph);
        lastV = -1;
        lastW = -1;
        lastIterableV = null;
        lastIterableW = null;
    }

    public void cache(int v, int w, DoubleBFS bfs) {
        lastV = v;
        lastW = w;
        lastLength = bfs.resultDistance;
        lastAncestor = bfs.resultVertex;
    }

    public void cache(Iterable<Integer> v, Iterable<Integer> w, DoubleBFS bfs) {
        lastIterableV = v;
        lastIterableW = w;
        lastIterableLength = bfs.resultDistance;
        lastIterableAncestor = bfs.resultVertex;
    }

    public int length(int v, int w) {
        if (v == lastV && w == lastW) {
            return lastLength;
        }
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(v, w);
        cache(v, w, bfs);
        return bfs.resultDistance;
    }

    public int ancestor(int v, int w) {
        if (v == lastV && w == lastW) {
            return lastAncestor;
        }
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(v, w);
        cache(v, w, bfs);
        return bfs.resultVertex;
    }

    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v.equals(lastIterableV) && w.equals(lastIterableW)) {
            return lastIterableLength;
        }
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(v, w);
        cache(v, w, bfs);
        return bfs.resultVertex;
    }

    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v.equals(lastIterableV) && w.equals(lastIterableW)) {
            return lastIterableAncestor;
        }
        var bfs = new DoubleBFS(graph);
        bfs.lockstepBfs(v, w);
        cache(v, w, bfs);
        return bfs.resultVertex;
    }
}
